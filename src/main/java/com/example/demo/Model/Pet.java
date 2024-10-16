package com.example.demo.Model;


public class Pet {
    private int id;
    private String nome;
    private String raca;
    private User user;
    private String donoNome;
    private String donoContato;



    
    public String getDonoNome() {
        return donoNome;
    }

    public void setDonoNome(String donoNome) {
        this.donoNome = donoNome;
    }

    public String getDonoContato() {
        return donoContato;
    }

    public void setDonoContato(String donoContato) {
        this.donoContato = donoContato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Pet() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
