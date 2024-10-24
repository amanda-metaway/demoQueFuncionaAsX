package com.example.demo.Service;

import com.example.demo.Model.Servico;
import com.example.demo.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ServicoService {
    private List<Servico> servicos = new ArrayList<>();



    public List<Servico> listarServicosRealizados() {
        return servicos;
    }


    public Servico buscarServicoPorUser(User userId) {
        return servicos.stream()
                .filter(servico -> servico.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }





}

