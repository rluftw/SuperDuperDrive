package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;

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

    public Boolean storeNote(Note note, Authentication authentication) {
        return noteService.storeNote(note, authentication);
    }

    public Note[] allNotes(Authentication authentication) {
        return noteService.allNotes(authentication);
    }

    public Boolean deleteNote(Integer noteId) {
        return noteService.deleteNote(noteId);
    }
}
