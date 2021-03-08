package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.external.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.internal.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public final class StorageService {
    private FileService fileService;
    private NoteService noteService;

    public StorageService(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    public String storeFile(MultipartFile file, Authentication authentication) {
        return fileService.storeFile(file, authentication);
    }

    public File[] allFiles(Authentication authentication) {
        return fileService.allFiles(authentication);
    }

    public Boolean deleteFile(Integer fileId) {
        return fileService.deleteFile(fileId);
    }

    public File getFile(Integer fileId) {
        return fileService.getFile(fileId);
    }

    public Boolean storeNote(NoteForm noteForm, Authentication authentication) {
        return noteService.storeNote(noteForm, authentication);
    }

    public Note[] allNotes(Authentication authentication) {
        return noteService.allNotes(authentication);
    }
}

/*
    private Integer id;
    private String title;
    private String description;
    private Integer userId;
 */