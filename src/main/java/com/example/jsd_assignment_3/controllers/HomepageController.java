package com.example.jsd_assignment_3.controllers;

import com.example.jsd_assignment_3.entities.File;
import com.example.jsd_assignment_3.entities.Setting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomepageController {

    @GetMapping("/")
    public String getHomepage(Model model){
        File file = new File();
        Setting setting = new Setting();
        model.addAttribute("upload_file", file);
        model.addAttribute("setting", setting);
        return "home";
    }



}
