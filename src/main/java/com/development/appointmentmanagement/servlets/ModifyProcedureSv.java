package com.development.appointmentmanagement.servlets;

import com.development.appointmentmanagement.logics.Controller;
import com.development.appointmentmanagement.models.ProcedureTable;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ModifyProcedureSv", urlPatterns = {"/ModifyProcedureSv"})
public class ModifyProcedureSv extends HttpServlet {

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
        ProcedureTable procedureId = controller.getProcedureById((long) id);
        HttpSession mySession = request.getSession();
        mySession.setAttribute("idProcedure", procedureId);
        response.sendRedirect("editProcedure.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Integer id = Integer.valueOf(request.getParameter("id"));
        ProcedureTable procedureId = controller.getProcedureById((long) id);
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        controller.modifyProcedure(name, description, procedureId.getAdmin(), procedureId.getAppointments(), procedureId);
        response.sendRedirect("ProcedureSv");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
