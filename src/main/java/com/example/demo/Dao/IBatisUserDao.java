package com.example.demo.Dao;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class IBatisUserDao extends SqlMapClientDaoSupport implements UserDao {


    private SqlMapClientTemplate sqlMapClientTemplate;



    @Override
    public void saveUser(User user) {
        System.out.println("USUARIO SALVO!");
    }

    @Override
    public User getUserById(int id) {
        return (User) getSqlMapClientTemplate().queryForObject("getUserById", id);
    }

    @Override
    public List<User> getListarUser() {
        return new ArrayList<>();
    }

    @Override
    public void updateUser(User user) {
        getSqlMapClientTemplate().update("updateUser", user);
    }

    @Override
    public void deleteUser(int id) {
        getSqlMapClientTemplate().delete("deleteUser", id);
    }


}
