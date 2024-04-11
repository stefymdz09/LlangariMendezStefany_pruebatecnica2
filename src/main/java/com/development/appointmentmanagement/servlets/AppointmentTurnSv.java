package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.ProcedureTable;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AppointmentTurnSv", urlPatterns = {"/AppointmentTurnSv"})
public class AppointmentTurnSv extends HttpServlet {

    Controller controller = new Controller();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    //---Obtener el listado de trámites para que el usuario los pueda solicitar
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        List<ProcedureTable> procedures = controller.getListProcedures();

        HttpSession mySession = request.getSession();
        mySession.setAttribute("proceduresList", procedures);
        response.sendRedirect("requestAppointment.jsp");
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
