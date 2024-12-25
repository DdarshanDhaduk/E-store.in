package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.service.Adminservice;
import E_Shop.Shoping.service.Emailservice;
import E_Shop.Shoping.service.Homeservice;
import E_Shop.Shoping.service.Userservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Homeservice homeservice;

    @Autowired
    private Emailservice emailservice;

    @Autowired
    private Adminservice adminservice;

    @Autowired
    private Userservice userservice;

//****************ACCOUNT, VIEW ORDER PRODUCT, MOVIE SAVE BY USER, LIKE SAVE, LOCAL ORDER STORE*****************//


//#####&&&&********Account Page Code**********&&&&#####//
    @GetMapping("/account")
    public String accountcode(Principal principal, Model model){

//        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }


//        Dashboard Details
        User user=userservice.getuserbyid(principal.getName());
        model.addAttribute("user",user);

//        Order Details(get finalorder by userid)
        List<Finalorder> finalorders= userservice.getFinalOrderByUserId(user.getUsername());
        model.addAttribute("order",finalorders);

//        Coupon card manage
        Couponoff couponoff=userservice.getCouponoff();
        model.addAttribute("coupon",couponoff);

//        E-Store Point card manage
        int point = user.getEstore_point();
        model.addAttribute("epoint",point);

//        show user address
        model.addAttribute("address",user);

        //        error page not show for model
        model.addAttribute("act",0);

        return "account";
    }

//#####&&&&******** Change Passowrd **********&&&&#####//

    @PostMapping("/changepassword")
    public String changepassword(Principal principal,
                                 @RequestParam("password") String password1,
                                 @RequestParam("newpassword") String newpassword,
                                 @RequestParam("confirmpassword") String confirmpassword, Model model) throws Exception {

        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }


//        Dashboard Details
        User user=userservice.getuserbyid(principal.getName());
        model.addAttribute("user",user);

//        Order Details(get finalorder by userid)
        List<Finalorder> finalorders= userservice.getFinalOrderByUserId(user.getUsername());
        model.addAttribute("order",finalorders);

//        Coupon card manage
        Couponoff couponoff=userservice.getCouponoff();
        model.addAttribute("coupon",couponoff);

//        E-Store Point card manage
        int point = user.getEstore_point();
        model.addAttribute("epoint",point);

//        show user address
        model.addAttribute("address",user);

//        error page show for model
        model.addAttribute("act",1);

//        chang password code
        User user1 = userservice.getuserbyid(principal.getName());
        if (!passwordEncoder.matches(password1,user1.getPassword())) {
            model.addAttribute("error1","please enter the true curentpassword");
            return "account";
        }
            if (newpassword.equals(confirmpassword)){
                userservice.changepasswords(principal.getName(),password1,newpassword);
            }
            else {
                model.addAttribute("error","Not same configpassword");
                return "account";
            }

        return "redirect:/user/account";
    }

//#####&&&&******** Edit Address Page Code**********&&&&#####//

    @GetMapping("/editaddress")
    public String editaddres(Principal principal, Model model){

        //        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }

        User user=userservice.getuserbyid(principal.getName());
        Address address = new Address();
        if(user.getHome_no()>0 || !user.getArea().isEmpty()) {
            address.setArea(user.getArea());
            address.setCity(user.getCity());
            address.setDistrict(user.getDistrict());
            address.setPincode(user.getPincode());
            address.setState(user.getState());
            address.setHome_no(user.getHome_no());
            address.setStreet_name(user.getStreet_name());
        }

        model.addAttribute("add",address);

        return "editaddress";
    }

    @PostMapping("/do_editaddres")
    public String editaddress(@Valid @ModelAttribute Address address, BindingResult result, Principal principal, Model model){

            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError error : result.getFieldErrors()) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }

                //        login
                if (principal == null) {
                    model.addAttribute("usern", 0);
                    model.addAttribute("w", 0);
                    model.addAttribute("c", 0);
                } else {
                    List<Orders> orders = userservice.getorderbyuserid(principal.getName());
                    List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
                    model.addAttribute("w", likes.size());
                    model.addAttribute("c", orders.size());
                    model.addAttribute("usern", 1);
                }

                model.addAttribute("error", errors);
                model.addAttribute("add", address);
                return "editaddress";
            }

        User user = userservice.getuserbyid(principal.getName());
            user.setArea(address.getArea());
            user.setCity(address.getCity());
            user.setDistrict(address.getDistrict());
            user.setState(address.getState());
            user.setStreet_name(address.getStreet_name());
            user.setHome_no(address.getHome_no());
            user.setPincode(address.getPincode());


        userservice.updateuser(user);

        return "redirect:/user/account";
    }

