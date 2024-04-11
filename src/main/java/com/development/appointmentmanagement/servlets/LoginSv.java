
package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.User;
import com.development.appointmentmanagement.persistences.PersistenceController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "LoginSv", urlPatterns = {"/LoginSv"})
public class LoginSv extends HttpServlet {

    PersistenceController persistenceController = new PersistenceController();

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
           String username = request.getParameter("username");
        String password = request.getParameter("password");
        User isAuthenticated = persistenceController.authenticateCitizen(username, password);

        if (isAuthenticated != null && isAuthenticated.getId() != null) {
            HttpSession session = request.getSession();
            session.setAttribute("citizen", isAuthenticated);
            System.out.println("ID del Citizen: " + isAuthenticated.getId());
            System.out.println("Nombre del Citizen: " + isAuthenticated.getUsername());
            response.sendRedirect("viewCitizen.jsp");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
