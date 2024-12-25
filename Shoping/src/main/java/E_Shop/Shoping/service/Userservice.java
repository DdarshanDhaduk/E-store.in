package E_Shop.Shoping.service;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.reposisory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class Userservice {

    @Autowired
    private Emailservice emailservice;

    @Autowired
    private Replacerepo replacerepo;

    @Autowired
    private Productcolorrepo productcolorrepo;

    @Autowired
    private Productrepo productrepo;

    @Autowired
    private Ordersrepo ordersrepo;

    @Autowired
    private Likesrepo likesrepo;

    @Autowired
    private Couponoffrepo couponoffrepo;

    @Autowired
    private Finalorderlistrepo finalorderlistrepo;

    @Autowired
    private FinalorderRepo finalorderRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Userrepo userrepo;

//################&&&&&& USER, ACCOUNT, ORDER VIEW, LIKE, LOCALORDER &&&&&&&##################//

    //*************USER SAVE AND UPDATE*************//

    public void saveuser(User user){
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username must be provided");
        }
//        enable true
        user.setEnable(true);
//        password encorder for
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        set the user role
        user.setRole("ROLE_USER");

        userrepo.save(user);
    }

//    get user
    public boolean getuserbyuserid(String id){
        return userrepo.existsuserByIdCustom(id);
    }

//    update user
    public void updateuser(User user){
        userrepo.save(user);
    }

//    update phone_no
    public void updatephonenumber(String no, User user){
        user.setPhone_no(no);
        userrepo.save(user);
    }

//    update password
    public void changepasswords(String username,String password,String newpassword) throws Exception{
        User user=userrepo.findById(username).get();
        if(passwordEncoder.matches(password, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newpassword));
            userrepo.save(user);
        }
        else {
            throw new Exception("not same currentpassword");
        }
    }
    //*************ACCOUNT PAGE CODE*************//

//    Dashboard
    public User getuserbyid(String id){
        return userrepo.findById(id).get();
    }

//    get FinalOrder by UserId
    public List<Finalorder> getFinalOrderByUserId(String id){
        return finalorderRepo.findFinalOrderByUserId(id);
    }

//    get coupon off
    public Couponoff getCouponoff(){
        return couponoffrepo.findById(1).get();
    }

    //*************Update Coupon*************//

    public void updatecoupon(Couponoff couponoff){
        couponoff.setId(1);
        couponoffrepo.save(couponoff);
    }

    //*************FINALORDER VIEW PAGE CODE*************//

//    get Productorder by bill_id
    public List<Finalorderlist> getProductByBillId(int id){
        return finalorderlistrepo.findFinalOrderlistByBillId(id);
    }

//    get finalorderlist by id
    public Finalorderlist getfinalorderlistbyid(int id){
        return finalorderlistrepo.findById(id).get();
    }


    //*************Whishlist PAGE CODE*************//

//    get likes by userid
    public List<Likes> getwhishlistbyuserid(String userid){
        return likesrepo.findLikesProductByUserId(userid);
    }

//    save likes
    public void savewhishlist(String id, String uid){
        User user = userrepo.findById(uid).get();
        Product product = productrepo.findById(id).get();

        Likes likes = new Likes();
        likes.setProduct(product);
        likes.setUsers(user);
        likesrepo.save(likes);
    }

//    get likes by uid and pid
    public Likes getlikesbyuidandpid(String uid, String pid){
        return likesrepo.getlikesbyuseridandpid(uid, pid);
    }

//    delete whishlist
    public void deletewhishlist(int id){
        likesrepo.deleteById(id);
    }

    //*************ORDERCART VIEW PAGE CODE*************//

//  get order by userid
    public List<Orders> getorderbyuserid(String userid){
        return ordersrepo.findOrderlistByUserid(userid);
    }

//    delete ordercart
    public void deletordercart(int id){
        ordersrepo.deleteById(id);
    }

//    save ordercart
    public void saveordercart(String pid,String color, int quntity, String userid){
        User user=userrepo.findById(userid).get();
        Product product=productrepo.findById(pid).get();
        Orders orders=new Orders();
        orders.setColor(color);
        orders.setQuntity(quntity);
        orders.setUsers(user);
        orders.setProduct(product);

        ordersrepo.save(orders);
    }

//    update ordercart
    public void updateordercart(int qunity, int oid){
        Orders orders = ordersrepo.findById(oid).get();
        orders.setQuntity(qunity);
        ordersrepo.save(orders);
    }

//    get finalorder by id
    public Finalorder getfinalorderbyid(int id){
        return finalorderRepo.findById(id).get();
    }


