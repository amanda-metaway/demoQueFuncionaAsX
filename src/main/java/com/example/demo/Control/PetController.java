package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Service.PetService;

import java.util.List;


public class PetController {

    private PetService petService;

    private Pet pet= new Pet();



    public Pet getPet(int id) {
        return petService.getPetById(id);
    }


    public void createPet(Pet pet) {
        petService.savePet(pet);
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





    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setPetService(PetService petService) {
        this.petService = petService;
    }
}
