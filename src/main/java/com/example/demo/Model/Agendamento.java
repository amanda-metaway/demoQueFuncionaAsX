package com.example.demo.Model;

import java.time.LocalDateTime;

public class Agendamento {

    private Integer id;
    private Servico servicoId;
    private User userId;
    private LocalDateTime dataHora;
    private StatusAgendamento status;

    public Agendamento() {
    }

    public Agendamento(Integer id, Servico servicoId, User userId, LocalDateTime dataHora, StatusAgendamento status) {
        this.id = id;
        this.servicoId = servicoId;
        this.userId = userId;
        this.dataHora = dataHora;
        this.status = status;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servico getServicoId() {
        return servicoId;
    }

    public void setServicoId(Servico servicoId) {
        this.servicoId = servicoId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}


