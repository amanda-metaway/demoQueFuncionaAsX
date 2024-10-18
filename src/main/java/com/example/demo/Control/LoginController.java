package com.example.demo.Control;


import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class LoginController {

    private UserProfile perfil;
    private UserProfile perfilSelecionado;
    private String cpfUsuario;
    private String password;
    private boolean manterConectado;


    private UserService userService;




    @Autowired
    private User user;


    @Autowired
    private AuditoriaService auditoriaService;






    @PostConstruct
    public void init() {
        checkManterConectado();
    }


    public void checkManterConectado() {
        Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("usuario")) {
                    cpfUsuario = cookie.getValue();
                    password = cookie.getValue();// nao armazenar  senha em  cookie-apenas para teste
                    System.out.println("Usuario  conectado: " + cpfUsuario);
                }
            }

        } else {
            limparCampos();
        }
    }


    public String login() {
        user = isValidUser(cpfUsuario, password);
        if (user != null) {
            perfil = user.getPerfil();
        }
        if (!perfil.equals(perfilSelecionado)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new javax.faces.application.FacesMessage("Perfil não autorizado!"));
            return "logout";
        }



        manterConectado = isManterConectado();
        //login sessao
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("usuario", cpfUsuario);
        session.setAttribute("perfil", perfil);
        session.setAttribute("senha", password);
        session.setAttribute("login", manterConectado);
        //manter o user_id para o pet depois
        HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("user_id", user.getId());  // Aqui está a modificação




        // audita
        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuario LOGOU no sistema");
        if (auditoria != null && auditoria.getUserId() != null) {
            auditoriaService.saveAuditoria(auditoria);
            System.out.println("Auditado: " + auditoria.getUserId().getCpfUsuario() + " " + auditoria.getAcao() + " " + auditoria.getDataHora());
        } else {
            System.out.println("Erro: auditoria não foi criada ou usuário não encontrado.");
        }


        if (manterConectado) {
            Cookie cookie = new Cookie("usuario", cpfUsuario);
            Cookie cookie2 = new Cookie("senha", password);//apenas para teste
            Cookie cookie3 = new Cookie("perfil", perfil.toString());

            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");//no caso esta para toda a aplic

            cookie2.setMaxAge(30 * 24 * 60 * 60);
            cookie2.setPath("/");

            cookie3.setMaxAge(30 * 24 * 60 * 60);
            cookie3.setPath("/");

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).addCookie(cookie);

        } else {
            limparCampos();
        }
        return redirecionarHome(perfil);
    }


    private User isValidUser(String cpfUsuario, String password) {
        User user = userService.getUserByCPF(cpfUsuario);
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
            //audita
            Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuario DESconectou do sistema");
            if (auditoria != null && auditoria.getUserId() != null) {
                auditoriaService.saveAuditoria(auditoria);
                System.out.println("Auditado: " + auditoria.getUserId().getCpfUsuario() + " " + auditoria.getAcao() + " " + auditoria.getDataHora());
            } else {
                System.out.println("Erro: auditoria não foi criada ou usuário não encontrado.");
            }
            // rem cookies
            Cookie cookieUsuario = new Cookie("usuario", null);
            cookieUsuario.setMaxAge(0);
            cookieUsuario.setPath("/");

            Cookie cookieSenha = new Cookie("senha", null);
            cookieSenha.setMaxAge(0);
            cookieSenha.setPath("/");

            Cookie cookiePerfil = new Cookie("perfil", null);
            cookiePerfil.setMaxAge(0);
            cookiePerfil.setPath("/");

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addCookie(cookieUsuario);
            response.addCookie(cookieSenha);
            response.addCookie(cookiePerfil);


            limparCampos();
        }
        return "logout";
    }

    private User limparCampos() {
        manterConectado = false;
        cpfUsuario = null;
        password = null;
        return user = null;
    }

    private String redirecionarHome(UserProfile perfil) {
        if (perfil.equals(UserProfile.ADMIN)) {
            System.out.println("Redirecionando para página de acesso Admin");
            return "paginaAdmin";
        } else if (perfil.equals(UserProfile.CLIENTE)) {
            System.out.println("Redirecionando para página de acesso Cliente");
            return "paginaCliente";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new javax.faces.application.FacesMessage("Erro inesperado! Contate o suporte."));
            limparCampos();
            return "logout";
        }
    }

    public String indexHome() {
        HttpSession session3 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UserProfile perfil = (UserProfile) session3.getAttribute("perfil");
        return redirecionarHome(perfil);
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


    public UserProfile getPerfilSelecionado() {
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(UserProfile perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }

    public UserProfile getPerfil() {
        return perfil;
    }

    public void setPerfil(UserProfile perfil) {
        this.perfil = perfil;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }


    public void setPetService(Object petService) {
    }
}
