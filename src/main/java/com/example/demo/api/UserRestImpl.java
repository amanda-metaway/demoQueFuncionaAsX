package com.example.demo.api;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.Service.PetService;
import com.example.demo.exception.PetShopException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class UserRestImpl implements UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Override
    public User createUser(User user) {
        try {
            if (userService.saveUser(user) <= 0) {
                throw new PetShopException("Não foi possível realizar a operação. Contate o suporte");
            }
            return user; // Retorna o usuário criado
        } catch (PetShopException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getUser(Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }
        return user;
    }

    @Override
    public User updateUser(Integer id, User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    @Override
    public List<User> listUsers() {
        return userService.listarUsers();
    }

    @Override
    public void createUserAndPet(User user, Pet pet) {
        try {
            userService.createUserAndPetService(user, pet);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuário e pet: " + e.getMessage());
        }
    }

    @Override
    public void savePet(Pet pet) {
        try {
            userService.savePet(pet);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pet: " + e.getMessage());
        }
    }
}