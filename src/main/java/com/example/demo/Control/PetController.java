package com.example.demo.Control;

import com.example.demo.Model.Pet;
import com.example.demo.Service.PetService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PetController implements Serializable {


    private Pet pet = new Pet();

    private List<Pet> pets;
    private PetService petService;

    public PetController() {
        this.pets = new ArrayList<>();
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

    public Pet getPetById(int id) {
        return petService.getPetById(id);
    }

    public void createPet() {
        petService.savePet(this.pet);
        this.pet = new Pet();
    }

    public void updatePet(Pet pet) {
        petService.updatePet(pet);
    }


    public void carregarPets() {
        pets = petService.listarPets();
    }



    public void deletePet(int id) {
        pets.removeIf(pet -> pet.getId() == id);
        petService.deletePet(id);

    }


    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


}
