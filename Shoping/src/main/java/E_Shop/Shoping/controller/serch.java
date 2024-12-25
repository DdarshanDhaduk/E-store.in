package E_Shop.Shoping.controller;

import E_Shop.Shoping.model.Finalorder;
import E_Shop.Shoping.model.Product;
import E_Shop.Shoping.service.Homeservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:63342")
@RestController
//@RequestMapping("/)
public class serch {

    @Autowired
    private Homeservice homeservice;

    //************* Search Code ***************//

    @GetMapping("/search")
    public List<Product> searchbase(@RequestParam("keyword") String name) {
        System.out.println(name);
        List<Product> products = homeservice.getsearchproduct(name);
        System.out.println(products.size());
        return products;
    }

    @GetMapping("/search1")
    public List<Finalorder> getfinalorderts(@RequestParam("keyword") String name) {
        System.out.println(name);
        List<Finalorder> finalorders = homeservice.getsearchfinalorder(name);
        System.out.println(finalorders.size());
        return finalorders;
    }

}
