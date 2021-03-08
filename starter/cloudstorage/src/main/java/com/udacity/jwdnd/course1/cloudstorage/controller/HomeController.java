package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.storage.StorageService;
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
    public String getHome(Note note, Credential credential, Model model, Authentication authentication) {
        model.addAttribute("files", storageService.allFiles(authentication));
        model.addAttribute("notes", storageService.allNotes(authentication));
        model.addAttribute("credentials", storageService.allCredentials(authentication));
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
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) {
        String errorMessage = storageService.storeFile(file, authentication);
        if (errorMessage == null) {
            model.addAttribute("successful", true);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "result";
    }

    @PostMapping("/noteUpload")
    public String handleNoteUpload(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        model.addAttribute("successful", storageService.storeNote(note, authentication));
        return "result";
    }

    @GetMapping("/deleteNote/{id}")
    public String handleNoteDelete(@PathVariable(value = "id") Integer noteId, Model model) {
        model.addAttribute("successful", storageService.deleteNote(noteId));
        return "result";
    }

    @PostMapping("/credentialsUpload")
    public String handleCredentialUpload(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model) {
        model.addAttribute("successful", storageService.storeCredential(credential, authentication));
        return "result";
    }

    @GetMapping("/deleteCredential/{id}")
    public String handleCredentialDelete(@PathVariable(value = "id") Integer credentialId, Model model) {
        model.addAttribute("successful", storageService.deleteCredential(credentialId));
        return "result";
    }
}
