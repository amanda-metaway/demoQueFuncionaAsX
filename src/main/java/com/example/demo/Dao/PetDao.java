package com.example.demo.Dao;

import com.example.demo.Model.Pet;

import java.util.List;

public interface PetDao {
    void savePet(Pet pet);

    Pet getPetById(int id);

    List<Pet> getListarPet();

    void updatePet(Pet pet);

    void deletePet(int id);

    public List<Pet> listarPetsPorUsuario(int userId);

}
