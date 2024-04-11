package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.persistences.PersistenceController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterAdminSv", urlPatterns = {"/RegisterAdminSv"})
public class RegisterAdminSv extends HttpServlet {

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
        String password = request.getParameter("password");
        System.out.println(username + " " + password);
        List<ProcedureTable> procedures = new ArrayList<>();

        controller.registerAdmin(firstName, lastName, procedures, username, password);

        response.sendRedirect("loginAdmin.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
