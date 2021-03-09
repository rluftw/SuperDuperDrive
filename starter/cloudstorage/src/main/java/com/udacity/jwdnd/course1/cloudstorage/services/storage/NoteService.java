package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.POJO.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public List<Note> allNotes(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        return noteMapper.getAllNotes(userId);
    }

    public Boolean storeNote(NoteForm noteForm, Authentication authentication) {
        Note note = new Note(
                noteForm.getId(),
                noteForm.getTitle(),
                noteForm.getDescription(),
                userService.getUser(authentication.getName()).getId()
        );

        Integer rowsAffected;
        if (note.getId() == null) {
            rowsAffected = noteMapper.addNote(note);
        } else {
            rowsAffected = noteMapper.updateNote(note);
        }
        return rowsAffected > 0;
    }

    public Boolean deleteNote(Integer noteId) {
        Integer rowsChanged = noteMapper.deleteNote(noteId);
        return rowsChanged > 0;
    }
}