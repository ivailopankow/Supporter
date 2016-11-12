package supporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ivaylo on 30-Oct-16.
 */
@Controller
public class HomeController {
    private static final String HOME_PAGE = "index";

    @RequestMapping("/")
    public String homePage(){
        return HOME_PAGE;
    }
}
