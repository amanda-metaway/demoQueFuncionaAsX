package com.example.demo.Service;

import com.example.demo.Dao.IBatisAuditoriaDao;
import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

@Service
public class AuditoriaService {
    private static final Logger logger = Logger.getLogger(AuditoriaService.class);

    @Autowired
    private IBatisAuditoriaDao auditoriaDao;
    @Autowired
    private UserService userService;





    public AuditoriaService() {

    }

    public AuditoriaService(IBatisAuditoriaDao auditoriaDao) {
        this.auditoriaDao = auditoriaDao;
    }
    public List<Auditoria> buscarTodas() {
        return auditoriaDao.buscarTodas();
    }

    public void saveAuditoria(Auditoria auditoria) {
       logger.info("tentando salvar auditoria :" + auditoria);
        auditoriaDao.inserirAuditoria(auditoria);
    }


    public Auditoria createAuditoria(String cpfUsuario, String acao) {
        logger.info("tentando inserir auditoria :" + acao);
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
