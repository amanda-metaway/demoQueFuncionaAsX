package com.example.demo.Service;


import com.example.demo.Control.AuditoriaController;
import com.example.demo.Dao.IBatisAuditoriaDao;
import com.example.demo.Dao.IBatisUserDao;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AuditoriaService {

    @Autowired
    private IBatisAuditoriaDao auditoriaIbatisDao;


    public void saveAuditoria(AuditoriaController auditoriaController) {
        auditoriaController.setUser(auditoriaController.getUser());
        auditoriaController.setAcao(String.valueOf(auditoriaController.getAcao()));
        auditoriaController.setDataHora(LocalDateTime.now());
        auditoriaIbatisDao.saveAuditoria(auditoriaController);
    }

}
