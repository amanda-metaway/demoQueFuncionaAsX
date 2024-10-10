package com.example.demo.Validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CpfValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String cpfUsuario = (String) value;

        //  foi informado
        if (cpfUsuario == null || cpfUsuario.isEmpty()) {
            throw new ValidatorException(new FacesMessage("O CPF deve ser informado!"));
        }

        // remov a mascara
        String cpfSemMascara = cpfUsuario.replaceAll("[^\\d]", "");
        if (cpfSemMascara.length() != 11) {
            throw new ValidatorException(new FacesMessage("O CPF deve conter 11 dígitos numéricos!"));
        }


    }
}
