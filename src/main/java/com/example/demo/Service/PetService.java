package com.example.demo.Service;


import com.example.demo.Dao.IBatisPetDao;
import com.example.demo.Model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {


    @Autowired
    private IBatisPetDao batisPetDao;

    @Autowired
    private UserService userService;


    public Pet getPetById(int id) {
        return batisPetDao.getPetById(id);
    }

    public void savePet(Pet pet) {
        batisPetDao.savePet(pet);
    }


    public void updatePet(Pet pet) {
        batisPetDao.updatePet(pet);
    }

    public void deletePet(int id) {
        batisPetDao.deletePet(id);
    }

    public List<Pet> listarPets() {
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

    public IBatisPetDao getBatisPetDao() {
        return batisPetDao;
    }

    public void setBatisPetDao(IBatisPetDao batisPetDao) {
        this.batisPetDao = batisPetDao;
    }


}