//#####&&&&******** FinalOrder View Product Page Code**********&&&&#####//

    @GetMapping("/vieworder/{id}")
    public String vieworders(@PathVariable("id") int id, Model model, Principal principal){

        //        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }

        List<Finalorderlist> finalorderlists=userservice.getProductByBillId(id);
        model.addAttribute("vieworder",finalorderlists);
        model.addAttribute("billid",id);

        return "ordershow";
    }

//#####&&&&******** Whishlist Product Page Code**********&&&&#####//

//    show whishlist
    @GetMapping("/whishlist")
    public String whishlists(Principal principal, Model model){

        List<Likes> likes1 = userservice.getwhishlistbyuserid(principal.getName());

        model.addAttribute("like",likes1);

//        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }
        return "wishlist";
    }

//    save whishlist
    @GetMapping("/savelike/{id}")
    public String savewhishlist(@PathVariable("id") String id, Principal principal){

        Likes likes = userservice.getlikesbyuidandpid(principal.getName(), id);
        if(likes!=null){
            return "redirect:/user/whishlist";
        }

        userservice.savewhishlist(id, principal.getName());

        return "redirect:/user/whishlist";
    }

//    delet whishlist
    @GetMapping("/deletwhishlist/{id}")
    public String deletewhishlist(@PathVariable("id") int id){

        userservice.deletewhishlist(id);

        return "redirect:/user/whishlist";
    }

//#####&&&&******** OrderCart View  Page Code**********&&&&#####//

//    shoe cart
    @GetMapping("/cartorder")
    public String ordercart(Principal principal, Model model){

        List<Orders> order = userservice.getorderbyuserid(principal.getName());
        model.addAttribute("orders",order);

        //        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }

        return "cart";
    }

