package E_Shop.Shoping.service;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.reposisory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Homeservice {
    //*************************************************Home, Menu(Shop), Product(same type(t.v, a.c....)) Page Code*************************************************//

    @Autowired
    private FinalorderRepo finalorderRepo;

    @Autowired
    private Buymoviesrepo buymoviesrepo;

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Finalorderlistrepo finalorderlistrepo;

    @Autowired
    private Headoffrepo headoffrepo;

    @Autowired
    private Ratingstarrepo ratingstarrepo;

    @Autowired
    private Productrepo productrepo;

    @Autowired
    private Moviesrepo moviesrepo;

    @Autowired
    private Productmenurepo productmenurepo;
    //###################################Home Code###################################//
//*************Slider Code***************//
//    Extra Headoffer Get
    public Headoff getLiveheadoff(){
        return headoffrepo.liveHeadOffer();
    }

//    Two Maximum Off Get Product
    public List<Product> getmaxiheadoff(){
        return productrepo.findTopEightDiscountedProducts();
    }

//   Live Head Offer get By Id
    public Headoff headoffByid(int id){
        return headoffrepo.findById(id).get();
    }


    //###################################Menu Code###################################//

    // get menu list
    public List<Productmenu> getmenuList(){
        return productmenurepo.findAll();
    }


//*************Product Code***************//
//    get all product by mneu id;
    public List<Product> getproductbymenuid(int id){
        return productrepo.getProductbymenuId(id);
    }

//    get releated prodcut
    public List<Product> releatedproduct(String name, String id){
        return productrepo.findreleatedproductlistByname(name,id);
    }

//*************Raatingstar Code***************//

    public List<Ratingstar> getratingstarbyproductid(String id){
        return ratingstarrepo.findratingstarbyproductid(id);
    }

//###################################Movies Code###################################//
//    get max off 8 Movies
    public List<Movies> getmexoffmovies(){
        return moviesrepo.findTopFourtCouponPricedMovies();
    }

    //*****************************************************review Code**********************************************************//

    public boolean chekbuyproductuser(String uid, String pid){
        List<Finalorderlist> finalorderlists = finalorderlistrepo.checkinuserbuyproduct(uid,pid);
        if (finalorderlists.isEmpty()){
            return false;
        }
        return true;
    }

    public void submitereviews(String uid, String pid, String d,int s){
        List<Finalorderlist> finalorderlist=finalorderlistrepo.checkinuserbuyproduct(uid,pid);

        Product product = productrepo.findById(pid).get();
        Ratingstar ratingstar=ratingstarrepo.findusergivereview(pid,uid);
        User user=userrepo.findById(uid).get();
        if(ratingstar!=null){
            int total = product.getTotal_star_count()-ratingstar.getStar_no();
            product.setTotal_star_count(total+s);
           ratingstar.setDescription(d);
           ratingstar.setStar_no(s);
           productrepo.save(product);
           ratingstarrepo.save(ratingstar);
        }
        else {
            if(!finalorderlist.isEmpty()){
                Ratingstar ratingstar1=new Ratingstar();
                ratingstar1.setName(user.getName());
                ratingstar1.setGmail(uid);
                ratingstar1.setProduct(product);
                ratingstar1.setDate(LocalDateTime.now());
                ratingstar1.setDescription(d);
                ratingstar1.setStar_no(s);
                product.setTotal_star(product.getTotal_star()+1);
                product.setTotal_star_count(product.getTotal_star_count()+s);

                productrepo.save(product);
                ratingstarrepo.save(ratingstar1);
            }
            else {
                System.out.println("not show");
            }
        }
    }

    //*****************************************************Scheduled Code**********************************************************//
//    get product list(/off not same and endtime finish) and Update poroduct in 10 second
   @Scheduled(fixedRate = 10000)
    public void updatetimeandoff(){
        List<Product> products=productrepo.findProductsWithDifferentOffAndLocalOff();
        if(!products.isEmpty()){
            for (Product productslist : products){
                Product p= productrepo.findById(productslist.getProduct_id()).get();
                p.setLocaloff(p.getOff());
                productrepo.save(p);
            }
        }
    }

    //*****************************************************Movie page code**********************************************************//

    public Movies getmoviebtmid(String mid){
        return moviesrepo.findById(mid).get();
    }

    public List<Movies> getmovieall(String id){
        return moviesrepo.findmoviesandnotsameid(id);
    }

//  Get Buy Movies By UserId
    public List<Buymovies> getmoviebyuserid(String uid){
        return buymoviesrepo.getbuymoviesbyuserid(uid);
    }

    public void deletebuymovie(int id){
        buymoviesrepo.deleteById(id);
    }

    public Buymovies findbuymoviebymidanduid(String mid, String uid){
        return buymoviesrepo.getbuymoviesbyuseridansmid(uid,mid);
    }

    public void savebuymovie(String id, String uid){
        User user = userrepo.findById(uid).get();
        Movies movies = moviesrepo.findById(id).get();

        user.setEstore_point(user.getEstore_point()-movies.getCoupon_price());
        userrepo.save(user);

        Buymovies buymovies = new Buymovies();
        buymovies.setPoint(movies.getCoupon_price());
        buymovies.setMovies(movies);
        buymovies.setUsers(user);
        buymoviesrepo.save(buymovies);
    }

    //************* Search Code ***************//

    public List<Product> getsearchproduct(String keyword){
        return productrepo.findbynameContaining(keyword);
    }

    public List<Finalorder> getsearchfinalorder(String keyword){
        return finalorderRepo.findByFinalOrderContaining(keyword);
    }

}
