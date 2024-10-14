package com.example.demo.Control;

import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AuditoriaController {
    private int id;
    private User userId;
    private String acao;
    private LocalDateTime dataHora;


    @Autowired
    private UserService userService;
    @Autowired
    private AuditoriaService auditoriaService;


    @Autowired
    public AuditoriaController(UserService userService, AuditoriaService auditoriaService) {
        this.userService = userService;
        this.auditoriaService = auditoriaService;
    }

    public AuditoriaController() {
    }

    public Auditoria createAuditoria(String cpfUsuario, String acao) {
        User user = userService.getUserByCPF(cpfUsuario);
        if (user == null) {
            return null;
        }

        Auditoria auditoria = new Auditoria();
        auditoria.setUserId(user);
        auditoria.setAcao(acao);
        auditoria.setDataHora(LocalDateTime.now());
        auditoriaService.saveAuditoria(auditoria);
        System.out.println("Auditado: " + auditoria.getUserId().getCpfUsuario() + " " + auditoria.getAcao() + " " + auditoria.getDataHora());
        return auditoria;
    }


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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }


}
