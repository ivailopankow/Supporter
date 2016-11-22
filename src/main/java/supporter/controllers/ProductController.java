package supporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class ProductController {

    @GetMapping("/product/create")
    @SuppressWarnings("unused")
    public String createProduct(Model model) {
        model.addAttribute("view", "product/create");
        return "base-layout";
    }
}
