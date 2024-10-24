package com.example.demo.Service;


import com.example.demo.Model.Auditoria;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import org.springframework.beans.factory.annotation.Autowired;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;




public class RelatorioService {

    @Autowired
    private AuditoriaService auditoriaService;

    private List<Auditoria> auditorias;





    public void gerarRelatorioPDF(List<Auditoria> auditorias) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/relatorio.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(auditorias);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Registro de Auditoria");


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
