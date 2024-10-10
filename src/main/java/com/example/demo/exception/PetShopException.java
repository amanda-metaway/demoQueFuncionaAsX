package com.example.demo.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class PetShopException extends RuntimeException {

    public PetShopException(String message) {
        super(message);
    }

    public static void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }

    public static void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
    }

    //  mensagens pre definidas
    public static void userCreatedSuccessfully() {
        addSuccessMessage("Usuário cadastrado com sucesso!");
    }

    public static void userCreationError() {
        addErrorMessage("Erro ao cadastrar usuário, contate o suporte");
    }





    public static void genericSupportError() {
        addErrorMessage("Erro na operacao, contate o suporte");
    }


}
