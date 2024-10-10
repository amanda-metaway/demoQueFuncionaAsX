package com.example.demo.Service;


import com.example.demo.Dao.IBatisUserDao;


import com.example.demo.Model.User;

import com.example.demo.exception.PetShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IBatisUserDao userIbatisUserDao;


    public User getUserByCPF(String cpfUsuario) {
        return userIbatisUserDao.getUserByCPF(cpfUsuario);
    }

    public void saveUser(User user) {

        if (user.getCpfUsuario() == null || user.getCpfUsuario().isEmpty()) {
            throw new PetShopException("O CPF deve ser informado!");
        }


        String cpfSemMascara = user.getCpfUsuario().replaceAll("[^\\d]", "");
        user.setCpfUsuario(cpfSemMascara);

//        if (cpfSemMascara.length() != 11) {
//            throw new PetShopException("O CPF deve conter 11 dígitos numéricos!");
//        }

//busca
        if (getUserByCPF(user.getCpfUsuario()) != null) {
            throw new PetShopException("O CPF já está cadastrado!");
        }


        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new PetShopException("A senha deve ser informada!");
        }


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
