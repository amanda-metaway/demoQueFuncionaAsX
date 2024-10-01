package com.example.demo;

import com.example.demo.Dao.IBatisPetDao;
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
        IBatisPetDao petDao = (IBatisPetDao) context.getBean("petDao");

        try {
//            //cadastra um pet
//            Pet newPet = new Pet("Boio", "Persa", "Marcelo Ferreira", "9926224866");
//            sqlMapClient.insert("savePet", newPet);
//            System.out.println("Pet cadastrado com sucesso!");


            //procura um pet
//            int petIdToSelect = 21;
//            Pet existingPet = (Pet) sqlMapClient.queryForObject("getPetById", petIdToSelect);
//            if (existingPet != null) {
//                petDao.getPetById(petIdToSelect);
//                System.out.println("Pet com ID " + petIdToSelect + " encontrado com sucesso!");
//                System.out.println("Descricao :" + existingPet);
//
//            } else {
//                System.out.println("Pet com ID " + petIdToSelect + " não encontrado.");
//            }



            // Atualiza pet
//            int petIdToUpdate = 1;
//            Pet existingPet = (Pet) sqlMapClient.queryForObject("getPetById", petIdToUpdate);
//            if (existingPet != null) {
//                existingPet.setNome("Gigi atualizada2");
//                existingPet.setRaca("Siamês");
//                existingPet.setDono("Novo Dono");
//                existingPet.setDonoContato("1234567890");
//
//
//                petDao.updatePet(existingPet);
//                System.out.println("Pet com ID " + petIdToUpdate + " atualizado com sucesso!");
//                System.out.println("Atualizado para " + existingPet);
//            } else {
//                System.out.println("Pet com ID " + petIdToUpdate + " não encontrado.");
//            }


            //deleta pet
//            int petIdToDelete = 14;
//            Pet existingPet = (Pet) sqlMapClient.queryForObject("getPetById", petIdToDelete);
//            if (existingPet != null) {
//                petDao.deletePet(petIdToDelete);
//                System.out.println("Pet com ID " + petIdToDelete + " deletado com sucesso!");
//            } else {
//                System.out.println("Pet com ID " + petIdToDelete + " não encontrado.");
//            }

            //lista pets
//            List<Pet> pets = sqlMapClient.queryForList("listarPets");
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