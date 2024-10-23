package com.example.demo.Model;

import com.example.demo.Model.TipoServico;

import javax.persistence.*;
import java.time.Duration;


public class Servicos {
    private int id;
    private TipoServico tipo;
    private String descricao;
    private double valor;
    private Duration duracaoEstimado;
    private User userId;

    public Servicos() {
    }


        public Servicos(int id, TipoServico tipo, String descricao, double valor, Duration duracaoEstimado, User userId) {
            this.id = id;
            this.tipo = tipo;
            this.descricao = descricao;
            this.valor = valor;
            this.duracaoEstimado = duracaoEstimado;
            this.userId = userId;
        }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public Duration getDuracaoEstimado() {
        return duracaoEstimado;
    }

    public void setDuracaoEstimado(Duration duracaoEstimado) {
        this.duracaoEstimado = duracaoEstimado;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
