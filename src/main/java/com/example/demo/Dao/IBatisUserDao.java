package com.example.demo.Dao;


import com.example.demo.Model.User;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import java.util.List;

public class IBatisUserDao extends SqlMapClientDaoSupport implements UserDao {


    @Override
    public void saveUser(User user) {

        getSqlMapClientTemplate().insert("saveUser", user);
    }

    @Override
    public User getUserByCPF(String cpfUsuario) {
        return (User) getSqlMapClientTemplate().queryForObject("getUserByCPF", cpfUsuario);
    }

    @Override
    public void updateUser(User user) {
        getSqlMapClientTemplate().update("updateUser", user);
    }

    @Override
    public void deleteUser(int id) {
        getSqlMapClientTemplate().delete("deleteUser", id);
    }

    @Override
    public List<User> getListarUser() {
        return getSqlMapClientTemplate().queryForList("listarUsers");
    }


}
