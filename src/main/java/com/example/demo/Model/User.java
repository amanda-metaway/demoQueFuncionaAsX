package com.example.demo.Model;

public class User {
    private int id;
    private String name;
    private UserProfile perfil_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserProfile getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(UserProfile perfil_id) {
        this.perfil_id = perfil_id;
    }
}
