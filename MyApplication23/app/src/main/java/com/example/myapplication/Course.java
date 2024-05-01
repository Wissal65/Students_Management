package com.example.myapplication;



public class Course {
    private String name;
    private String duration;
    private int photoResourceId; // Resource ID of the course photo

    public Course() {
    }

    public Course(String name, String duration, int photoResourceId) {
        this.name = name;
        this.duration = duration;
        this.photoResourceId = photoResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public int getPhotoResourceId() {
        return photoResourceId;
    }
}
