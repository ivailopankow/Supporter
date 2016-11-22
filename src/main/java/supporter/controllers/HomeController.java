package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import supporter.models.Product;
import supporter.repositories.ProductRepository;

import java.util.List;

/**
 * Created by Ivaylo on 30-Oct-16.
 */
@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("view", "home/index");
        List<Product> allProducts = productRepository.findAll();
        model.addAttribute("products", allProducts);
        return "base-layout";
    }
}
