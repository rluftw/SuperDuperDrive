package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.external.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public Note[] allNotes(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        return noteMapper.getAllNotes(userId);
    }

    public Boolean storeNote(NoteForm noteForm, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        Integer rowsInserted = noteMapper.addNote(new Note(
                null,
                noteForm.getTitle(),
                noteForm.getDescription(),
                userId)
        );
        return rowsInserted > 0;
    }
}