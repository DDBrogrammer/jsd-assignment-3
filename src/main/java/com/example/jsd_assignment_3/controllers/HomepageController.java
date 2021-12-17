package com.example.jsd_assignment_3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomepageController {

    @GetMapping("/")
    public String getHomepage(){
        return "home";
    }



}
