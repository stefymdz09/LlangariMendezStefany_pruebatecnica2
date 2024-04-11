
package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ModifyAppointmentSv", urlPatterns = {"/ModifyAppointmentSv"})
public class ModifyAppointmentSv extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Integer id = Integer.valueOf(request.getParameter("id"));
        Appointment idAppointment = controller.getAppointmentById((long) id);
        HttpSession mySession = request.getSession();
        mySession.setAttribute("idAppointment", idAppointment);
        response.sendRedirect("editAppointment.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Integer id = Integer.valueOf(request.getParameter("id"));

        String description = request.getParameter("comentary");
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("time");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(dateString), LocalTime.parse(timeString));

        Citizen citizen = (Citizen) request.getSession().getAttribute("citizen");

        Appointment idAppointment = controller.getAppointmentById((long) id);

        Long procedureId = Long.valueOf(request.getParameter("procedures"));
        ProcedureTable procedure = controller.getProcedureById(procedureId);
        
        controller.modifyAppointment(dateTime, false,description, procedure, citizen, idAppointment);

        response.sendRedirect("TurnSv");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
