package com.example.demo.Dao;


import com.example.demo.Model.Pet;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class IBatisPetDao extends SqlMapClientDaoSupport implements PetDao {


    @Override
    public void savePet(Pet pet) {
        getSqlMapClientTemplate().insert("savePet", pet);

    }

    @Override
    public Pet getPetById(int id) {
        return (Pet) getSqlMapClientTemplate().queryForObject("getPetById", id);
    }


    @Override
    public void updatePet(Pet pet) {
        getSqlMapClientTemplate().update("updatePet", pet);
    }

    @Override
    public void deletePet(int id) {
        System.out.println("delete Pet ...");
        getSqlMapClientTemplate().delete("deletePet", id);
    }

    @Override
    public List<Pet> getListarPet() {
        return getSqlMapClientTemplate().queryForList("listarPets");
    }


    public List<Pet> listarPetsPorUsuario(int userId) {
        return getSqlMapClientTemplate().queryForList("listarPetsPorUsuario", userId);
    }

}