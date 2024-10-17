package com.example.demo.Control;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import com.example.demo.exception.PetShopException;
import com.ibatis.sqlmap.client.SqlMapExecutor;
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
    private UserService userService;
    @Autowired
    private PetService petService;



    private UserController userController;


    private User user = new User();
    private List<User> users;
    private User buscaUser;
    private String mensagemSucesso;





    public UserController() {
        this.users = new ArrayList<>();
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }


    public String createUser() {
        try {
            if(userService.saveUser(this.user) <= 0){
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
            userService.createUserAndPetService(user, pet);//chamando o serviço aquii

            //a mensagem tem que ser na tela
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


    public void prepararBusca() {
        buscaUser = getUserByCPF(user.getCpfUsuario());
    }


    public void updateUser(User user) {
        userService.updateUser(user);
    }


    public void deleteUser(int id) {
        userService.deleteUser(id);
    }

    public List<User> listarUsers() {
        return users = userService.listarUsers();
    }

    public List<User> getUsers() {
        return users;
    }


    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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


    public void setUserServiceTransaction(UserService userServiceTransaction) {
    }

    public void setPetController(String petController) {
    }

    public PetController getPetController() {
        return petController;
    }

    public void setPetController(PetController petController) {
        this.petController = petController;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }


    public UserService getUserService() {
        return userService;
    }


}
