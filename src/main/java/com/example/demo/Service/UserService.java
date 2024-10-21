package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Dao.IBatisUserDao;
import com.example.demo.Model.Auditoria;
import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.exception.PetShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
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
    private AuditoriaService auditoriaService;

    @Autowired
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
            String cpfUsuario = user.getCpfUsuario();

            //audita
            Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuario Cadastrado");

                auditoriaService.saveAuditoria(auditoria);


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
                pet.setUser(user);//vincula eles
                pet.setId(savePet(pet));
                if (pet.getId() <= 0) {
                    throw new PetShopException("Pet nao cadastrado");
                }

                String cpfUsuario = user.getCpfUsuario();
                //audita
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Cadastrou Pet");

                    auditoriaService.saveAuditoria(auditoria);




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
            Integer id = null;

            batisPetDao.savePet(pet);//salvamento la na outra camada

            if (pet.getId() <= 0) {
                status.setRollbackOnly();
                throw new PetShopException("Pet nao cadastrado");
            }

            id = pet.getId();

            return id;
        });

    }


    public List<User> listarUsers() {
        return userIbatisUserDao.getListarUser();
    }


    public void updateUser(User user) {
        userIbatisUserDao.updateUser(user);
        String cpfUsuario = user.getCpfUsuario();

        //audita
        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "ATUALIZOU USUARIO");
            auditoriaService.saveAuditoria(auditoria);

    }

    public void deleteUser(int id) {
        User userToDelete = userIbatisUserDao.getUserById(id);

        if (userToDelete == null) {
            throw new PetShopException("Usuário não encontrado para exclusão.");
        }

        String cpfUsuario = userToDelete.getCpfUsuario();
        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuário Deletado");

        try {

            if (auditoria != null) {
                auditoriaService.saveAuditoria(auditoria);
            }


            userIbatisUserDao.deleteUser(id);

        } catch (Exception e) {
            throw new PetShopException("Erro ao excluir usuário: " + e.getMessage());
        }
    }


    public void setUserDao(IBatisUserDao userDao) {
        this.userIbatisUserDao = userDao;
    }


    public void setPetDao(IBatisPetDao petDao) {
    }


}