//    save cartorder
    @PostMapping("/do_cartorder/{id1}")
    public String ordercraete(@PathVariable("id1") String pid,@RequestParam("colorOption") int colorid, @RequestParam("quntity") int quntity, Principal principal, Model model){

        Productcolor productcolor=adminservice.getproductcolor(colorid);

//        error quntity
        if(productcolor.getQuntity()<(quntity+1)){

            //        Login and  Logout
            if (principal== null){
                model.addAttribute("usern",0);
                model.addAttribute("w",0);
                model.addAttribute("c",0);
            }
            else {
                List<Orders> orders = userservice.getorderbyuserid(principal.getName());
                List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
                model.addAttribute("w",likes.size());
                model.addAttribute("c",orders.size());
                model.addAttribute("usern",1);
            }

            Product product = adminservice.getproductibyd(pid);
            model.addAttribute("products",product);

            List<Productcolor> productcolors = adminservice.getProductColorByProductId(pid);
            Productcolor productcolor1 = productcolors.get(0);
            model.addAttribute("color1",productcolor1);
            model.addAttribute("color",productcolors);

            Productmenu productmenu = adminservice.getmenu(product.getProductmenu().getMenu_id());
            model.addAttribute("menu",productmenu);
            model.addAttribute("error","Requested quantity exceeds available stock for this color. Only " + (productcolor.getQuntity()-2) + " items are available.");

            Map<String ,String> map=new HashMap<>();
            map.put(productmenu.getD_1(), product.getD_1());
            map.put(productmenu.getD_2(), product.getD_2());
            map.put(productmenu.getD_3(), product.getD_3());
            map.put(productmenu.getD_4(), product.getD_4());
            map.put(productmenu.getD_5(), product.getD_5());
            map.put(productmenu.getD_6(), product.getD_6());
            map.put(productmenu.getD_7(), product.getD_7());
            map.put(productmenu.getD_8(), product.getD_8());
            map.put(productmenu.getD_9(), product.getD_9());
            map.put(productmenu.getD_10(), product.getD_10());
            map.put(productmenu.getD_11(), product.getD_11());
            map.put(productmenu.getD_12(), product.getD_12());
            map.put(productmenu.getD_13(), product.getD_13());
            map.put(productmenu.getD_14(), product.getD_14());
            map.put(productmenu.getD_15(), product.getD_15());

            model.addAttribute("detailsp",map);

//        releated product
            List<Product> products = homeservice.releatedproduct(product.getName(),product.getProduct_id());
            List<Product> products1 = new ArrayList<>();
            for (int i=0; i<8; i++){
                products1.add(products.get(i));
            }
            model.addAttribute("releated",products1);

//        rating show
            List<Ratingstar> ratingstars=homeservice.getratingstarbyproductid(product.getProduct_id());
            List<Ratingstar> ratingstars1=new ArrayList<>();
            if(ratingstars.size()>3) {
                Collections.shuffle(ratingstars);
                for (int i = 0; i < 3; i++) {
                    ratingstars1.add(ratingstars.get(i));
                }
                model.addAttribute("rating",ratingstars1);
            }
            else {
                model.addAttribute("rating",ratingstars);
            }
            if(principal!=null && homeservice.chekbuyproductuser(principal.getName(),pid)){
                model.addAttribute("reviewt","t");
            }

            return "details";
        }

        List<Orders> orders = userservice.getorderbyuserid(principal.getName());
        for (int i = 0; i <orders.size() ; i++) {
            if (orders.get(i).getProduct().getProduct_id().equals(pid) && orders.get(i).getColor().equals(productcolor.getColorcode())){
                quntity+=orders.get(i).getQuntity();
                userservice.updateordercart(quntity,orders.get(i).getOrder_id());
                return "redirect:/user/cartorder";
            }
        }

        userservice.saveordercart(pid,productcolor.getColorcode(),quntity,principal.getName());
        return "redirect:/user/cartorder";
    }