//    final order place
    public int orderplace(int deliverytype, int paymenttype, String gmailid, int couponcode, float eoff){

//        randome 6 digit
        Random random=new Random();
        boolean loop=true;
        int randomenumber=0;
        while (loop){
             randomenumber = 100000 + random.nextInt(900000);
            if (finalorderRepo.existsByfinalorderIdCustom(randomenumber)){
                loop=false;
            }
        }

        List<Orders> orders = ordersrepo.findOrderlistByUserid(gmailid);

        Finalorder finalorder=new Finalorder();
        finalorder.setBill_id(randomenumber);
        finalorder.setDate(LocalDate.now());
        Couponoff couponoff = couponoffrepo.findById(1).get();
        if(couponcode>0){
            finalorder.setCouponcode(true);
            finalorder.setCouponoff(couponcode);
        }
        else {
            finalorder.setCouponcode(false);
            finalorder.setCouponoff(0);
        }
        if(deliverytype==2){
            finalorder.setDeliverydate(LocalDate.now().plusDays(1));
            finalorder.setDelivverycharge(orders.size()*100);
            finalorder.setDelivverytype("faster");
        }
        else {
            finalorder.setDeliverydate(LocalDate.now().plusDays(2));
            finalorder.setDelivverycharge(0);
            finalorder.setDelivverytype("free");
        }
        if(paymenttype==1){
            finalorder.setPaymenttype("Cash On Deliviry");
        }
        else {
            finalorder.setPaymenttype("Online Payment");
        }
        if (eoff == 0){
            finalorder.setEstroepoint(0);
        }else {
            finalorder.setEstroepoint(eoff);
        }

        String otp = emailservice.generateOTP();
        finalorder.setOtp(otp);

        User user=userrepo.findById(gmailid).get();
        finalorder.setUser(user);
            finalorderRepo.save(finalorder);

            int point=0,buypoint=0;
            Finalorder finalorder1 = finalorderRepo.findById(randomenumber).get();
            for (int i = 0; i < orders.size(); i++) {

                Finalorderlist finalorderlist = new Finalorderlist();
                finalorderlist.setImg(orders.get(i).getProduct().getProductimg1());
                finalorderlist.setColorcode(orders.get(i).getColor());
                finalorderlist.setQuntity(orders.get(i).getQuntity());
                finalorderlist.setPrice(orders.get(i).getProduct().getPrice());
                finalorderlist.setProduct_id1(orders.get(i).getProduct().getProduct_id());
                LocalDate localDate = finalorder1.getDeliverydate().plusDays(orders.get(i).getProduct().getReturnday());
                finalorderlist.setReplaceendtime(localDate);
                finalorderlist.setReplaceproduct(false);
                finalorderlist.setProduct_name(orders.get(i).getProduct().getProduct_name());
                finalorderlist.setFinalorders(finalorder1);

                Productcolor productcolor = productcolorrepo.findBypidandcolor(orders.get(i).getColor(),orders.get(i).getProduct().getProduct_id());
                productcolor.setQuntity(productcolor.getQuntity()-orders.get(i).getQuntity());
                productcolorrepo.save(productcolor);

                Product product = productrepo.findById(orders.get(i).getProduct().getProduct_id()).get();
                product.setTotal_buyproduct(product.getTotal_buyproduct()+orders.get(i).getQuntity());
                productrepo.save(product);

                buypoint+=product.getCoupon_price();

                point+=orders.get(i).getProduct().getCoupon();
                finalorderlistrepo.save(finalorderlist);
            }
            if(buypoint<=user.getEstore_point()){
                user.setEstore_point(user.getEstore_point()-buypoint);
            }

            user.setEstore_point(user.getEstore_point()+point);
            userrepo.save(user);
            ordersrepo.deleteallorderbyuserid(gmailid);

            return randomenumber;
    }

//    forgot password update
    public void updateforgotpassword(String uid, String password){
        User user = userrepo.findById(uid).get();
        user.setPassword(passwordEncoder.encode(password));
        userrepo.save(user);
    }

    //************* Replace PAGE CODE*************//

    public void setreplaceproduct(int pid, String uid){
        Finalorderlist finalorderlist = finalorderlistrepo.findById(pid).get();
        User user = userrepo.findById(uid).get();

        finalorderlist.setReplaceproduct(true);
        finalorderlistrepo.save(finalorderlist);

        Repalceorder repalceorder = new Repalceorder();
        repalceorder.setLocalDate(LocalDate.now());
        repalceorder.setCheck1(false);
        repalceorder.setFinalorderlist(finalorderlist);
        repalceorder.setUsers(user);
        replacerepo.save(repalceorder);
    }

    public List<Finalorder> getfinalorderall(){
        return finalorderRepo.findDatesDesc()   ;
    }
}
