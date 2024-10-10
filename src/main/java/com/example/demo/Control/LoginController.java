package com.example.demo.Control;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class LoginController {
    private String cpfUsuario;
    private String password;
    private boolean manterConectado;

    private User user;
    private UserService userService;

    public String login() {
        if (isValidUser(cpfUsuario, password)) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("usuario", cpfUsuario);
            if (manterConectado) {
                // manter conec e cokies
            }
            return "paginaSucesso"; // ainda nao tem
        } else {
            //add msg erro
            FacesContext.getCurrentInstance().addMessage(null,
                    new javax.faces.application.FacesMessage("CPF ou Senha inválidos!"));
            return "login"; // volta p login
        }
    }

    private boolean isValidUser(String cpf, String password) {
        User user = userService.getUserByCPF(cpf);//service dando null
        return user != null && user.getPassword().equals(password);
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManterConectado() {
        return manterConectado;
    }

    public void setManterConectado(boolean manterConectado) {
        this.manterConectado = manterConectado;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
