package com.example.demo.Converter;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter implements Converter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm:ss");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                return LocalDateTime.parse(value, DATE_FORMATTER);
            }
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (value instanceof LocalDateTime) {
                return DATE_FORMATTER.format((LocalDateTime) value);
            }
            return "";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Erro ao exibir data";
        }
    }
}

