package com.udacity.jwdnd.course1.cloudstorage.Model.external;

public class NoteForm {
    private String title;
    private String description;

    public NoteForm(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
