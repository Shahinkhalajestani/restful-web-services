package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.models.User;

public class PostModel {
    private String description;
    private User user;

    public PostModel() {
    }

    public PostModel(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
