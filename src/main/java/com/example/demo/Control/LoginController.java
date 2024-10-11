package com.example.demo.Control;

import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController {

    private UserProfile perfil;
    private UserProfile perfilSelecionado;
    private String cpfUsuario;
    private String password;
    private boolean manterConectado;
    private AuditoriaController auditoriaController;



    private User user;
    private UserService userService;
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

        System.out.println("========================================================================================================================================");
        System.out.println("Usuario conectado na sessao: " + " cpf = " + " " + cpfUsuario +  " " + "  perfil acesso = " + perfil.toString() + " " +  " mantem sessao = "+ " " + manterConectado);
        System.out.println("========================================================================================================================================");

        System.out.println("======AUDITORIA======");
        auditoriaService.saveAuditoria(auditoriaController);
        System.out.println("Auditado:" + "  " + getAuditoriaController().getUser().getCpfUsuario() + "  " + getAuditoriaController().getAcao().toString() + "   " + getAuditoriaController().getDataHora().toString() + "  ");
        System.out.println("=================================================");

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

        if (perfil.equals(UserProfile.ADMIN)) {
            System.out.println("Redirecionando para pagina acesso Admin");
            return "paginaAdmin";
        } else if (perfil.equals(UserProfile.CLIENTE)) {
            System.out.println("Redirecionando para pagina acesso Cliente");
            return "paginaCliente";

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new javax.faces.application.FacesMessage("Erro inesperado!Contate o suporte."));
            limparCampos();
            return "logout";
        }
    }


    private User isValidUser(String cpfUsuario, String password) {
        User user = userService.getUserByCPF(cpfUsuario);
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("Usuario DESconectou: " + cpfUsuario);
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
        return user =  null;
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

    public AuditoriaController getAuditoriaController() {
        return auditoriaController;
    }

    public void setAuditoriaController(AuditoriaController auditoriaController) {
        this.auditoriaController = auditoriaController;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

}
