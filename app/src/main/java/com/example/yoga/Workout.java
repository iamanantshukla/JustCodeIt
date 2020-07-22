package com.example.yoga;

public class Workout {

    private String title;
    private String description;

    public Workout(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Workout() {
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
