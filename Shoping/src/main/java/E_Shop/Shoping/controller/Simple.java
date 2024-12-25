package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.service.Adminservice;
import E_Shop.Shoping.service.Userservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class Simple {

    @Autowired
    private Adminservice adminservice;

    @Autowired
    private Userservice userservice;

    //****************CREATEPRODUCT AND UPDATE AND REMOVE SHOW*****************//

    @PostMapping("/create")
    public String productc(@RequestParam("productmenu") int id, Model model) {
        model.addAttribute("id", id);
        Productmenu productmenu = adminservice.getmenu(id);
        Map<String, String> menu1 = productmenu.getMenuMap();
        model.addAttribute("menu", menu1);
        return "admincreateproduct";
    }

    @PostMapping("/do_product/{id}")
    public String productcr(@Valid @ModelAttribute Product product,
                            BindingResult result,
                            @PathVariable("id") int menuid,
                            @RequestParam("i1") MultipartFile img1,
                            @RequestParam("im1") MultipartFile imgm1,
                            @RequestParam("i2") MultipartFile img2,
                            @RequestParam("i3") MultipartFile img3,Model model) {

        if(result.hasErrors()){

            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("prod",product);
            model.addAttribute("id", menuid);
            Productmenu productmenu = adminservice.getmenu(menuid);
            Map<String, String> menu1 = productmenu.getMenuMap();
            model.addAttribute("menu", menu1);

            return "admincreateproduct";
        }

        try {
            product.setProductimg1(img1.getBytes());
            product.setProductimg2(imgm1.getBytes());
            product.setOfferimg(img2.getBytes());
            product.setHeadoffimg(img3.getBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
        try {
            String id= product.getProduct_id();
            adminservice.productsave(product, menuid);
            return "redirect:/admin/color/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


//    update product
    @GetMapping("/updatep/{id}/{menu}")
    public String updatepr(@PathVariable("id") String id, @PathVariable("menu") int menu, Model model){
        Product p=adminservice.getproductibyd(id);
        Productmenu productmenu=adminservice.getmenu(menu);
        Map<String,String> manumap=productmenu.getMenuMap();
        model.addAttribute("idm",menu);
        model.addAttribute("product",p);
        model.addAttribute("menu",manumap);
        return "updateproduct";
    }

    @PostMapping("/do_productup/{id}/{pid}")
    public String updateproduct(@Valid @ModelAttribute Product product,
                                BindingResult result,
                                @PathVariable("id") int menuid,
                                @PathVariable("pid") String productid,
                                @RequestParam("i1") MultipartFile img1,
                                @RequestParam("im1") MultipartFile imgm1,
                                @RequestParam("i2") MultipartFile img2,
                                @RequestParam("i3") MultipartFile img3, Model model){

        Product p1=adminservice.getproductibyd(productid);

        if(result.hasErrors()){

            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("product",p1);
            model.addAttribute("idm", menuid);
            Productmenu productmenu = adminservice.getmenu(menuid);
            Map<String, String> menu1 = productmenu.getMenuMap();
            model.addAttribute("menu", menu1);

            return "updateproduct";
        }

        try {
            if(!img1.isEmpty()){
                product.setProductimg1(img1.getBytes());
            }
            else {
                product.setProductimg1(p1.getProductimg1());
            }
            if(!imgm1.isEmpty()){
                product.setProductimg2(imgm1.getBytes());
            }
            else {
                product.setProductimg2(p1.getProductimg2());
            }
            if(!img2.isEmpty()){
                product.setOfferimg(img2.getBytes());
            }
            else {
                product.setOfferimg(p1.getOfferimg());
            }
            if(!img3.isEmpty()){
                product.setHeadoffimg(img3.getBytes());
            }
            else {
                product.setHeadoffimg(p1.getHeadoffimg());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        try {
            adminservice.productupdate(product,menuid,productid);
            return "redirect:/admin/showall";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    Remove Product Show
    @GetMapping("/removeshow")
    public String removeShowp(Model model){
        List<Deletproduct> deletproduct=adminservice.showremove();
        model.addAttribute("remove",deletproduct);
        return "removeshow";
    }

//    Remove Product img get
    @GetMapping("/imgremove/{id}")
    public ResponseEntity<byte[]> getimgremove(@PathVariable("id") int id) {
        Deletproduct deletproduct = adminservice.getremoveproductbyid(id);
        if (deletproduct.getImg() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG as per your image type
                    .body(deletproduct.getImg());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//**************** Replace Code*****************//

    @GetMapping("/replace")
    public String replaceproduct(Model model){

        List<Repalceorder> repalceorder = adminservice.getrepalceproduct();
        model.addAttribute("replace",repalceorder);

        return "Replace";
    }

    @GetMapping("/replacecheck/{id}")
    public String replacecheck(@PathVariable("id") int reid){

        adminservice.setreplacecheck(reid);

        return "redirect:/admin/replace";
    }

//****************COLORCREATE AND UPDATE AND DELETE*****************//

//    color create

    @GetMapping("/color/{id}")
    public String colorc(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "admincreateproductcolor";
    }

    @PostMapping("/do_color/{id}")
    public String colorr(@Valid @ModelAttribute Productcolor color,
                         BindingResult result,
                         @PathVariable("id") String id,
                         @RequestParam("i1") MultipartFile img1,
                         @RequestParam("i2") MultipartFile img2,
                         @RequestParam("i3") MultipartFile img3,
                         @RequestParam("iv1") MultipartFile imgv1, Model model) {

//        @RequestParam("v1") MultipartFile video1,
        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("color1",color);
            model.addAttribute("error",errors);
            model.addAttribute("id",id);
            return "admincreateproductcolor";
        }

        try {
            color.setImg1(img1.getBytes());
            color.setImg2(img2.getBytes());
            color.setImg3(img3.getBytes());
            color.setImgv1(imgv1.getBytes());
//            color.setVideo1(video1.getBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
        try {
            adminservice.savecolor(color, id);
            return "redirect:/admin/color/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    Update Color
    @GetMapping("/colorupd/{id}")
    public String colorupdate(@PathVariable("id") int id, Model model){
        Productcolor productcolor = adminservice.getproductcolor(id);
        model.addAttribute("colorup",productcolor);
        return "updatecolor";
    }

    @PostMapping("/do_colorupa/{id}/{cid}")
    public String colorupdatee(@ModelAttribute Productcolor color,
                               @PathVariable("id") String id,
                               @PathVariable("cid") int cid,
                               @RequestParam("i1") MultipartFile img1,
                               @RequestParam("i2") MultipartFile img2,
                               @RequestParam("i3") MultipartFile img3,
                               @RequestParam("iv1") MultipartFile imgv1){
//                               @RequestParam("v1") MultipartFile video1

        Productcolor pc=adminservice.getproductcolor(cid);

        try {
            if(!img1.isEmpty()){
                color.setImg1(img1.getBytes());
            }
            else {
                color.setImg1(pc.getImg1());
            }
            if(!img2.isEmpty()){
                color.setImg2(img2.getBytes());
            }
            else {
                color.setImg2(pc.getImg2());
            }
            if(!img3.isEmpty()){
                color.setImg3(img3.getBytes());
            }
            else {
                color.setImg3(pc.getImg3());
            }
            if(!imgv1.isEmpty()){
                color.setImgv1(imgv1.getBytes());
            }
            else {
                color.setImgv1(pc.getImgv1());
            }
//            if(!video1.isEmpty()){
//                color.setVideo1(video1.getBytes());
//            }
//            else {
//                color.setVideo1(pc.getVideo1());
//            }

        } catch (Exception e) {
            return e.getMessage();
        }
        try {
            adminservice.updatecolor(color, cid, id);
            return "redirect:/admin/color/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    Delete  color
    @GetMapping("/colordelete/{id}/{pid}")
    public String colordelet(@PathVariable("id") int id,
                             @PathVariable("pid") String pid){
         adminservice.deletcolor(id,pid);
        return "redirect:/admin/colorview/"+pid;
    }

//****************COLORSHOW*****************//
    //this id is product_id
    @GetMapping("/colorview/{id}")
    public String colorshow(@PathVariable("id") String id, Model model){
        List<Productcolor> color=adminservice.getProductColorByProductId(id);
        model.addAttribute("color1",color);
        model.addAttribute("id1",id);
        return "adminproductcolor";
    }



    //****************CREATEMENu AND UPDATE*****************//

    @GetMapping("/menu")
    public String menushow(Model model) {
        model.addAttribute("menulist", adminservice.getproductmenuList());
        return "adminproductmenushow";
    }

    @GetMapping("/createmenu")
    public String menuc() {
        return "adminmenu";
    }

    @PostMapping("/do_menucre")
    public String menucr(@Valid @ModelAttribute("menu") Productmenu productmenu,
                         BindingResult result,
                         @RequestParam("i1") MultipartFile img, Model model) {

        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("menu1",productmenu);
            return "adminmenu";
        }

        try {
            productmenu.setImg(img.getBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
        adminservice.productmenusave(productmenu);
        return "redirect:/admin/menu";
    }

//    update Menu

    @GetMapping("/menuup/{id}")
    public String menuup(@PathVariable("id") int id, Model model){
        Productmenu productmenu=adminservice.getmenu(id);
        model.addAttribute("menu",productmenu);
        return "updatemenu";
    }

    @PostMapping("/do_menuupd/{id}")
    public String menuupd(@Valid @ModelAttribute("menu") Productmenu productmenu,
                         BindingResult result,
                         @PathVariable("id") int id,
                         @RequestParam("i1") MultipartFile img, Model model) {

        Productmenu menuupdate=adminservice.getmenu(id);

        if(result.hasErrors()){
            Map<String,String> errors =new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("menu",menuupdate);
            return "updatemenu";
        }

        try {
            if(!img.isEmpty()){
                productmenu.setImg(img.getBytes());
            }
            else {
                productmenu.setImg(menuupdate.getImg());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        adminservice.productmenuupdate(productmenu,id);
        return "redirect:/admin/showall";
    }

//**************** Billshow Code*****************//

    @GetMapping("/billshow")
    public String billshow1(Model model){

        List<Finalorder> finalorders = userservice.getfinalorderall();
        model.addAttribute("finalo",finalorders);

        return "adminbillshow";
    }

//****************CREATEMOVIES AND UPDATE*****************//

//    create movie
    @GetMapping("/createmovie")
    public String moviesc() {
        return "admincreatemovie";
    }

    @Value("${upload.directory}")
    private String uploadDirectory;

    @PostMapping("/do_movie")
    public String moviescr(@Valid @ModelAttribute("movies") Movies movies,
                           BindingResult result,
                           @RequestParam("i1") MultipartFile img1,
                           @RequestParam("i2") MultipartFile img2,
                           @RequestParam("i3") MultipartFile img3,
                           @RequestParam("v1") MultipartFile tailer,
                           @RequestParam("v2") MultipartFile movievideo,Model model) throws Exception {

        if(result.hasErrors()){

            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            model.addAttribute("error",errors);
            model.addAttribute("movies1",movies);

            return "admincreatemovie";
        }

        try {
            movies.setImg1(img1.getBytes());
            movies.setImg2(img2.getBytes());
            movies.setImg3(img3.getBytes());
            movies.setTailer(tailer.getBytes());
            String fillname = movievideo.getOriginalFilename();

//        update fillname
            String extension = fillname.substring(fillname.lastIndexOf("."));
            String newfillname = UUID.randomUUID().toString() + extension;

            Path uploadopath = Paths.get(uploadDirectory, newfillname);

            // Create directories if not exist
            Files.createDirectories(uploadopath.getParent());

            // Save the file to the specified pat
            movievideo.transferTo(uploadopath);

            // Create movie URL for static content
            String videoUrl = "/video/" + newfillname;

            movies.setMovieurl(videoUrl);
        } catch (Exception e) {
            return e.getMessage();
        }
        adminservice.moviessave(movies);

        return "redirect:/admin/createmovie";
    }

//    update movie
    @GetMapping("/movieup/{id}")
    public String movieupd(@PathVariable String id, Model model){
        Movies movies= adminservice.getMovieById(id);
        model.addAttribute("movie", movies);
        return "updatemovie";
    }

    @PostMapping("/do_movieupd/{id}")
    public String moviesupd(@Valid @ModelAttribute("movies") Movies movies,
                           BindingResult result,
                           @PathVariable("id") String id,
                           @RequestParam("i1") MultipartFile img1,
                           @RequestParam("i22") MultipartFile img2,
                           @RequestParam("i3") MultipartFile img3,
                           @RequestParam("v11") MultipartFile tailer,
                           @RequestParam("v22") MultipartFile movievideo, Model model) throws Exception {

        Movies m=adminservice.getMovieById(id);
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            model.addAttribute("error",errors);
            model.addAttribute("movie",m);
            return "updatemovie";
        }

        try {
            if(!img1.isEmpty()){
                movies.setImg1(img1.getBytes());
            }
            else {
                movies.setImg1(m.getImg1());
            }
            if(!img2.isEmpty()){
                movies.setImg2(img2.getBytes());
            }
            else {
                movies.setImg1(m.getImg2());
            }
            if(!img3.isEmpty()){
                movies.setImg3(img3.getBytes());
            }
            else {
                movies.setImg3(m.getImg3());
            }
            if(!tailer.isEmpty()){
                movies.setTailer(tailer.getBytes());
            }
            else {
                movies.setTailer(m.getTailer());
            }
            if(!movievideo.isEmpty()){
                String fillname = movievideo.getOriginalFilename();

//        update fillname
                String extension = fillname.substring(fillname.lastIndexOf("."));
                String newfillname = UUID.randomUUID().toString() + extension;

                Path uploadopath = Paths.get(uploadDirectory, newfillname);

                Movies movies1 = adminservice.getMovieById(id);
                String existingFileUrl = movies1.getMovieurl();
                if (existingFileUrl != null && !existingFileUrl.isEmpty()) {
                    // Extract file name from the URL (if the URL contains a path)
                    String oldFileName = existingFileUrl.substring(existingFileUrl.lastIndexOf("/") + 1);
                    Path oldFilePath = Paths.get(uploadDirectory, oldFileName);
                    File oldFile = oldFilePath.toFile();

                    // Delete the old file if it exists
                    if (oldFile.exists()) {
                        boolean deleted = oldFile.delete();
                        if (!deleted) {
                            throw new Exception("Failed to delete old video file.");
                        }
                    }
                }
                // Create directories if not exist
                Files.createDirectories(uploadopath.getParent());

                // Save the file to the specified pat
                movievideo.transferTo(uploadopath);

                // Create movie URL for static content
                String videoUrl = "/video/" + newfillname;

                movies.setMovieurl(videoUrl);

            }
            else {
                movies.setMovieurl(m.getMovieurl());
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        adminservice.moviessave(movies);
        return "redirect:/admin/showall";
    }
//****************COUPON UPDATE*****************//

    @GetMapping("/couponup")
    public String couponupdate(Model model){

        Couponoff couponoff=userservice.getCouponoff();
        model.addAttribute("coupon",couponoff);

        return "updatecoupon";
    }

    @PostMapping("/do_couponup")
    public String couponupd(@Valid @ModelAttribute("couponoff") Couponoff couponoff,
                            BindingResult result, Model model){

        if (result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("coupon",couponoff);
            return "updatecoupon";
        }

        userservice.updatecoupon(couponoff);

        return "redirect:/admin/showall";
    }

//****************HEADOFFER CREATE AND UPDATE AND DELETE*****************//

    @GetMapping("/headoff")
    public String headoffc() {
        return "admincreateheadoff";
    }

    @PostMapping("/do_headoff")
    public String headooffcr(@Valid @ModelAttribute Headoff headoff,
                             BindingResult result,
                             @RequestParam("i1") MultipartFile img1, Model model) {

        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
//            lamda
//            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//            java
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            model.addAttribute("error",errors);
            model.addAttribute("offhead",headoff);
            return "admincreateheadoff";
        }

        try {
            headoff.setImg(img1.getBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
        adminservice.saveheadoff(headoff);
        return "redirect:/admin/headoff";
    }

//    Update Head Offer
    @GetMapping("/headup/{id}")
    public String headoffup(@PathVariable("id") int id, Model model){
        Headoff headoff=adminservice.getHeadOffById(id);
        model.addAttribute("headoffup",headoff);
        return "updateheadoff";
    }

    @PostMapping("/do_headoffupd/{id}")
    public String headooffupd(@Valid @ModelAttribute Headoff headoff,
                             BindingResult result,
                             @PathVariable("id") int id,
                             @RequestParam("i1") MultipartFile img1,Model model) {

        Headoff headoffup=adminservice.getHeadOffById(id);

        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            model.addAttribute("error",errors);
            model.addAttribute("headoffup",headoffup);
            return "updateheadoff";
        }

        try {
            if(!img1.isEmpty()){
                headoff.setImg(img1.getBytes());
            }
            else {
                headoff.setImg(headoffup.getImg());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        adminservice.headoffupdate(headoff,id);
        return "redirect:/admin/showall";
    }

//    Delete Headoff
    @GetMapping("/deletheadoff/{id}")
    public String deleteheadoff(@PathVariable("id") int id){
        adminservice.deleteheadoff(id);
        return "redirect:/admin/showall";
    }


//****************FINISHPRODUCTCOLOR*****************//

    @GetMapping("/finish")
    private String finishs(Model model){
        List<Productcolor> finishproduct = adminservice.getfinish();
        model.addAttribute("finish", finishproduct);
        return "adminfinishproduct";
    }

    //    show product,live offer,movies,head offer,menu list
    @GetMapping("/showall")
    public String showalll(Model model){

      // Live Offer get
        List<Product> product=adminservice.getliveOffer();
        model.addAttribute("liveoffer",product);

      //All Product get
        List<Product> productsall=adminservice.productviewall();
        model.addAttribute("allproduct",productsall);

      //All Movies get
        List<Movies> movies=adminservice.getAllMovies();
        model.addAttribute("allmovie",movies);

      //All Product Menu get
        List<Productmenu> productmenus=adminservice.getproductmenuList();
        model.addAttribute("allmenu",productmenus);

      //All Head Offer get
        List<Headoff> headoffs=adminservice.getallHeadOff();
        model.addAttribute("allheadoff",headoffs);

      //All Head Offer get
        Couponoff couponoff=userservice.getCouponoff();
        model.addAttribute("coupon",couponoff);

        return "adminshowproduct";
    }

}




