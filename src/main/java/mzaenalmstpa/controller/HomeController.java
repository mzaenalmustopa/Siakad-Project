package mzaenalmstpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("pages/main");
    }

    @GetMapping("/home")
    public ModelAndView home(){
        return new ModelAndView("pages/home");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        return new ModelAndView("pages/home");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("pages/login");
    }

    @GetMapping("/contact")
    public ModelAndView contact(){
        return new ModelAndView("pages/contact");
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        return new ModelAndView("pages/profile");
    }
}
