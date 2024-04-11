package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.User;
import com.development.appointmentmanagement.persistences.PersistenceController;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminSv", urlPatterns = {"/AdminSv"})
public class AdminSv extends HttpServlet {

    Controller controller = new Controller();
    PersistenceController persistenceController = new PersistenceController();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        List<Appointment> appointments = controller.getListAppointments();
        User admin = (Administrator) request.getSession().getAttribute("admin");
        System.out.println(appointments);

        HttpSession mySession = request.getSession();
        mySession.setAttribute("adminAppointmentList", appointments);
        response.sendRedirect("adminAppointment.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(password);

        //Administrator isAuthenticated = persistenceController.authenticateUser(username, password);
        Administrator isAuthenticated = persistenceController.authenticateUser(username, password);

        if (isAuthenticated != null && isAuthenticated.getId() != null) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", isAuthenticated);
            System.out.println("ID del Administrador: " + admin.getId());
            System.out.println("ID del Administrador: " + isAuthenticated.getId() + isAuthenticated.getRole().getName());

            response.sendRedirect("procedures.jsp");
        } else {

            response.sendRedirect("loginAdmin.jsp?error=1");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
