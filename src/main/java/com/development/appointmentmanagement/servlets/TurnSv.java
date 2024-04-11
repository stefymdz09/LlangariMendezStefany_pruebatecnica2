
package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.User;
import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "TurnSv", urlPatterns = {"/TurnSv"})
public class TurnSv extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        List<Appointment> appointment = controller.getListAppointments();
        User citizen = (Citizen) request.getSession().getAttribute("citizen");
        System.out.println(appointment);
        List<Appointment> filteredAppointment = controller.filterAppointmentsByCitizen(appointment, citizen);

        // Establecer la lista filtrada en la sesión
        HttpSession mySession = request.getSession();
        mySession.setAttribute("userAppointmentList", filteredAppointment);

        System.out.println(appointment);  
        response.sendRedirect("viewAppointment.jsp");
    }

//-------------SOLICITAR EL TURNO  --------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Integer procedureId = Integer.valueOf(request.getParameter("procedures"));
        String description = request.getParameter("comentary");
        Citizen citizen = (Citizen) request.getSession().getAttribute("citizen");
        ProcedureTable procedure = controller.getProcedureById((long) procedureId);
        // Obtenemos la cadena de fecha y hora y creamos un objetos LocalDateTime
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("time");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(dateString), LocalTime.parse(timeString));

        controller.createAppointment(dateTime, description, procedure, citizen);
        response.sendRedirect("requestAppointment.jsp");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
