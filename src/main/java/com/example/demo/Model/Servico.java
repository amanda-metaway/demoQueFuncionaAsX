package com.example.demo.Model;

public class Servico {
    private Integer id;
    private TipoServico tipoServico;
    private User userId;

    public Servico() {
    }

    public Servico(Integer id, TipoServico tipoServico, User userId) {
        this.id = id;
        this.tipoServico = tipoServico;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
