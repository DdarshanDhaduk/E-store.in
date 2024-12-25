package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.service.Adminservice;
import E_Shop.Shoping.service.Emailservice;
import E_Shop.Shoping.service.Homeservice;
import E_Shop.Shoping.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class Home {

    //*************************************************Home, Menu(Shop), Product(same type(t.v, a.c....)) Page Code*************************************************//

    @Autowired
    private Emailservice emailservice;

    @Autowired
    private Userservice userservice;

    @Autowired
    private Homeservice homeservice;

    @Autowired
    private Adminservice adminservice;

    //###################################Home Code###################################//
    @GetMapping("/home")
    public String homepage(Model model, Principal principal){

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

//        Live Headoff get
        Headoff headoffs=homeservice.getLiveheadoff();
        model.addAttribute("headlive",headoffs);
//        Max off two product
        List<Product> products=homeservice.getmaxiheadoff();
            Product product1 = products.get(0);
            Product product2 = products.get(1);
        model.addAttribute("headmax1",product1);
        model.addAttribute("headmax2",product2);

//        Get Any 8 Menu
        List<Productmenu> menu=homeservice.getmenuList();
        Collections.shuffle(menu);
        List<Productmenu> menu2 = new ArrayList<>();
        if (11<menu.size()) {
            for (int i = 0; i < 12; i++) {
                menu2.add(menu.get(i));
            }
        }
        model.addAttribute("menul",menu2);

//        show(all product, smart product, hot offer)
//        show all product
        List<Product> allproducts = adminservice.productviewall();
        Collections.shuffle(allproducts);
        List<Product> allproductshow = new ArrayList<>();
        if (7<allproducts.size()) {
            for (int i = 0; i < 8; i++) {
                allproductshow.add(allproducts.get(i));
            }
        }
        model.addAttribute("allproduct",allproductshow);

//        show hot offer product
        Collections.shuffle(products);
        List<Product> hotoffproduct=new ArrayList<>();
        if (7<products.size()) {
            for (int i = 0; i < 8; i++) {
                hotoffproduct.add(products.get(i));
            }
        }
        model.addAttribute("hotoff",products);

//      get deals products
        List<Product> deals=adminservice.getliveOffer();
        Collections.shuffle(deals);
        List<Product> deals1=new ArrayList<>();
        if(deals.size()>1) {
            for (int i = 0; i < 2; i++) {
                deals1.add(deals.get(i));
            }
        }
        model.addAttribute("dealsp",deals1);

//        get  Movies
        List<Movies> movie = adminservice.getAllMovies();
        Collections.shuffle(movie);
        List<Movies> movies=new ArrayList<>();
        if (6<movie.size()) {
            for (int i = 0; i < 7; i++) {
                movies.add(movie.get(i));
            }
        }
        model.addAttribute("movieshow",movies);

//        product get 8 to 16
        List<Product> allproductshow2 = new ArrayList<>();
        if (3<allproducts.size()) {
            for (int i = 8; i < 16; i++) {
                allproductshow2.add(allproducts.get(i));
            }
        }
        model.addAttribute("showproduct8to16",allproductshow2);


//        Hot Off and hotmovie and all product and all movie
//        hot off product
        List<Product> maxoffp=new ArrayList<>();
        if (2<products.size()){
            for (int i = 0; i <3; i++) {
            maxoffp.add(products.get(i));
        }
            }
        model.addAttribute("maxoff",maxoffp);

//        hot off movie
        List<Movies> hotmovie=homeservice.getmexoffmovies();
        Collections.shuffle(hotmovie);
        model.addAttribute("maxmovie",hotmovie);

//        all product for
        List<Product> productsa=new ArrayList<>();
        if (2<allproducts.size()) {
            for (int i = 0; i < 3; i++) {
                productsa.add(allproducts.get(i));
            }
        }
        model.addAttribute("productall",productsa);

//        all Movies for
        List<Movies> moviesa=new ArrayList<>();
        if (3<movie.size()) {
            for (int i = 0; i < 4; i++) {
                moviesa.add(movie.get(i));
            }
        }
        model.addAttribute("moviesa",moviesa);


        return "index";
    }


//###################################PRODUCT Page Code###################################//

//    get manu product page by menu id
        @GetMapping("/menuproduct/{id}")
    public String getproductpagebymenuid(@PathVariable("id") int id, Model model, Principal principal){

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

        List<Product> products=homeservice.getproductbymenuid(id);
        model.addAttribute("product", products);
        return "product";
    }

//###################################MANU Page Code###################################//

    @GetMapping("/menushow")
    private String manushowshoppage(Model model, Principal principal){
        List<Productmenu> productmenuList=adminservice.getproductmenuList();
        model.addAttribute("menu",productmenuList);
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
        return "shop";
    }

    //*************Get All Img***************//

    //    Imag get and print web page only Product for
    @GetMapping("/imgp/{id}/{name}")
    public ResponseEntity<byte[]> getImgp(@PathVariable("id") String id, @PathVariable("name") String name) {
        Product p = adminservice.getproductibyd(id);

        if (name.equals("i1")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(p.getProductimg1());
        }
        else if (name.equals("im1")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(p.getProductimg2());
        }
        else if (name.equals("i2")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(p.getHeadoffimg());
        }
        else if (name.equals("i3")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(p.getOfferimg());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //    Imag get and print web page only Movie for
    @GetMapping("/imgm/{id}/{name}")
    public ResponseEntity<byte[]> getImgm(@PathVariable("id") String id,
                                          @PathVariable("name") String name) {

        Movies m = adminservice.getMovieById(id);
        if (name.equals("i1")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(m.getImg1());
        }
        else if (name.equals("i2")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(m.getImg2());
        }
        else if (name.equals("i3")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(m.getImg3());
        }
        else if (name.equals("i4")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // or video as per your video type
                    .body(m.getTailer());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //    Imag get and print web page only Product Menu for
    @GetMapping("/imgpm/{id}")
    public ResponseEntity<byte[]> getImgpm(@PathVariable("id") int id) {
        Productmenu pm = adminservice.getmenu(id);
        if (pm.getImg() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(pm.getImg());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    Imag get and print web page only HeadOffer for
    @GetMapping("/imgph/{id}")
    public ResponseEntity<byte[]> getImgh(@PathVariable("id") int id) {
        Headoff h = adminservice.getHeadOffById(id);
        if (h.getImg() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(h.getImg());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    Imag get and print web page only ProductColor for
        @GetMapping("/img/{id}/{name}")
        public ResponseEntity<byte[]> getImg(@PathVariable("id") int id, @PathVariable("name") String name) {
            Productcolor p = adminservice.getproductcolor(id);

            if (name.equals("i1")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                        .body(p.getImg1());
            }
            else if (name.equals("i2")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                        .body(p.getImg2());
            }
            else if (name.equals("i3")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                        .body(p.getImg3());
            }
            else if (name.equals("i4")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                        .body(p.getImgv1());
            }
            else if (name.equals("i5")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM) // or video as per your video type
                        .body(p.getVideo1());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }

//       Imag get and print web page only finalorderlist for

    @GetMapping("/imgfo/{id}")
    public ResponseEntity<byte[]> getImgfinalorderlist(@PathVariable("id") int id) {
        Finalorderlist fo = userservice.getfinalorderlistbyid(id);
        if (fo.getImg() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(fo.getImg());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//################################### Detail Page Code ###################################//

    @GetMapping("/detail/{id}")
    public String sowdetail(@PathVariable("id") String id, Model model, Principal principal){

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

        Product product = adminservice.getproductibyd(id);
        model.addAttribute("products",product);

        List<Productcolor> productcolor= adminservice.getProductColorByProductId(id);
        Productcolor productcolor1 = productcolor.get(0);
        model.addAttribute("color1",productcolor1);
        model.addAttribute("color",productcolor);

        Productmenu productmenu = adminservice.getmenu(product.getProductmenu().getMenu_id());
        model.addAttribute("menu",productmenu);

//        description show
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
        if(principal!=null && homeservice.chekbuyproductuser(principal.getName(), id)){
            model.addAttribute("reviewt","t");
        }

        return "details";
    }

//    review submit
    @PostMapping("/do_review/{id1}")
    public String submitreview(@PathVariable("id1") String id1, Ratingstar ratingstar, Principal principal){

        homeservice.submitereviews(principal.getName(),id1, ratingstar.getDescription(),ratingstar.getStar_no());

        return "redirect:/detail/"+id1;
    }

    //  Forgot password

    @GetMapping("/forgot")
    public String forgotpassword(){

        return "forgot1";
    }

    @PostMapping("/do_forgot")
    public String forgotpassword2(@RequestParam("username") String name, Model model){

        if(name.isEmpty()){
            model.addAttribute("error","Username cannot be empty");
            return "forgot1";
        }

        if(userservice.getuserbyuserid(name)){
            model.addAttribute("error","This username is not valid! Username already exists.");
            return "forgot1";
        }

        String otp = emailservice.generateOTP();
        model.addAttribute("otp",otp);
        model.addAttribute("uid",name);

        emailservice.sendOTP(name, otp);

        return "sentotp";
    }

    @PostMapping("/do_sentotp/{otp}/{id}")
    public String sentotps(@RequestParam("snetotp") int otp, @PathVariable("otp") String otp3, @PathVariable("id") String id, Model model){

        String otp2 = String.format("%06d",otp);

        if(!otp2.equals(otp3)){
            model.addAttribute("error","The OTP you entered is incorrect.");
            model.addAttribute("otp",otp3);
            model.addAttribute("uid",id);
            return "sentotp";
        }
        model.addAttribute("uid",id);

        return "forgotpasswordchend";
    }

    @PostMapping("/do_forgotpassword/{id}")
    public String forgotpasswordfinal(@RequestParam("newpassword") String p, @PathVariable("id") String uid){

        userservice.updateforgotpassword(uid,p);

        return "redirect:/login";
    }

//    try
    @GetMapping("/try45")
    public String try567(Model model){

        model.addAttribute("t","/img/avatar-2.jpg");

        return "try";
    }

    //**************** View Bill *****************//

    @GetMapping("/bill/{id}")
    public String billshoww(@PathVariable("id") int billid, Model model){

        Finalorder finalorder = userservice.getfinalorderbyid(billid);
        model.addAttribute("bid",finalorder);

        List<Finalorderlist> finalorderlist = userservice.getProductByBillId(finalorder.getBill_id());
        model.addAttribute("fo",finalorderlist);
        int total1 = 0;
        for (Finalorderlist finalorderlist1 : finalorderlist){
            total1 += (finalorderlist1.getPrice()*finalorderlist1.getQuntity());
        }
        model.addAttribute("total",total1);
        return "billpage";
    }
// try
//    @GetMapping("/try123")
//    public String try576(Model model){
//        model.addAttribute("pid",2);
//
//        return "try";
//    }
}
