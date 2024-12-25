package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.Finalorder;
import E_Shop.Shoping.reposisory.FinalorderRepo;
import E_Shop.Shoping.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminnormal")
public class Delivery {

    @Autowired
    private Userservice userservice;

    @Autowired
    private FinalorderRepo finalorderRepo;

    @GetMapping("/deliveryboy")
    public String delivweryboy(Model model){

        List<Finalorder> finalorders = userservice.getfinalorderall();
        model.addAttribute("finalo",finalorders);

        return "deliveryordershow";
    }

    @GetMapping("/billone/{id}")
    public String delivweryboybillid(@PathVariable("id") int bid , Model model){

        Finalorder finalorder = userservice.getfinalorderbyid(bid);
        List<Finalorder> finalorders = new ArrayList<>();
        finalorders.add(finalorder);
        model.addAttribute("finalo",finalorders);

        return "deliveryordershow";
    }

    @GetMapping("/otpgenrate/{fid}")
    public String otpgenreated(@PathVariable("fid") int fbillid, Model model){
        model.addAttribute("ffid",fbillid);
        return "deliveryotp";
    }

    @PostMapping("/do_deliveryotp/{fid}")
    public String dootp(@RequestParam("delieveryotp") int otp,
                        @PathVariable("fid") int billid, Model model){
        String otp1 = String.valueOf(otp);
        Finalorder finalorder = userservice.getfinalorderbyid(billid);

        if(!otp1.equals(finalorder.getOtp())){
            model.addAttribute("error","Please Entere the currect OTP");
            return "deliveryotp";
        }

        finalorder.setOrderok(true);
        finalorderRepo.save(finalorder);

        return "redirect:/adminnormal/deliveryboy";
    }
}
