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

    private PetService petService;



    public User getUserById(int id) {

        return userIbatisUserDao.getUserById(id);
    }

    public User getUserByCPF(String cpfUsuario) {
        return userIbatisUserDao.getUserByCPF(cpfUsuario);
    }


    //save basico
    /*se der fazer um managed bean para a transaction
     * */
    public Integer saveUser(User user) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        return transactionTemplate.execute((TransactionCallback<Integer>) status -> {
            if (user.getCpfUsuario() == null || user.getCpfUsuario().isEmpty()) {
                throw new PetShopException("O CPF deve ser informado!");
            }
            String cpfSemMascara = user.getCpfUsuario().replaceAll("[^\\d]", "");
            user.setCpfUsuario(cpfSemMascara);
            if (getUserByCPF(user.getCpfUsuario()) != null) {
                throw new PetShopException("O CPF já está cadastrado!");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new PetShopException("A senha deve ser informada!");
            }

            Integer id = null;

            userIbatisUserDao.saveUser(user);//esse saveUser é da outra camada

            if (user.getId() <= 0) {
                status.setRollbackOnly();//se der erro ele cancela a op
                throw new PetShopException("Usuario nao cadastrado");
            }

            id = user.getId();
            //auditoria-tem que injetar antes
            return id;
        });

    }


    public void createUserAndPetService(User user, Pet pet) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                if (user == null) {
                    throw new PetShopException("Usuário não pode ser nulo.");
                }
                if (pet == null) {
                    throw new PetShopException("Pelo menos um pet deve ser informado.");
                }

                //ja na camada de servicço ,levando para dao ,salvando retorna o id do save para esse obj settar o id
                user.setId(saveUser(user));//faz tudo isso para trazer o i por aqui
                if (user.getId() <= 0) {
                    throw new PetShopException("Usuario nao cadastrado");
                }

                pet.setId(savePet(pet));
                if (pet.getId() <= 0) {
                    throw new PetShopException("Pet nao cadastrado");
                }
                pet.setUser(user);
                System.out.println("os dois  : " + pet.getNome() + user.getName());



            } catch (Exception e) {
                status.setRollbackOnly();
                throw new PetShopException("Erro ao cadastrar usuário e pets: " + e.getMessage());
            }
            return null;
        });
    }

    //save para pet cadastro
    public Integer savePet(Pet pet) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute((TransactionCallback<Integer>) status -> {
            //id inicializdo aqui
            Integer id = null;
            batisPetDao.savePet(pet);//salvamento la na outra camada

            if (pet.getId() <= 0) {
                status.setRollbackOnly();
                throw new PetShopException("Pet nao cadastrado");
            }
            id = pet.getId();
            //auditoria aqui
            return id;
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

    public PetService getPetService() {
        return petService;
    }

    public void setPetService(PetService petService) {
        this.petService = petService;
    }
}
