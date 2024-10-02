package com.example.demo.Servlet;

import com.example.demo.Control.PetController;
import com.example.demo.Service.PetService;
import com.example.demo.Model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PetServlet extends HttpServlet {

    private PetService petService;

    private PetController petController;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        petController = new PetController(petService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("listar".equals(action)) {
            request.setAttribute("pets", petController.listarPets());
            request.getRequestDispatcher("/index.xhtml").forward(request, response);
        } else if ("buscar".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Pet pet = petController.getPet(id);
            request.setAttribute("pet", pet);
            request.getRequestDispatcher("/editarPet.xhtml").forward(request, response);
        } else {
            request.getRequestDispatcher("/index.xhtml").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            Pet pet = new Pet();
            pet.setNome(request.getParameter("nome"));
            pet.setRaca(request.getParameter("raca"));
            pet.setDono(request.getParameter("dono"));
            pet.setDonoContato(request.getParameter("donoContato"));

            petController.createPet(pet);
            response.sendRedirect(request.getContextPath() + "/index.xhtml?action=listar");
        } else if ("atualizar".equals(action)) {
            Pet pet = new Pet();
            pet.setId(Integer.parseInt(request.getParameter("id")));
            pet.setNome(request.getParameter("nome"));
            pet.setRaca(request.getParameter("raca"));
            pet.setDono(request.getParameter("dono"));
            pet.setDonoContato(request.getParameter("donoContato"));

            petController.updatePet(pet);
            response.sendRedirect(request.getContextPath() + "/index.xhtml?action=listar");
        } else if ("deletar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            petController.deletePet(id);
            response.sendRedirect(request.getContextPath() + "/index.xhtml?action=listar");
        }
    }
}
