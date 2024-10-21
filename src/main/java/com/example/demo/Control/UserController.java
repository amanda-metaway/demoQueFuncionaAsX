package com.example.demo.Control;


import com.example.demo.Model.Auditoria;
import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import com.example.demo.exception.PetShopException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController {

    @Autowired
    private PetController petController;
    @Autowired
    private AuditoriaService auditoriaService;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;


    private User user = new User();
    private List<User> users;
    private User buscaUser;
    private String cpfUsuario;
    private boolean editing;
    private String mensagemSucesso;


    public UserController() {
        this.users = new ArrayList<>();
    }


    //só para perfil admin esta sem uso -teria que fazer a pg que oadmin criar  usuarios
    public String createUser() {
        try {
            if (userService.saveUser(this.user) <= 0) {
                throw new PetShopException("Não foi possivel realizar a operação. Contate o suporte");
            }
            this.user = new User();
            PetShopException.userCreatedSuccessfully();
            return "pagina de sucesso-que ainda nao tem";
        } catch (PetShopException e) {
            PetShopException.addErrorMessage(e.getMessage());
        } catch (Exception e) {
            PetShopException.userCreationError();
        }
        return null;
    }


    public void createUserAndPetController() {

        Pet pet = petController.getPet();

        if (user == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não pode ser nulo!", null));
            return;
        }

        try {
            userService.createUserAndPetService(user, pet);//controller chamando o serviço aquii


            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário e pets cadastrados com sucesso!", null));

            resetUser();
            petController.resetPet();

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usuário e pets: " + e.getMessage(), null));
            e.printStackTrace();
        }
    }


    public void resetUser() {
        this.user = new User();
        this.petController.resetPet();
    }


    public User getUserByCPF(String cpfUsuario) {
        return userService.getUserByCPF(cpfUsuario);
    }


    public String prepararBusca() {
        if (cpfUsuario == null || cpfUsuario.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("CPF não pode ser vazio!!", null));
            return null; // fica na mesma página
        }

        buscaUser = getUserByCPF(cpfUsuario);
        if (buscaUser == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Usuário não encontrado"));
        }
        return null; // fica na mesma página
    }

    public String prepararEdicao() {
        System.out.println("Método prepararEdicao chamado.");
        editing = true;//ativo a edicao
        return null;//namesma pg
    }

    public String cancelarEdicao() {
        editing = false;//desativ a edit
        return "index-home";
    }

    public void updateUser(User user) {
        userService.updateUser(user);
        editing = false;//desativa a edit
    }


//    public void deleteUser(int id) {
//        userService.deleteUser(id);
//    }


    public List<User> listarUsers() {
        return users = userService.listarUsers();
    }



    public void editar(Integer id) {
        try {
            buscaUser = userService.getUserById(id);
            editing = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void excluir(Integer id) {
        try {
            buscaUser = userService.getUserById(id);
            userService.deleteUser(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public User getBuscaUser() {
        return buscaUser;
    }

    public void setBuscaUser(User buscaUser) {
        this.buscaUser = buscaUser;
    }


    public String getMensagemSucesso() {
        return mensagemSucesso;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    public void setUserService(Object userService) {
    }

    public void setPetService(Object petService) {
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }
}
