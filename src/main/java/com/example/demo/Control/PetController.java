package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Service.PetService;

import java.io.Serializable;
import java.util.List;

public class PetController implements Serializable {

    private PetService petService;
    private Pet pet = new Pet();

    public PetController() {
    }

    public PetController(PetService petService) {
        this.petService = petService;
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



    public void createPet(Pet pet) {
        petService.savePet(this.pet);
        this.pet = new Pet();
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

    public void setPetService(Object petService) {
    }


}
