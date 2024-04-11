package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteAppointmentSv", urlPatterns = {"/DeleteAppointmentSv"})
public class DeleteAppointmentSv extends HttpServlet {

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

        Integer id = Integer.valueOf(request.getParameter("id"));
        controller.deleteAppointment((long) id);
        response.sendRedirect("TurnSv");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
