package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.User;
import E_Shop.Shoping.reposisory.Productcolorrepo;
import E_Shop.Shoping.reposisory.Productrepo;
import E_Shop.Shoping.service.Userservice;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class registerlogin {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Userservice userservice;

    @Autowired
    private Productcolorrepo productcolorrepo;

    @Autowired
    private Productrepo productrepo;

    @GetMapping("/register")
    public String userc(Model model) {

        return "register";
    }

    @PostMapping("/do_register")
    public String usercr(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         @RequestParam("password1") String newpassword, Model model){

        if (userservice.getuserbyuserid(user.getUsername()) == false){
            System.out.println("1");
            model.addAttribute("error2","This username is already in use.");
            return "register";
        }

            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError error : result.getFieldErrors()) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("error", errors);
                model.addAttribute("user", user);
                return "register";
            }


        if (passwordEncoder.matches(newpassword, user.getPassword())){
            model.addAttribute("user",user);
            model.addAttribute("error1","Not same ConfingPassword");
            return "register";
        }
        user.setHome_no(5000);
        userservice.saveuser(user);

        return "login";
    }

    @GetMapping("/login")
    public String loginc() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutuser(HttpServletResponse response){
        Cookie JSESSIONID=new Cookie("JSESSIONID",null);
        JSESSIONID.setMaxAge(0);
        response.addCookie(JSESSIONID);

        return "redirect:/login?logout=true";
    }

//    @GetMapping("/home1")
//    public String home(Model model){
//
//        Product p=productrepo.findById("darshan").get();
//        LocalDateTime time=p.getEndtime();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        String endtimes=time.format(formatter);
//        model.addAttribute("endtime",endtimes);
//        System.out.printf(endtimes);
//        return "index";
//    }


//    Query in Reposistory
//    @GetMapping("/home12")
//    public String try12(Model model) {
//        List<Productcolor> colors = productcolorrepo.findLowStockProducts(10); // 10 કરતાં ઓછી ક્વેન્ટિટી માટે
////        System.out.println(colors);
//        model.addAttribute("color",colors);
//        return "cart";
//    }
//    <div th:each="entry : ${color}">
//    <h2 th:text="${entry.quntity}"></h2>
//    <h2 th:text="${entry.color_id}"></h2>
//    <h2 th:text="${entry.product.product_id}"></h2>
//</div>
}
