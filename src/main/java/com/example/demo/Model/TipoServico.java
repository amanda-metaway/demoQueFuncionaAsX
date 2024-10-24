package com.example.demo.Model;

import java.math.BigDecimal;
import java.time.Duration;

public class TipoServico {
    private Integer id;
    private String nome;
    private BigDecimal valor;
    private Duration duracaoEstimado;

    public TipoServico() {
    }

    public TipoServico(Integer id, String nome, BigDecimal valor, Duration duracaoEstimado) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.duracaoEstimado = duracaoEstimado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Duration getDuracaoEstimado() {
        return duracaoEstimado;
    }

    public void setDuracaoEstimado(Duration duracaoEstimado) {
        this.duracaoEstimado = duracaoEstimado;
    }
}
