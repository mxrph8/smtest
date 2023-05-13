package com.example.godplesedothis;

import androidx.annotation.NonNull;

public class University {
    private int id;
    private String name;
    private String faculty;
    private int points;

    private String description;

    public University(int id, String name, String faculty, int points, String description) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.points = points;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getPoints() {
        return points;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + faculty + ", " + points + " баллы для поступления)";
    }
}
