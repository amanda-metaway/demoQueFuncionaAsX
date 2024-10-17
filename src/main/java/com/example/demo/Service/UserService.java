package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Dao.IBatisUserDao;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;

import com.example.demo.exception.PetShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IBatisUserDao userIbatisUserDao;

    @Autowired
    private IBatisPetDao batisPetDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private UserService userServiceTransaction;



    public User getUserById(int id) {

        return userIbatisUserDao.getUserById(id);
    }

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
        //add transaction e auditoria
    }


    public void createUserAndPets(User user, List<Pet> pets) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {

                    if (user == null) {
                        throw new PetShopException("Usuário não pode ser nulo.");
                    }
                    if (pets == null || pets.isEmpty()) {
                        throw new PetShopException("Pelo menos um pet deve ser informado.");
                    }


                    userIbatisUserDao.saveUser(user);

                    for (Pet pet : pets) {
                        pet.setUser(user);
                        batisPetDao.savePet(pet);
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new PetShopException("Erro ao cadastrar usuário e pets: " + e.getMessage());
                }
                return null;
            }
        });
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


    public IBatisUserDao getUserIbatisUserDao() {
        return userIbatisUserDao;
    }

    public void setUserIbatisUserDao(IBatisUserDao userIbatisUserDao) {
        this.userIbatisUserDao = userIbatisUserDao;
    }


    public void setPetDao(IBatisPetDao petDao) {
    }
    @Autowired
    public void setUserServiceTransaction(UserService userServiceTransaction) {
        this.userServiceTransaction = userServiceTransaction;
    }

}