//    cart delet
    @GetMapping("/deletcart/{id}")
    public String deleteordercart(@PathVariable("id") int id){

        userservice.deletordercart(id);

        return "redirect:/user/cartorder";
    }

    //#####&&&&******** OrderSubmit**********&&&&#####//

    @GetMapping("/submitorder")
    public String submiteorders(Principal principal, Model model){

        //        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }

        User user = userservice.getuserbyid(principal.getName());
        model.addAttribute("user",user);

        return "ordersubmit1";
    }

    @PostMapping("/do_ordersubmit")
    public String dosubmitorders(@RequestParam("radio") int deliverytype,
                                 @RequestParam("phone_no") String phone_no,
                                 @RequestParam("Coupon_code") String couponcode,
                                 @RequestParam("radio1") int paymenttype,Principal principal){
        User user=userservice.getuserbyid(principal.getName());
        userservice.updatephonenumber(phone_no,user);

//        error for couponoff
        Couponoff couponoff=userservice.getCouponoff();
        if(!couponcode.equals(couponoff.getCode()) && couponcode!=""){
            return "redirect:/user/submitorder";
        }

//        error for address
        if(user.getCity().isEmpty()){
            return "redirect:/user/submitorder";
        }

        boolean code;
        if(couponcode=="" || couponcode==null){
            code=false;
        }
        else {
            code=true;
        }

        return "redirect:/user/placeorder/"+deliverytype+"/"+code+"/"+paymenttype;
    }

    @GetMapping("/placeorder/{deliverytype}/{code}/{paymenttype}")
    public String orderfinalplace(@PathVariable("deliverytype") int deliverytype,
                                  @PathVariable("code") boolean code,
                                  @PathVariable("paymenttype") int paymenttype,
                                  Principal principal, Model model){
        //        login
        if (principal == null){
            model.addAttribute("usern",0);
            model.addAttribute("w",0);
            model.addAttribute("c",0);
        }
        else {
            List<Orders> orders = userservice.getorderbyuserid(principal.getName());
            List<Likes> likes = userservice.getwhishlistbyuserid(principal.getName());
            model.addAttribute("w",likes.size());
            model.addAttribute("c",orders.size());
            model.addAttribute("usern",1);
        }

        List<Orders> orders = userservice.getorderbyuserid(principal.getName());
        int totalp=0;
        int point=0,buypoint=0;
        for (int i = 0; i <orders.size() ; i++) {
            totalp += orders.get(i).getProduct().getPrice()*orders.get(i).getQuntity();
            point+=(orders.get(i).getProduct().getPrice()*orders.get(i).getProduct().getCoupon_off())/100;
            buypoint+=orders.get(i).getProduct().getCoupon_price();
        }
        model.addAttribute("totalp",totalp);

        User user = userservice.getuserbyid(principal.getName());
        if(user.getEstore_point()>=buypoint){
            model.addAttribute("totalp1",totalp-point);
            int presentage = (point*100)/totalp;
            model.addAttribute("presentage",presentage);
            model.addAttribute("presentage1",(point*100)/totalp);
        }
        else {
            model.addAttribute("totalp1",0);
            model.addAttribute("presentage",0);
            model.addAttribute("presentage1",0);
            point=0;
        }

        int total=totalp-point;
        if(code==true){
            Couponoff couponoff=userservice.getCouponoff();
            Headoff headoff = homeservice.getLiveheadoff();
            int off = 0;
            if(headoff!=null){
                model.addAttribute("code",headoff.getHappycoupon());
                off=(total*headoff.getHappycoupon())/100;
                model.addAttribute("url2",headoff.getHappycoupon());
            }
            else {
                model.addAttribute("code",couponoff.getCoupon_off());
                off=(total*couponoff.getCoupon_off())/100;
                model.addAttribute("url2",couponoff.getCoupon_off());
            }
            total=total-off;
        }
        else {
            model.addAttribute("code", 0);
            model.addAttribute("url2",0);
        }

        if(deliverytype==2){
            model.addAttribute("delivery",orders.size()*100);
            total+=orders.size()*100;
        }
        else {
            model.addAttribute("delivery","");
        }
        model.addAttribute("url1",deliverytype);
        model.addAttribute("url3",paymenttype);

        model.addAttribute("total",total);
        model.addAttribute("payment",paymenttype);


        return "placeorder";
    }

    @GetMapping("/cashdelivery/{deliverytype}/{code}/{paymenttype}/{eoff}")
    public String cashplaceorder(@PathVariable("deliverytype") int deliverytype,
                                 @PathVariable("code") int code,
                                 @PathVariable("paymenttype") int paymenttype,
                                 @PathVariable("eoff") float eoff1,
                                 Principal principal){
        int foid = userservice.orderplace(deliverytype,paymenttype,principal.getName(),code,eoff1);

//        email sender
        Finalorder finalorder = userservice.getfinalorderbyid(foid);
        User user = userservice.getuserbyid(principal.getName());
        emailservice.senderOrderSuccessEmail(finalorder,user);

        return "redirect:/home";
    }

    @GetMapping("/onlinepayment/{deliverytype}/{code}/{paymenttype}/{eoff}")
    public String onlineplaceorder(@PathVariable("deliverytype") int deliverytype,
                                 @PathVariable("code") int code,
                                 @PathVariable("paymenttype") int paymenttype,
                                 @PathVariable("eoff") float eoff1,
                                 Principal principal){
        int foid = userservice.orderplace(deliverytype,paymenttype,principal.getName(),code,eoff1);

//        email sender
        Finalorder finalorder = userservice.getfinalorderbyid(foid);
        User user = userservice.getuserbyid(principal.getName());
        emailservice.senderOrderSuccessEmail(finalorder,user);

        return "redirect:/home";
    }

    //#####&&&&******** Replace Product Code**********&&&&#####//

    @GetMapping("/replace/{id}/{biilid}")
    public String replaceproduct(@PathVariable("id") int pid,
                                 @PathVariable("biilid") int billid, Principal principal){

        userservice.setreplaceproduct(pid,principal.getName());

        return "redirect:/user/vieworder/"+billid;
    }
}
