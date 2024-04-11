package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProcedureSv", urlPatterns = {"/ProcedureSv"})
public class ProcedureSv extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }
//El administrador obtendrá un listado de los trámites que ha creado para poder modificarlos y eliminarlos

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        List<ProcedureTable> procedures = controller.getListProcedures();

        HttpSession mySession = request.getSession();
        mySession.setAttribute("proceduresList", procedures);
        response.sendRedirect("adminProcedures.jsp");
    }
//Metodo para poder crear el tramites por el administrador 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        List<Appointment> appointments = new ArrayList<>();

        // Obtener el usuario de la sesión
        User user = (User) request.getSession().getAttribute("admin");

        // Verificar si el usuario es un administrador
        if (user != null && user instanceof Administrator) {

            Administrator admin = (Administrator) user;
            System.out.println("ID del Administrador: " + admin.getId());

            // Llamar al método para crear un trámite
            controller.createProcedure(name, description, appointments, admin);
        } else {

            System.out.println("El usuario no es un administrador.");
        }

        response.sendRedirect("procedures.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
