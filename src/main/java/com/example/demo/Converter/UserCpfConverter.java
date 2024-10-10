package com.example.demo.Converter;

import com.example.demo.exception.PetShopException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class UserCpfConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return value != null ? value.replaceAll("[^\\d]", "") : null;
        } catch (Exception e) {
            PetShopException.genericSupportError();
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (value != null) {
                String cpfUsuario = value.toString();
                return formatoCpf(cpfUsuario);
            }
            return "Ops, CPF inválido ou inexistente";
        } catch (Exception e) {
            PetShopException.genericSupportError();
            System.err.println(e.getMessage());
            return "Erro ao exibir CPF";
        }
    }

    private String formatoCpf(String cpfUsuario) {
        if (cpfUsuario != null && cpfUsuario.length() == 11) {
            return cpfUsuario.replaceFirst("(\\d{3})(\\d)", "$1.$2") // ponto
                    .replaceFirst("(\\d{3})(\\d)", "$1.$2") // ponto
                    .replaceFirst("(\\d{3})(\\d{1,2})$", "$1-$2"); // traço
        }
        return cpfUsuario != null ? cpfUsuario : "";
    }
}
