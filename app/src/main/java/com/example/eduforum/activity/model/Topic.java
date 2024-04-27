package com.example.eduforum.activity.model;

public class Topic {
    private String id;
    private String name;
    private boolean isDepartment;

    public Topic() {}

    public Topic(String id, String name, boolean isDepartment) {
        this.id = id;
        this.name = name;
        this.isDepartment = isDepartment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDepartment() {
        return isDepartment;
    }

    public void setDepartment(boolean department) {
        isDepartment = department;
    }
}
