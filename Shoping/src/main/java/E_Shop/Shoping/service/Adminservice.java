package E_Shop.Shoping.service;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.reposisory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Adminservice {

    @Autowired
    private Deletproductrepo deletproductrepo;

    @Autowired
    private Ratingstarrepo ratingstarrepo;

    @Autowired
    private Likesrepo likesrepo;

    @Autowired
    private Replacerepo replacerepo;

    @Autowired
    private Headoffrepo headoffrepo;

    @Autowired
    private Productcolorrepo productcolorrepo;

    @Autowired
    private Productrepo productrepo;

    @Autowired
    private Productmenurepo productmenurepo;

    @Autowired
    private Moviesrepo moviesrepo;

    @Autowired
    private Ordersrepo ordersrepo;

//    @Autowired
//    private Userrepo userrepo;


//*************Product*************//
//    Product Save And Update
    public void productsave(Product product,int id) throws Exception{
        Productmenu menu=productmenurepo.findById(id).orElseThrow(()->new Exception("productmenu not found"));

        Boolean ans=productrepo.existsByIdCustom(product.getProduct_id());
        Boolean ans1=deletproductrepo.deletecheckidtruefalse(product.getProduct_id());
        if(ans && ans1) {
            product.setProductmenu(menu);
            productrepo.save(product);
        }
        else {
            System.out.println(false);
        }
//        menu.getProducts().add(product);
//        productmenurepo.save(menu);
    }

//    UPdate Product
public void productupdate(Product product,int id ,String idp) throws Exception{
    Productmenu menu=productmenurepo.findById(id).orElseThrow(()->new Exception("productmenu not found"));

        product.setProductmenu(menu);
        product.setProduct_id(idp);

        productrepo.save(product);
}

    //    All Product Get
    public List<Product> productviewall(){
        return productrepo.findAll();
    }

    //    Live Timer Product Offer Get
    public List<Product> getliveOffer(){
        return productrepo.liveOffer();
    }

    // Get Product by id
    public Product getproductibyd(String id){
        return productrepo.findById(id).get();
    }

    // Remove show product details
    public List<Deletproduct> showremove(){
        return deletproductrepo.findAll();
    }

    // Remove Product get by id
    public Deletproduct getremoveproductbyid(int id){
        return deletproductrepo.findById(id).get();
    }

//*************Product Menu*************//
//    Product Menu Save
    public void productmenusave(Productmenu productmenu){
        productmenurepo.save(productmenu);
    }

//    One Menu Get
    public Productmenu getmenu(int id){
        return productmenurepo.findById(id).get();
    }

    //    Get All Product Menu List
    public List<Productmenu> getproductmenuList(){
        return productmenurepo.findAll();
    }

//    update productMenu
    public void productmenuupdate(Productmenu productmenu, int id){
        productmenu.setMenu_id(id);
        productmenurepo.save(productmenu);
    }


//*************Movies*************//
    //    Movie Save
     public void moviessave(Movies movies) throws Exception {
         moviesrepo.save(movies);
    }

    //    Get All Movies
    public List<Movies> getAllMovies(){
        return moviesrepo.findAll();
    }

    //    Get Mopvie by id
    public Movies getMovieById(String id){
        return moviesrepo.findById(id).get();
    }

//    update movie
    public void updatemovie(Movies movies, String id){
        movies.setMovie_id(id);
        moviesrepo.save(movies);
    }


//*************Product Color*************//
//  Product Color save
public void savecolor(Productcolor productcolor,String id) throws Exception{
    Product p=productrepo.findById(id).orElseThrow(()->new Exception(("product not found")));

//        Create Total Quantity Productcolor
    int quntity=productcolor.getQuntity();
    int totalquntity=productcolor.getTotalquntity();
    int ans=quntity+totalquntity;
    productcolor.setTotalquntity(ans);

//        create Total Quantity product
    p.setTotalquntity(p.getTotalquntity()+quntity);

    productcolor.setProduct(p);
    p.getProductcolors().add(productcolor);

//    productrepo.save(p);
    productcolorrepo.save(productcolor);
}

    //product color get by color_id
    public Productcolor getproductcolor(int id){
        return productcolorrepo.findById(id).get();
    }

    //get product coolor list by product_id
    public List<Productcolor> getProductColorByProductId(String id){
        return productcolorrepo.findByProductId(id);
    }


    //Update product_color
    public void updatecolor(Productcolor productcolor,int cid, String id){

        Product product=productrepo.findById(id).get();
        Productcolor pc=productcolorrepo.findById(cid).get();

        int quntity=productcolor.getQuntity();
        product.setTotalquntity(product.getTotalquntity()+quntity);
        productcolor.setTotalquntity(pc.getTotalquntity()+quntity);
        productcolor.setQuntity(pc.getQuntity()+quntity);

        productcolor.setColor_id(cid);
        productcolor.setProduct(product);
        productrepo.save(product);
        productcolorrepo.save(productcolor);

    }

    //    Finish Product color List Get
    public List<Productcolor> getfinish(){
        return productcolorrepo.findLowStockProducts();
    }

//    Delete color
    public void deletcolor(int id, String pid){
        Productcolor productcolor=productcolorrepo.findById(id).get();
        productcolorrepo.delete(productcolor);
        ordersrepo.deleteallorder(pid,productcolor.getColorcode());

        Product product=productrepo.findById(pid).get();

        if(product.getProductcolors().isEmpty()){
//            deletproduct save
            List<Likes> likes = likesrepo.getlikebypid(pid);
            for (Likes likes1 : likes){
                likesrepo.deleteById(likes1.getId());
            }

            List<Ratingstar> ratingstars = ratingstarrepo.getratingstarbypid(pid);
            for (Ratingstar ratingstar : ratingstars){
                ratingstarrepo.deleteById(ratingstar.getId());
            }

            List<Orders> orders = ordersrepo.getorderbypid(pid);
            for (Orders orders1 : orders){
                ordersrepo.deleteById(orders1.getOrder_id());
            }

            Deletproduct deletproduct=new Deletproduct();
            deletproduct.setProduct_id(product.getProduct_id());
            deletproduct.setName(product.getName());
            deletproduct.setTotal_buyquntity(product.getTotal_buyproduct());
            deletproduct.setTotal_Addquntity(product.getTotalquntity());
            deletproduct.setProduct_name(product.getProduct_name());
            deletproduct.setImg(product.getProductimg1());

            deletproductrepo.save(deletproduct);
            productrepo.deleteById(pid);
        }
    }
    //************* Repalce Code *************//

    public List<Repalceorder> getrepalceproduct(){
        return replacerepo.findDatesDesc();
    }

    public void setreplacecheck(int id){
        Repalceorder repalceorder = replacerepo.findById(id).get();
        repalceorder.setCheck1(true);
        replacerepo.save(repalceorder);
    }

    //*************Head Offer*************//
     //    Head Offer Save
      public void saveheadoff(Headoff headoff){
        Headoff headoffs = headoffrepo.liveHeadOffer();

        headoffs.setEndtime(LocalDateTime.now().minusHours(2));
        headoffrepo.save(headoffs);
          headoffrepo.save(headoff);
      }

    //All Head Offer Get
      public List<Headoff> getallHeadOff(){
        return headoffrepo.findAll();
      }

    //Get Head Offer By Id
    public Headoff getHeadOffById(int id){
        return headoffrepo.findById(id).get();
    }

//    headoff update
    public void headoffupdate(Headoff headoff,int id){
        headoff.setOffid(id);
        headoffrepo.save(headoff);
    }

//    Headoff Dlete
    public void deleteheadoff(int id){
        headoffrepo.deleteById(id);
    }


//    //
//    public User userdetails(String id){
//        return userrepo.findById(id).get();
//    }
}
