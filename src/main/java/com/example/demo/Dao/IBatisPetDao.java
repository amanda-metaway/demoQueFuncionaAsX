package com.example.demo.Dao;


import com.example.demo.Model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class IBatisPetDao extends SqlMapClientDaoSupport implements PetDao {



    private SqlMapClientTemplate sqlMapClientTemplate;



    @Override
    public void savePet(Pet pet) {

    }

    @Override
    public Pet getPetById(int id) {
        return (Pet) sqlMapClientTemplate.queryForObject("Pet.getPetById", id);
    }

    @Override
    public List<Pet> getListarPet() {
        return new ArrayList<>();
    }


    @Override
    public void updatePet(Pet pet) {
        sqlMapClientTemplate.update("Pet.updatePet", pet);
    }

    @Override
    public void deletePet(int id) {
        sqlMapClientTemplate.delete("Pet.deletePet", id);
    }
}