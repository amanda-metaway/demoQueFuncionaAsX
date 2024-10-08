package com.example.demo.Model;


public class Pet {
    private int id;
    private String nome;
    private String raca;
    private String dono;
    private String donoContato;

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

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getDonoContato() {
        return donoContato;
    }

    public void setDonoContato(String donoContato) {
        this.donoContato = donoContato;
    }

    public Pet() {
    }

//    public Pet(String nome, String raca, String dono, String donoContato) {
//        this.nome = nome;
//        this.raca = raca;
//        this.dono = dono;
//        this.donoContato = donoContato;
//    }



    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", dono='" + dono + '\'' +
                ", donoContato='" + donoContato + '\'' +
                '}';
    }
}
