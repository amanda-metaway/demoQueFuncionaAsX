package com.example.demo.Model;
public enum UserProfile {
    ADMIN(1),
    CLIENTE(2);


    private final int id;

    UserProfile(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}