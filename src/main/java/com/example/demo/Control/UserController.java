package com.example.demo.Control;


import com.example.demo.Model.User;
import com.example.demo.Service.UserService;


public class UserController {


    private UserService userService;

    public UserController() {

    }

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public User getUser(int id) {
        return userService.getUserById(id);
    }


    public void createUser(User user) {
        userService.saveUser(user);
    }


    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void deleteUser(int id) {
        userService.deleteUser(id);
    }
}
