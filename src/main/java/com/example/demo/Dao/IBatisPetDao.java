package com.example.demo.Dao;


import com.example.demo.Model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class IBatisPetDao extends SqlMapClientDaoSupport implements PetDao {





    @Override
    public void savePet(Pet pet) {
    System.out.println(pet + toString());
        getSqlMapClientTemplate().insert("savePet", pet);
    }

    @Override
    public Pet getPetById(int id) {
        return (Pet) getSqlMapClientTemplate().queryForObject("getPetById", id);
    }

    @Override
    public List<Pet> getListarPet() {
        return  getSqlMapClientTemplate().queryForList("listarPets");
    }


    @Override
    public void updatePet(Pet pet) {
        getSqlMapClientTemplate().update("updatePet", pet);
    }

    @Override
    public void deletePet(int id) {
        getSqlMapClientTemplate().delete("deletePet", id);

    }
}