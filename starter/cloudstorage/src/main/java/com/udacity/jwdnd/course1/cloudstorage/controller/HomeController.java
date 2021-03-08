package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.external.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.internal.File;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class HomeController {
    private final StorageService storageService;

    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/home")
    public String getHome(NoteForm chatForm, Model model, Authentication authentication) {
        model.addAttribute("files", storageService.allFiles(authentication));
        model.addAttribute("notes", storageService.allNotes(authentication));
        return "home";
    }

    @GetMapping("/deleteFile/{id}")
    public String handleFileDelete(@PathVariable(value = "id") Integer fileId, Model model) {
        model.addAttribute("successful", storageService.deleteFile(fileId));

        return "result";
    }

    @GetMapping("/file/{id}")
    public void handleFileRequest(@PathVariable(value = "id") Integer fileId, Model model, HttpServletResponse response) {
        File file = storageService.getFile(fileId);
        response.setContentType(file.getContentType());

        InputStream is = new ByteArrayInputStream(file.getFileData());
        try {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/fileUpload")
    public String handleUpload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) {
        String errorMessage = storageService.storeFile(file, authentication);
        if (errorMessage == null) {
            model.addAttribute("successful", true);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "result";
    }

    @PostMapping("/noteUpload")
    public String handleNoteUpload(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, Model model) {
        return "result";
    }
}
