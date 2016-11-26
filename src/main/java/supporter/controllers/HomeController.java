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

    private static final String PRODUCTS = "products";
    @Autowired
    ProductService productService;

    @GetMapping(Routes.DELIMITER)
    public String index(Model model) {
        model.addAttribute(Routes.VIEW, Routes.HOME_INDEX);
        List<Product> allProducts = productService.findAll();
        model.addAttribute(PRODUCTS, allProducts);
        return Routes.BASE_LAYOUT;
    }

    @RequestMapping("/error/403")
    public String accessDenied(Model model) {
        model.addAttribute("view", "error/403");
        return Routes.BASE_LAYOUT;
    }
}
