package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


public class UserController {

    private UserService userService;


    public User getUser(int id) {
        return userService.getUserById(id);
    }


    public void createUser(User user) {
        userService.saveUser(user);
    }


    public void updateUser(User user) {
        userService.updateUser(user);
    }


    public void deleteUser(int id) {
        userService.deleteUser(id);
    }
}
