package com.example.demo.Validators;

import com.example.demo.Model.User;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

public class CpfValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String cpfUsuario = (String) value;


            // apenas dig
//            if (!cpfUsuario.matches("\\d+")) {
//                throw new ValidatorException(new FacesMessage("este campo deve conter apenas números!"));
//            }

            // numero de dig
//            if (cpfUsuario.length() != 11) {
//                throw new ValidatorException(new FacesMessage("cpf inválido!"));
//            }
    }
}
