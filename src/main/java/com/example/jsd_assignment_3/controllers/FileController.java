package com.example.jsd_assignment_3.controllers;

import com.example.jsd_assignment_3.entities.File;
import com.example.jsd_assignment_3.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/file")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/upload")
    public String doAddWithImage(File file, RedirectAttributes flashSession, @RequestParam(name = "upload_file") MultipartFile multipartFile) {
        if (fileService.save(file, multipartFile)) {
            flashSession.addFlashAttribute("success", "Upload successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Upload failed");
        }
        return "redirect:/home/";
    }



}
