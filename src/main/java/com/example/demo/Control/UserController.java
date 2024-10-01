package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
