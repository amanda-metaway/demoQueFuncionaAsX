package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.List;


public class PetController implements Serializable {


    private Pet pet = new Pet();

    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    public PetController() {

    }

    public PetService getPetService() {
        return petService;
    }

    public void setPetService(PetService petService) {
        this.petService = petService;
    }


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet(int id) {
        return petService.getPetById(id);
    }


    public void createPet() {
        petService.savePet(this.pet);
        // this.pet = new Pet();
    }

    public void updatePet(Pet pet) {
        petService.updatePet(pet);
    }

    public List<Pet> listarPets() {
        return petService.listarPets();
    }

    public void deletePet(int id) {
        petService.deletePet(id);
    }


    public Object getSetNome() {
        return pet.getNome();
    }
}
