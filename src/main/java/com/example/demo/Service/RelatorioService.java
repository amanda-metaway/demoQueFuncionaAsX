package com.example.demo.Service;

import com.example.demo.Model.Auditoria;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioService {

@Autowired
private AuditoriaService auditoriaService;

private List<Auditoria> auditorias;


    public void gerarRelatorio(List<Auditoria> auditorias) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            //cria aqui documento
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // titulo
            document.add(new Paragraph("Registro de Auditoria").setBold().setFontSize(20));

            // a tabela em si
            Table table = new Table(3);
            table.addHeaderCell("Ação");
            table.addHeaderCell("Data e Hora");
            table.addHeaderCell("Usuário");

            // ela  com dados
            for (Auditoria auditoria : auditorias) {
                table.addCell(auditoria.getAcao());
                table.addCell(auditoria.getDataHora().toString());
                table.addCell(String.valueOf(auditoria.getUserIdValue()));
            }

            document.add(table);
            document.close();


            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"auditoria_report.pdf\"");
            response.setContentLength(outputStream.size());


            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();
            facesContext.responseComplete();

        } catch (IOException e) {
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

