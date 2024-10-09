package com.example.demo.Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class UserCpfConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // tira o que nao é numero e retorna o meu valor
        //ex:123.456.789-09, retorna 12345678909.
        return value != null ? value.replaceAll("[^\\d]", "") : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //se existe poe a mascara
        //traz o valor do banco limpo ,mas para a tela insere a mascara
        if (value != null) {
            String cpfUsuario = value.toString();
            return formatoCpf(cpfUsuario);
        }
        return "Ops cpf invalido ou nao existente";
    }

    private String formatoCpf(String cpfUsuario) {
        //  mascara
        if (cpfUsuario.length() == 11) {
            return cpfUsuario.replaceFirst("(\\d{3})(\\d)", "$1.$2") // ponto
                    .replaceFirst("(\\d{3})(\\d)", "$1.$2") // ponto
                    .replaceFirst("(\\d{3})(\\d{1,2})$", "$1-$2"); // traço
        }
        return cpfUsuario;
    }
}
