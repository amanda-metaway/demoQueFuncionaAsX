package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Dao.IBatisUserDao;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IBatisUserDao userIbatisUserDao;


    public User getUserById(int id) {
        return userIbatisUserDao.getUserById(id);
    }

    public void saveUser(User user) {
        userIbatisUserDao.saveUser(user);
    }

    public List<User> listarUsers() {
        return userIbatisUserDao.getListarUser();
    }


    public void updateUser(User user) {
        userIbatisUserDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userIbatisUserDao.deleteUser(id);
    }


    public void setUserDao(IBatisUserDao userDao) {
        this.userIbatisUserDao = userDao;
    }


}
