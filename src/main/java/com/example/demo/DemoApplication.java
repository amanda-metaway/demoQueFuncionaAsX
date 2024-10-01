package com.example.demo;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        SqlMapClient sqlMapClient = (SqlMapClient) context.getBean("sqlMapClient");


        try {
//            //cria um pet
            Pet newPet = new Pet("Boio", "Persa", "Marcelo Ferreira", "9926224866");
            sqlMapClient.insert("savePet", newPet);
            System.out.println("Pet cadastrado com sucesso!");

            //atualiza pet
//            Pet existingPet = (Pet) sqlMapClient.queryForObject("Pet.getPetById", newPet.getId());
//            if (existingPet != null) {
//                existingPet.setNome("Luna atualizada");
//                sqlMapClient.update("Pet.updatePet", existingPet);
//                System.out.println("Pet atualizado com sucesso!");
//            }

            //deleta pet
//            sqlMapClient.delete("Pet.deletePet");
//            System.out.println("Pet deletado com sucesso!");

            //lista pets
//            List<Pet> pets = sqlMapClient.queryForList("Pet.listarPets");
//            System.out.println("Lista de Pets Cadastrados:");
//            for (Pet pet : pets) {
//                System.out.println("Nome: " + pet.getNome() + ", Raça: " + pet.getRaca() + ", Dono: " + pet.getDono() + ", Contato: " + pet.getDonoContato());
//            }

            //cadastra user com perfil
//            User newUser = new User();
//            newUser.setName("Maria Silva");
//            newUser.setProfile(UserProfile.ADMIN);
//            sqlMapClient.insert("User.saveUser", newUser);
//            System.out.println("Usuário cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}