package com.example.demo.Control;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Service.UserService;

import java.util.ArrayList;
import java.util.List;


public class UserController {

    private User user = new User();
    private List<User> users;

    private UserService userService;


    public UserController() {
        this.users = new ArrayList<>();
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }


    public void createUser() {
        userService.saveUser(this.user);
        this.user = new User();

    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }



    public void deleteUser(int id) {
        userService.deleteUser(id);
    }

    public List<User> listarUsers() {
       return  users = userService.listarUsers();
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
}
