package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int createNote(NoteForm form, String userName) {
        final User user = userService.getUserByName(userName);
        final Note note = formToNote(form);
        note.setUserid(user.getUserId());
        return noteMapper.insert(note);
    }

    public void deleteNote(Integer id) {
        noteMapper.delete(id);
    }

    public void updateNote(NoteForm form) {
        final Note note = formToNote(form);
        noteMapper.update(note);
    }

    public List<Note> getAllNotes(String userName) {
        final User user = userService.getUserByName(userName);
        if (user != null) {
            return noteMapper.getUserNotes(user.getUserId());
        }
        return new ArrayList<>();
    }

    private Note formToNote(NoteForm form) {
        return new Note(
                form.getId(),
                form.getTitle(),
                form.getDescription(),
                null
        );
    }
}
