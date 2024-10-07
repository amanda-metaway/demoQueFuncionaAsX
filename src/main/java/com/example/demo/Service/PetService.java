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


    public Pet getPetById(int id) {
        return batisPetDao.getPetById(id);
    }

    public void savePet(Pet pet) {
        batisPetDao.savePet(pet);
    }

    public List<Pet> listarPets() {
        return batisPetDao.getListarPet();
    }

    public void updatePet(Pet pet) {
        batisPetDao.updatePet(pet);
    }

    public void deletePet(int id) {
        batisPetDao.deletePet(id);
    }


    public void setPetDao(IBatisPetDao petDao) {
        this.batisPetDao = petDao;
    }


}