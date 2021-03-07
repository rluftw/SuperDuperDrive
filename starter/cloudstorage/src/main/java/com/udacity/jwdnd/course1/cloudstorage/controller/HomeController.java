package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
    private final StorageService storageService;

    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/home")
    public String getHome(Model model, Authentication authentication) {
        model.addAttribute("files", storageService.allFiles(authentication));
        return "home";
    }

    @PostMapping("/home")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) {
        String errorMessage = storageService.store(file, authentication);
        file = null;
        if (errorMessage == null) {
            model.addAttribute("successfulUpload", true);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "result";
    }

    @GetMapping("/deleteFile/{id}")
    public String handleFileDelete(@PathVariable(value = "id") String fileId) {
        storageService.deleteFile(Integer.getInteger(fileId));

        return "home";
    }
}
