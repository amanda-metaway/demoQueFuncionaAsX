package com.example.demo.Service;

import com.example.demo.Converter.DateConverter;
import com.example.demo.Model.Auditoria;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RelatorioService {

    @Autowired
    private AuditoriaService auditoriaService;

    private List<Auditoria> auditorias;





    public void gerarRelatorioPDF(List<Auditoria> auditorias) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Cria aqui o documento
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            document.add(new Paragraph("Registro de Auditoria")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // A tabela em si
            Table table = new Table(6); // 6 colunas
            table.addHeaderCell("Ação");
            table.addHeaderCell("Data e Hora");
            table.addHeaderCell("User ID");
            table.addHeaderCell("Nome");
            table.addHeaderCell("CPF");
            table.addHeaderCell("PERFIL");

            // Conversor de data
            DateConverter dateConverter = new DateConverter();

            // Adiciona os dados
            for (Auditoria auditoria : auditorias) {
                table.addCell(auditoria.getAcao());
                String formattedDate = dateConverter.getAsString(null, null, auditoria.getDataHora());
                table.addCell(formattedDate);
                table.addCell(String.valueOf(auditoria.getUserIdValue()));
                table.addCell(auditoria.getUserId().getName());
                table.addCell(auditoria.getUserId().getCpfUsuario());

                String perfil = auditoria.getUserId().getPerfil() != null ? auditoria.getUserId().getPerfil().getPerfil() : "N/A";
                table.addCell(perfil);
            }

            document.add(table);
            document.close();

            // Para o PDF
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




    public void gerarRelatorioCsv(List<Auditoria> auditorias) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Ação,Data e Hora,User ID,Nome,CPF,PERFIL\n");

        // conversor da ta aqui tbm
        DateConverter dateConverter = new DateConverter();

        for (Auditoria auditoria : auditorias) {
            csvBuilder.append(escaparCampo(auditoria.getAcao())).append(",");
            // formatar aqui a data
            String formattedDate = dateConverter.getAsString(null, null, auditoria.getDataHora());
            csvBuilder.append(escaparCampo(formattedDate)).append(",");
            csvBuilder.append(escaparCampo(String.valueOf(auditoria.getUserIdValue()))).append(",");
            csvBuilder.append(escaparCampo(auditoria.getUserId().getName())).append(",");
            csvBuilder.append(escaparCampo(auditoria.getUserId().getCpfUsuario())).append("\n");
        }

        // para o CSV
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"auditoria_report.csv\"");
        response.setContentLength(csvBuilder.length());

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(csvBuilder.toString().getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            facesContext.responseComplete();
        }
    }


    private String escaparCampo(String campo) {
        String escapedCampo = campo.replace("\"", "\"\"");
        return "\"" + escapedCampo + "\"";
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
