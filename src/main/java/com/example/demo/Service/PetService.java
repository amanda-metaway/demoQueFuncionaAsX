package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Model.Auditoria;
import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.apache.log4j.Logger;


@Service
public class PetService {
    // log4j
    private static final Logger logger = Logger.getLogger(PetService.class);


    @Autowired
    private IBatisPetDao batisPetDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private DataSourceTransactionManager transactionManagerPet;


    public Pet getPetById(int id) {
        logger.info("Buscando pet com ID: " + id);
        return batisPetDao.getPetById(id);
    }

    //save add mais pets a usuario ja cadastrado!
    public void saveMaisPet(Pet pet) {
        logger.info("Tentando salvar pet: " + pet);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManagerPet);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                batisPetDao.savePet(pet);
                // Auditoria
                // fazer como obter o CPF do usuário logado
                String cpfUsuario = pet.getUser().getCpfUsuario();

                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Cadastrou um novo pet");
                auditoriaService.saveAuditoria(auditoria);
                logger.info("Pet salvo com sucesso: " + pet);
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao salvar pet: " + e.getMessage(), e);
                throw new RuntimeException("Erro ao salvar pet: " + e.getMessage());
            }
            return null;
        });
    }


    public void updatePet(Pet pet) {
        logger.info("Tentando atualizar pet: " + pet);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                Integer userId = (Integer) session.getAttribute("user_id");

                if (userId != null) {
                    User user = userService.getUserById(userId);
                    if (user != null) {
                        logger.info("Atualizando pet: " + pet);
                        batisPetDao.updatePet(pet);
                        // Auditoria
                        String cpfUsuario = user.getCpfUsuario();
                        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Atualizou um pet");
                        auditoriaService.saveAuditoria(auditoria);
                        logger.info("Pet atualizado com sucesso: " + pet);
                    } else {
                        logger.warn("Usuário não encontrado ao tentar atualizar pet: " + pet);
                        throw new RuntimeException("Erro: Usuário não encontrado.");
                    }
                } else {
                    logger.warn("Usuário não está logado ao tentar atualizar pet: " + pet);
                    throw new RuntimeException("Erro: Usuário não está logado.");
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao atualizar pet: " + e.getMessage(), e);
                throw new RuntimeException("Erro ao atualizar pet: " + e.getMessage());
            }
            return null;
        });
    }


    public void deletePet(int id) {
        logger.info("Tentando deletar pet com ID: " + id);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                Pet pet = batisPetDao.getPetById(id);
                if (pet != null) {
                    batisPetDao.deletePet(id);

                    // audit
                    try {
                        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                        Integer userId = (Integer) session.getAttribute("user_id");

                        if (userId != null) {
                            User user = userService.getUserById(userId);
                            String cpfUsuario = user != null ? user.getCpfUsuario() : null; // logado

                            if (cpfUsuario != null) {
                                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Deletou um pet");
                                auditoriaService.saveAuditoria(auditoria);
                                logger.info("Pet deletado com sucesso: " + pet);
                            } else {
                                logger.warn("CPF do usuário não encontrado para auditoria.");
                            }
                        } else {
                            logger.warn("Usuário não está logado ao tentar auditar a exclusão do pet.");
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        logger.error("Erro ao registrar auditoria após exclusão de pet: " + e.getMessage(), e);
                        throw new RuntimeException("Erro ao registrar auditoria: " + e.getMessage());
                    }
                } else {
                    logger.warn("Pet não encontrado com ID: " + id);
                    throw new RuntimeException("Pet não encontrado.");
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                logger.error("Erro ao excluir pet: " + e.getMessage(), e);
                throw new RuntimeException("Erro ao excluir pet: " + e.getMessage());
            }
            return null;
        });
    }



    public List<Pet> listarPets (User user){
                logger.info("Listando pets para o usuário: " + user);
                return batisPetDao.getListarPet();
            }

            public List<Pet> listarPetsPorUsuario ( int userId){
                logger.info("Listando pets para o usuário com ID: " + userId);
                User user = userService.getUserById(userId);

                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                return transactionTemplate.execute(status -> {
                    try {
                        List<Pet> pets = batisPetDao.listarPetsPorUsuario(userId);
                        if (pets == null || pets.isEmpty()) {
                            logger.warn("Nenhum pet encontrado para o usuário com ID: " + userId);
                            throw new RuntimeException("Nenhum pet encontrado para o usuário.");
                        }
                        // Auditoria
                        String cpfUsuario = user.getCpfUsuario();
                        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Visualizou pets");
                        auditoriaService.saveAuditoria(auditoria);
                        logger.info("Pets listados com sucesso para o usuário: " + userId);
                        return pets;
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        logger.error("Erro ao listar pets: " + e.getMessage(), e);
                        throw new RuntimeException("Erro ao listar pets: " + e.getMessage());
                    }
                });
            }


            public void setPetDao (IBatisPetDao petDao){
                this.batisPetDao = petDao;
            }


            public UserService getUserService () {
                return userService;
            }

            public void setUserService (UserService userService){
                this.userService = userService;
            }

        }