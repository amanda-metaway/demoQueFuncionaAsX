package com.example.demo.Service;


import com.example.demo.Model.Auditoria;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;





public class RelatorioService {

    @Autowired
    private AuditoriaService auditoriaService;

    private List<Auditoria> auditorias;





    public void gerarRelatorioPDF(List<Auditoria> auditorias) {
        Connection conexao = null;

        try {
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testeAppDemo", "postgres", "postgres");

            InputStream inputStream = getClass().getResourceAsStream("/templateRelatorio.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);



            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Registro de Auditoria");

            List<Map<String, ?>> dados = new ArrayList<>();
            for (Auditoria auditoria : auditorias) {
                Map<String, Object> reg = new HashMap<>();
                reg.put("acao", auditoria.getAcao());
                reg.put("data_hora", auditoria.getDataHora().toString());
                reg.put("user_id", auditoria.getUserId().getId());
                reg.put("name", auditoria.getUserId().getName());
                reg.put("cpf_usuario", auditoria.getUserId().getCpfUsuario());
                reg.put("perfil", auditoria.getUserId().getPerfil().toString());
                dados.add(reg);
            }
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dados);



            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"auditoria_relatorio.pdf\"");
            response.setContentLength(outputStream.size());

            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao gerar relatório: " + e.getMessage(), null));
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }








    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<Auditoria> auditorias) {
        this.auditorias = auditorias;
    }
}
