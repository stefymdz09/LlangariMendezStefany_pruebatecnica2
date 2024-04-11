
<%@page import="java.util.List"%>
<%@page import="com.development.appointmentmanagement.models.ProcedureTable"%>
<%@page import="com.development.appointmentmanagement.models.Citizen"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitar Turno</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <style>

        .formulario-container {
            max-width:480px; 
            margin: 0 auto; 
        }

    </style>
    <body>
        <div class="content">
            <% if (request.getSession().getAttribute("citizen") != null) { %>

            <div class="formulario-container">
                <form id="myForm" class="user" action="TurnSv" method="POST">
                    <h2>Solicitar Turno</h2>

                    <%
                        List<ProcedureTable> procedures = (List) request.getSession().getAttribute("proceduresList");
                    %>
              <div class="form-group">
                        <label for="date">Seleccionar Fecha:</label>
                        <input type="date" id="date" name="date" class="form-control" required min="<%= java.time.LocalDate.now()%>" >
                    </div>


                    <div class="form-group">
                        <label for="time">Seleccionar Hora:</label>
                        <select id="time" name="time" class="form-control" required>
                            <option value="08:00">09:00 AM</option>
                            <option value="09:30">09:30 AM</option>
                            <option value="10:30">10:30 AM</option>
                            <option value="11:30">11:30 AM</option>
                            <option value="12:30">12:30 AM</option>
                            <option value="13:30">13:30 AM</option>
                            <option value="14:30">14:00 AM</option>
                            <option value="23:00">23:00 AM</option>
                            <option value="23:30">23:30 AM</option>

                        </select>
                    </div>
                     <label for="procedures">Descripcion-Comentario</label>
                    <div class="form-group">
                        <input type="text" class="form-control"  name="comentary">
                    </div>
                  
                    <div class="form-group">
                        <label for="procedures">Tramite a realizar: </label>
                        <select id="procedures" name="procedures" class="form-control" required>
                            <% for (ProcedureTable procedure : procedures) {%>
                            <option value="<%= procedure.getIdProcedure()%>" ><%= procedure.getName().toUpperCase()%></option>
                            <% }%>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        Solicitar Turno
                    </button>              
                </form>
            </div>
        </div>
        <% } else { %>
        <p>Debe iniciar sesión como ciudadano para acceder a esta página.</p>
        <% }%>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>