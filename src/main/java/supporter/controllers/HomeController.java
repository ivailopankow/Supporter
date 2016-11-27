package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.models.Product;
import supporter.services.product.ProductService;

import java.util.List;

/**
 * Created by Ivaylo on 30-Oct-16.
 */
@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "home/index";
    }

    @RequestMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }
}
