package com.example.demo.Model;


import java.time.LocalDateTime;

public class Auditoria {
    private int id;
    private User userId;
    private String acao;
    private LocalDateTime dataHora;







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getUserIdValue() {
        return userId != null ? userId.getId() : null;
    }


}
