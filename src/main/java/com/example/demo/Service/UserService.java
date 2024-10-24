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

import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Service
public class UserService {

    // log4j
    private static final Logger logger = Logger.getLogger(UserService.class);

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

//aqui
    public User getUserById(int id) {
        logger.info("Buscando usuario com ID: " + id);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(status -> {
            User user = userIbatisUserDao.getUserById(id);
            if (user == null) {
                logger.warn("Usuario nao encontrado");
                throw new PetShopException("Usuário não encontrado.");
            }
            return user;
        });
    }

    public User getUserByCPF(String cpfUsuario) {
        logger.info("Buscando usuario com cpf: " + cpfUsuario);
        return userIbatisUserDao.getUserByCPF(cpfUsuario);
    }


    //save basico
    /*se der fazer um managed bean para a transaction
     * */
    public Integer saveUser(User user) {
        logger.info("Tentando salvar usuario : " + user.getCpfUsuario());
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        return transactionTemplate.execute((TransactionCallback<Integer>) status -> {
            if (user.getCpfUsuario() == null || user.getCpfUsuario().isEmpty()) {
                logger.warn("O CPF deve ser informado!");
                throw new PetShopException("O CPF deve ser informado!");
            }
            String cpfSemMascara = user.getCpfUsuario().replaceAll("[^\\d]", "");
            user.setCpfUsuario(cpfSemMascara);
            if (getUserByCPF(user.getCpfUsuario()) != null) {
                logger.warn("O CPF já está cadastrado!");
                throw new PetShopException("O CPF já está cadastrado!");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                logger.warn("A senha deve ser informado!");
                throw new PetShopException("A senha deve ser informada!");
            }

            Integer id = null;

            userIbatisUserDao.saveUser(user);//esse saveUser é da outra camada

            if (user.getId() <= 0) {
                status.setRollbackOnly();//se der erro ele cancela a op
                logger.error("Usuario nao cadastrado");
                throw new PetShopException("Usuario nao cadastrado");
            }

            id = user.getId();
            String cpfUsuario = user.getCpfUsuario();

            //audita
            Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuario Cadastrado");
            auditoriaService.saveAuditoria(auditoria);
            logger.info("Usuario cadastrado com sucesso");
            return id;
        });

    }


    public void createUserAndPetService(User user, Pet pet) {
        logger.info("Tentando cadastrar usuario/pet: " + user.getCpfUsuario() + pet.getNome());
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                if (user == null) {
                    logger.warn("Usuario nao pode ser nulo");
                    throw new PetShopException("Usuário não pode ser nulo.");
                }
                if (pet == null) {
                    logger.warn("Pelo menos um pet deve ser informado.");
                    throw new PetShopException("Pelo menos um pet deve ser informado.");
                }

                //ja na camada de servicço ,levando para dao ,salvando retorna o id do save para esse obj settar o id
                user.setId(saveUser(user));//faz tudo isso para trazer o i por aqui
                if (user.getId() <= 0) {
                    logger.warn("Usuario nao cadastrado");
                    throw new PetShopException("Usuario nao cadastrado");
                }
                pet.setUser(user);//vincula eles
                pet.setId(savePet(pet));
                if (pet.getId() <= 0) {
                    logger.warn("Pet nao cadastrado");
                    throw new PetShopException("Pet nao cadastrado");
                }

                String cpfUsuario = user.getCpfUsuario();
                //audita
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Cadastrou Pet");
                auditoriaService.saveAuditoria(auditoria);
                logger.info("Pet cadastrado com sucesso");
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao cadastrar usuário e pets: " + e.getMessage());
                throw new PetShopException("Erro ao cadastrar usuário e pets: " + e.getMessage());
            }
            return null;
        });
    }

    //save para pet cadastro
    public Integer savePet(Pet pet) {
        logger.info("Tentando salvar Pet: " + pet.getNome());
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute((TransactionCallback<Integer>) status -> {
            Integer id = null;
            batisPetDao.savePet(pet);//salvamento la na outra camada
            if (pet.getId() <= 0) {
                status.setRollbackOnly();
                logger.error("Pet nao cadastrado");
                throw new PetShopException("Pet nao cadastrado");
            }
            id = pet.getId();
            return id;
        });

    }


    public List<User> listarUsers() {
        logger.info("Tentando listar usuarios");
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(status -> {
            try {
                List<User> users = userIbatisUserDao.getListarUser();
                if (users == null || users.isEmpty()) {
                    logger.warn("Nenhum usuario encontrado");
                    throw new PetShopException("Nenhum usuário encontrado.");
                }
                return users;
            } catch (Exception e) {
                status.setRollbackOnly();  // mesmo em leitura?
                logger.error("Erro ao listar usuarios: " + e.getMessage());
                throw new PetShopException("Erro ao listar usuários: " + e.getMessage());
            }
        });
    }


    public void updateUser(User user) {
        logger.info("Tentando atualizar usuario: " + user.getCpfUsuario());
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                userIbatisUserDao.updateUser(user);
                String cpfUsuario = user.getCpfUsuario();

                // Auditoria
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "ATUALIZOU USUARIO");
                auditoriaService.saveAuditoria(auditoria);
                logger.info("Usuario atualizado com sucesso");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "usuario Atualizado/Editado com com sucesso!", null));
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao atualizar usuario: " + e.getMessage());
                throw new PetShopException("Erro ao atualizar usuário: " + e.getMessage());
            }
            return null;
        });
    }

    public void deleteUser(int id) {
        logger.info("Tentando deletar usuario: " + id);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                User userToDelete = userIbatisUserDao.getUserById(id);
                if (userToDelete == null) {
                    throw new PetShopException("Usuário não encontrado para exclusão.");
                }

                String cpfUsuario = userToDelete.getCpfUsuario();
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Usuário Deletado");


                if (auditoria != null) {
                    auditoriaService.saveAuditoria(auditoria);

                }

                userIbatisUserDao.deleteUser(id);
                logger.info("Usuario deletado com sucesso");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "usuario deletado com com sucesso!", null));
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao deletar usuario: " + e.getMessage());
                throw new PetShopException("Erro ao excluir usuário: " + e.getMessage());
            }
            return null;
        });
    }


    public void setUserDao(IBatisUserDao userDao) {
        this.userIbatisUserDao = userDao;
    }


    public void setPetDao(IBatisPetDao petDao) {
    }


}
