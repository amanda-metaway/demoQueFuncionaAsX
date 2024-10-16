package com.example.demo.Control;


import com.example.demo.Model.Auditoria;
import com.example.demo.Model.Pet;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.PetService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PetController implements Serializable {

    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditoriaService auditoriaService;


    private Pet pet = new Pet();
    private String nome;
    private String raca;
    private List<Pet> pets;

    private User selectedUser;



    public PetController() {
        this.pet = new Pet();
        this.pets = new ArrayList<>();
    }


    public PetController(PetService petService) {
        this.petService = petService;
    }


    //nao func erro de jvSct no modal de tela
    public void editar(Integer id) {
        try {
            pet = petService.getPetById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //nao func erro de jvSct no modal de tela
    //seleciona e traz os pets vinculados
    public void selecionar(Integer id) {
        try {

            User user = userService.getUserById(id);
            List<Pet> pets = petService.listarPetsPorUsuario(id);

            user.setPets(pets);

            this.selectedUser = user;
            this.pets = pets;
            for (Pet pet : pets) {
                System.out.println("Pet : " + pet.getNome());
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //criando dentro do usuario ja existe!
    //dois pq nao tem modal para o cadastrinho
    public String createPet() {
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
                    String cpfUsuario = user.getCpfUsuario();

                    petService.saveMaisPet(novoPet);
                    //audita
                    Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Cadastrou NOVO Pet");
                    if (auditoria != null && auditoria.getUserId() != null) {
                        auditoriaService.saveAuditoria(auditoria);
                    } else {
                        System.out.println("Erro: auditoria não foi criada ou usuário não encontrado.");
                    }

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
        return "index.xhtml";
    }


    public void updatePet(Pet pet) {
        petService.updatePet(pet);
    }

    //seria para ver todos no caso para admin
    public void carregarPets(User user) {
        pets = petService.listarPets(user);

    }

//    public void prepararPets(User user) {
//        this.selectedUser = user;
//        carregarPets(user);
//    }



    //apenas o do user pessoal
    public void carregarPetsDoUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");//do logado
        if (userId != null) {
            pets = petService.listarPetsPorUsuario(userId);
            Integer idUser = userId;
            String cpfUsuario = userService.getUserById(idUser).getCpfUsuario();
            //audita
            Auditoria auditoria = auditoriaService.createAuditoria(cpfUsuario, "Carregou sua LISTA de pets");
            if (auditoria != null && auditoria.getUserId() != null) {
                auditoriaService.saveAuditoria(auditoria);
            } else {
                System.out.println("Erro: auditoria não foi criada ou usuário não encontrado.");
            }
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


    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public List<Pet> getPets() {
       return pets;
    }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


    public void setUserService(Object userService) {
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
