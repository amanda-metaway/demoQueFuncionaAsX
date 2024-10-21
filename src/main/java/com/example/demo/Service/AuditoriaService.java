package com.example.demo.Service;

import com.example.demo.Dao.IBatisAuditoriaDao;
import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaService {
    @Autowired
    private IBatisAuditoriaDao auditoriaDao;
    @Autowired
    private UserService userService;


    public AuditoriaService() {

    }
    public AuditoriaService(IBatisAuditoriaDao auditoriaDao) {
        this.auditoriaDao = auditoriaDao;
    }


    public void saveAuditoria(Auditoria auditoria) {
        System.out.println("Auditoria: " + auditoria);
        auditoriaDao.inserirAuditoria(auditoria);
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

        return auditoria;
    }



    public void setAuditoriaDao(IBatisAuditoriaDao auditoriaDao) {
    }



    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
