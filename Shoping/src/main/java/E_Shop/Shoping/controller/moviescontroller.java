package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.*;
import E_Shop.Shoping.service.Homeservice;
import E_Shop.Shoping.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class moviescontroller {

    @Autowired
    private Userservice userservice;

    @Autowired
    private Homeservice homeservice;

//    show movies
    @GetMapping("/movie/{id}")
    public String movies1(@PathVariable("id") String mid, Model model, Principal principal){

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

        Movies movies = homeservice.getmoviebtmid(mid);
        model.addAttribute("movie",movies);

        if (principal!=null){
            User user = userservice.getuserbyid(principal.getName());
            if(user.getEstore_point()>=movies.getCoupon_price()){
                model.addAttribute("buy",0);
            }
            else {
                model.addAttribute("buy",1);
            }
        }
        else {
            model.addAttribute("buy",1);
        }

        List<Movies> moviesList = homeservice.getmovieall(mid);
        Collections.shuffle(moviesList);
        List<Movies> moviesList1 = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            moviesList1.add(moviesList.get(i));
        }
        model.addAttribute("movies",moviesList1);

        return "movieshow";
    }

    @GetMapping("/tailer/{id}")
    public String tailer(@PathVariable("id") String id, Model model){

        model.addAttribute("mid1", id);

        return "videopage";
    }

    @GetMapping("/user/movieshow/{id}")
    public String movieshow1(@PathVariable("id") String id, Model model){

        Movies movies = homeservice.getmoviebtmid(id);
        model.addAttribute("movieUrl", movies.getMovieurl());
        model.addAttribute("md1", id);

        return "movieshowvideo";
    }

    @GetMapping("/user/buymovie")
    public String buymovies1(Principal principal, Model model){

        List<Buymovies> buymovies = homeservice.getmoviebyuserid(principal.getName());
        model.addAttribute("buymovie",buymovies);

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

        return "buymovies";
    }

    @GetMapping("/moviedelete/{id}")
    public String deletemovies(@PathVariable("id") int id){

        homeservice.deletebuymovie(id);

        return "redirect:/user/buymovie";
    }

//    buy movies and save buymovies
    @GetMapping("/user/buymovies/{id}")
    public String buymovies2(@PathVariable("id") String id, Principal principal){

        User user = userservice.getuserbyid(principal.getName());
        Movies movies = homeservice.getmoviebtmid(id);

        Buymovies buymovies = homeservice.findbuymoviebymidanduid(movies.getMovie_id(), user.getUsername());
        if(buymovies==null){
            if (user.getEstore_point()>=movies.getCoupon_price()){
                homeservice.savebuymovie(id,principal.getName());
            }else {
                return "redirect:/movie/"+id;
            }
        }

        return "redirect:/user/buymovie";
    }
}
