package com.example.demo.Control;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.exception.PetShopException;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController {

    @Autowired
    private PetController petController;


    private UserController userController;

    private UserService userServiceTransaction;


    private User user = new User();
    private List<User> users;
    private User buscaUser;
    private String mensagemSucesso;


    private UserService userService;


    public UserController() {
        this.users = new ArrayList<>();
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }


    public String createUser() {
        try {
            userService.saveUser(this.user);
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

//    public void createUserAndPets() {
//        // registros
//        List<Pet> petsToRegister = new ArrayList<>();
//
//        // pegar pet do formulario
//        Pet pet = petController.getPet();
//        if (pet != null) {
//            petsToRegister.add(pet);
//        }
//
//
//        userServiceTransaction.createUserAndPets(userController.getUser(), petsToRegister);
//
//        // Reseta os formulários ou fornece feedback ao usuário
//        userController.resetUser(); // Método para resetar o usuário
//        petController.resetPet(); // Método para resetar o pet
//    }



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

    public UserService getUserServiceTransaction() {
        return userServiceTransaction;
    }


    public UserService getUserService() {
        return userService;
    }
}
