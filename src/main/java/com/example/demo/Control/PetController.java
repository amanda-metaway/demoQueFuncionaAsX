package com.example.demo.Control;


import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PetController implements Serializable {




    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;


    private Pet pet = new Pet();
    private String nome;
    private String raca;
    private List<Pet> pets;

//esse new nao sei nao
    public PetController() {
        this.pet = new Pet();
        this.pets = new ArrayList<>();
    }

    public PetController(PetService petService) {
        this.petService = petService;
    }


    public void createPet() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");
        try {
            if (userId != null) {

                User user = userService.getUserById(userId);
                if (user != null) {
                    Pet novoPet = new Pet();
                    novoPet.setNome(pet.getNome()); //  do obj
                    novoPet.setRaca(pet.getRaca());
                    novoPet.setUser(user);


                    petService.savePet(novoPet);
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            } else {
                System.out.println("Usuário não está logado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void updatePet(Pet pet) {
        petService.updatePet(pet);
    }


    public void carregarPets() {
        pets = petService.listarPets();
    }

    public void carregarPetsDoUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");//do logado
        if (userId != null) {
            pets = petService.listarPetsPorUsuario(userId);
        } else {
            System.out.println("Usuário não está logado.");
        }
    }


    public void deletePet(int id) {
        pets.removeIf(pet -> pet.getId() == id);
        petService.deletePet(id);

    }




    public void resetPet() {
        this.pet = new Pet(); // inicia um novo pet
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public PetService getPetService() {
        return petService;
    }

    public void setPetService(PetService petService) {
        this.petService = petService;
    }


    public Pet getPet(int id) {
        return petService.getPetById(id);
    }

    public Pet getPetById(int id) {
        return petService.getPetById(id);
    }


    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public Pet getPet() {
        return pet;
    }

}
