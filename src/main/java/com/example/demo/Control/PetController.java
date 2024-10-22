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
import java.util.List;



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
    private User buscaUser;
    private Pet buscaPet;
    private boolean editing;
    private Pet selectedPet;




    public PetController() {
        this.pet = new Pet();
        this.pets = new ArrayList<>();
    }


    public PetController(PetService petService) {
        this.petService = petService;
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
                    pets.add(pet);
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

    //seria para ver todos no caso para admin
    public void carregarPets(User user) {
        pets = petService.listarPets(user);

    }

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






    public void editarPet(Pet pet) {
        this.buscaPet = pet;
        this.selectedPet = pet;
    }

    public String updatePet(Pet pet) {
        petService.updatePet(pet);
        cancelarEdicao();
        return null;
    }

    public void cancelarEdicao() {
        buscaPet = new Pet();
        selectedPet = null;
    }

    public void excluirPet(Pet pet) {
        petService.deletePet(pet.getId());
        pets.remove(pet);
        cancelarEdicao();
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


    public User getBuscaUser() {
        return buscaUser;
    }

    public void setBuscaUser(User buscaUser) {
        this.buscaUser = buscaUser;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public Pet getBuscaPet() {
        return buscaPet;
    }

    public void setBuscaPet(Pet buscaPet) {
        this.buscaPet = buscaPet;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }


}
