package com.example.demo.Dao;

import com.example.demo.Model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    User getUserById(int id);

    List<User> getListarUser();

    void updateUser(User user);

    void deleteUser(int id);
}
