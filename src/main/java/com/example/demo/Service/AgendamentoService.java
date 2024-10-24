package com.example.demo.Service;

import com.example.demo.Model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.Model.Agendamento;
import com.example.demo.Model.TipoServico;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;


public class AgendamentoService {
    @Autowired
    private Servico servico;

    private List<Agendamento> agendamentos = new ArrayList<>();

    public Agendamento realizarAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
        return agendamento;
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentos;
    }

    public Agendamento buscarAgendamentoTipoServico(TipoServico tipoServico) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getServicoId().getTipoServico().equals(tipoServico.getId()))
                .findFirst()
                .orElse(null);
    }

    public void cancelarAgendamento(Integer id) {
        agendamentos.removeIf(agendamento -> agendamento.getId().equals(id));
    }

    public List<Agendamento> listarAgendamentosPorUsuario(Integer userId) {
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getUserId().getId().equals(userId))
                .collect(Collectors.toList());
    }

    // Verifica a disponibilidade de horários para um tipo de serviço em uma data específica
    public List<LocalDateTime> listarHorariosDisponiveis(TipoServico tipoServico, LocalDateTime data) {
        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();

        // Defina o intervalo de horários disponíveis, por exemplo, das 8h às 18h
        for (int i = 8; i <= 18; i++) {
            LocalDateTime horario = data.withHour(i).withMinute(0);
            if (isHorarioDisponivel(horario, tipoServico)) {
                horariosDisponiveis.add(horario);
            }
        }

        return horariosDisponiveis;
    }

    // Verifica se o horário está disponível
    private boolean isHorarioDisponivel(LocalDateTime horario, TipoServico tipoServico) {
        // Verifique se já existe um agendamento para esse horário e tipo de serviço
        return agendamentos.stream()
                .noneMatch(agendamento -> agendamento.getDataHora().equals(horario) &&
                        agendamento.getServicoId().getTipoServico().equals(tipoServico.getId()));
    }
}

