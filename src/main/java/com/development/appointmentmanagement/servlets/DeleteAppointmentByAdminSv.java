package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.persistences.exceptions.NoAppointmentException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteAppointmentByAdminSv", urlPatterns = {"/DeleteAppointmentByAdminSv"})
public class DeleteAppointmentByAdminSv extends HttpServlet {

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
        String dni = request.getParameter("dniCitizen");
        Integer id = Integer.valueOf(request.getParameter("idTurn"));
        try {
            controller.deleteAppointmentByAdmin((long) id, dni);
            request.setAttribute("eliminacionExitosa", true);
        } catch (NoAppointmentException e) {
            request.setAttribute("eliminacionExitosa", false);
            request.setAttribute("errorMessage", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("confirmDelete.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
