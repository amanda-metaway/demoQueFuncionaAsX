package com.example.demo.Service;


import com.example.demo.Dao.IBatisUserDao;


import com.example.demo.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private IBatisUserDao userIbatisUserDao;

    public void setUserDao(IBatisUserDao userDao) {
        this.userIbatisUserDao = userDao;
    }


    public User getUserById(int id) {
        return userIbatisUserDao.getUserById(id);
    }

    public void saveUser(User user) {
        userIbatisUserDao.saveUser(user);
    }
//nao fiz nenhum chamado do metodo listar
    public List<User> listarUsers() {
        return userIbatisUserDao.getListarUser();
    }

    public void updateUser(User user) {
        userIbatisUserDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userIbatisUserDao.deleteUser(id);
    }



}
