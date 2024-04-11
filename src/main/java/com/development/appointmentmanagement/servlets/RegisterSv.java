package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.persistences.PersistenceController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefy
 */
@WebServlet(name = "RegisterSv", urlPatterns = {"/RegisterSv"})
public class RegisterSv extends HttpServlet {

    PersistenceController persistenceController = new PersistenceController();
    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String document = request.getParameter("document");
        String password = request.getParameter("password");
        List<Appointment> appointments = new ArrayList<>();

        controller.createCitizen(firstName, lastName, document, appointments, username, password);
        // Obtener la sesión del usuario (o crear una nueva si no existe)
        System.out.println(" " + firstName + " " + lastName + " " + username + " " + appointments + " " + document + " " + password);
        HttpSession session = request.getSession(true);
        // Guardar el nombre de usuario en la sesión para su uso posterior
        session.setAttribute("citizen", username);
       // Redirigir a una página de confirmación o inicio de sesión
        response.sendRedirect("loginCitizen.jsp");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
