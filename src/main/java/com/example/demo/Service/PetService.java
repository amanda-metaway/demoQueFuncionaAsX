package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PetService {


    @Autowired
    private IBatisPetDao batisPetDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private UserService userService;


    public Pet getPetById(int id) {
        return batisPetDao.getPetById(id);
    }

    //save add mais pets a usuario ja cadastrado!
    public void saveMaisPet(Pet pet) {
        batisPetDao.savePet(pet);
    }


    public void updatePet(Pet pet) {
        batisPetDao.updatePet(pet);
    }

    public void deletePet(int id) {
        batisPetDao.deletePet(id);
    }

    public List<Pet> listarPets(User user) {
        return batisPetDao.getListarPet();
    }

    public List<Pet> listarPetsPorUsuario(int userId) {
        return batisPetDao.listarPetsPorUsuario(userId);
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