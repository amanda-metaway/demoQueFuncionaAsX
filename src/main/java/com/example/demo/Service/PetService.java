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
import java.util.logging.Logger;


@Service
public class PetService {
//atualiza p log4j
    private static final Logger logger = Logger.getLogger(PetService.class.getName());

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
        return batisPetDao.getPetById(id);
    }

    //save add mais pets a usuario ja cadastrado!
    public void saveMaisPet(Pet pet) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManagerPet);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                batisPetDao.savePet(pet);
                // Auditoria
                String cpfUsuario = ""; // fazer como obter o CPF do usuário logado
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Cadastrou um novo pet");
                auditoriaService.saveAuditoria(auditoria);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Erro ao salvar pet: " + e.getMessage());
            }
            return null;
        });
    }


    public void updatePet(Pet pet) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                Integer userId = (Integer) session.getAttribute("user_id");

                if (userId != null) {
                    User user = userService.getUserById(userId);
                    if (user != null) {
                        System.out.println("Atualizando pet: " + pet);
                        batisPetDao.updatePet(pet);
                        // Auditoria
                        String cpfUsuario = user.getCpfUsuario();
                        Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Atualizou um pet");
                        auditoriaService.saveAuditoria(auditoria);
                    } else {
                        throw new RuntimeException("Erro: Usuário não encontrado.");
                    }
                } else {
                    throw new RuntimeException("Erro: Usuário não está logado.");
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Erro ao atualizar pet: " + e.getMessage());
            }
            return null;
        });
    }



    public void deletePet(int id) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            try {
                Pet pet = batisPetDao.getPetById(id);
                if (pet != null) {
                    batisPetDao.deletePet(id);
                    // Auditoria
                    String cpfUsuario = ""; // Defina como obter o CPF do usuário logado
                    Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Deletou um pet");
                    auditoriaService.saveAuditoria(auditoria);
                } else {
                    throw new RuntimeException("Pet não encontrado.");
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Erro ao excluir pet: " + e.getMessage());
            }
            return null;
        });
    }

    public List<Pet> listarPets(User user) {
        return batisPetDao.getListarPet();
    }
    public List<Pet> listarPetsPorUsuario(int userId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(status -> {
            try {
                List<Pet> pets = batisPetDao.listarPetsPorUsuario(userId);
                if (pets == null || pets.isEmpty()) {
                    throw new RuntimeException("Nenhum pet encontrado para o usuário.");
                }
                // Auditoria
                String cpfUsuario = ""; // fazer ainda como obter o CPF do usuário logado
                Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Visualizou pets");
                auditoriaService.saveAuditoria(auditoria);

                return pets;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Erro ao listar pets: " + e.getMessage());
            }
        });
    }


    public void setPetDao(IBatisPetDao petDao) {
        this.batisPetDao = petDao;
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}