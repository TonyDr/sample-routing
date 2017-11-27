package ru.tony.sample.routing.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CountryController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
