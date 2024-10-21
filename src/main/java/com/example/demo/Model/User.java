package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String name;
    private UserProfile perfil;
    private String cpfUsuario;
    private String password;
    private String contato;
    private List<Pet> pets = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getPerfil() {
        return perfil;
    }

    public void setPerfil(UserProfile perfil) {
        this.perfil = perfil;
    }


}
