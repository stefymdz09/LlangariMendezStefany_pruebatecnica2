package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.Appointment;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "filterAppointmentSv", urlPatterns = {"/filterAppointmentSv"})
public class filterAppointmentSv extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        List<Appointment> appointments = controller.getListAppointments();
        if (appointments != null) {

            boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
            String fechaString = request.getParameter("fecha");
            LocalDate fechaSeleccionada = LocalDate.parse(fechaString);
            if (estado) {
                appointments = controller.filterByDateStatus(fechaSeleccionada, true);
            } else {
                appointments = controller.filterByDateStatus(fechaSeleccionada, false);
            }
        }
        HttpSession mySession = request.getSession();
        mySession.setAttribute("userAppointmentList", appointments);
        request.getRequestDispatcher("viewAppointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
