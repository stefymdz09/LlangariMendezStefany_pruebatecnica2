
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="com.development.appointmentmanagement.models.Appointment"%>
<%@ include file="components/navbar.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">

    </head>

    <body>

        <div class="content">
            <% if (request.getSession().getAttribute("citizen") != null) { %>

            <div class="container-fluid">

                <%
                    List<Appointment> userAppointmentList = (List) request.getSession().getAttribute("userAppointmentList");
                %>
                <h1>Mis Turnos</h1>

                <div class="card shadow mb-4">
                    <div class="container">
                        <h2>Formulario de Filtrado</h2>
                        <form  action="filterAppointmentSv" method="GET">
                            <input type="date" class="form-control" id="inputDate" name="fecha" placeholder="Seleccione una fecha...">
                            <div class="radio-group">
                                <input type="radio" id="attended" name="estado" value=true>
                                <label for="attended">Atendido</label>

                                <input type="radio" id="waiting" name="estado" value=false>
                                <label for="waiting">En Espera</label>
                            </div>
                            <button type="submit" class="submit-btn">Aplicar Filtro</button>
                        </form>
                    </div>

                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Username</th>
                                        <th>Tramite</th>
                                        <th>Informacion</th>
                                        <th>Fecha</th>
                                        <th>Estado</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Appointment appointment : userAppointmentList) {%>
                                    <tr>

                                        <td><%= appointment.getIdAppointment()%> </td>

                                        <td><%= appointment.getCitizen().getUsername()%></td>

                                        <td><%= appointment.getProcedure().getName()%></td>

                                        <td><%= appointment.getDescription()%></td>

                                        <%
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                            String formattedDateTime = appointment.getDateTime().format(formatter);
                                        %>
                                        <td><%= formattedDateTime%></td>

                                        <td><%= appointment.esAtendido() ? "Atendido" : "En espera"%></td>

                                        <td>
                                            <form action="DeleteAppointmentSv" method="POST"> 

                                                <button class="delete-btn">&#128465; Eliminar</button>

                                                <input type="hidden" name="id" value="<%= appointment.getIdAppointment()%>">
                                            </form>
                                            <hr>
                                            <form  action="ModifyAppointmentSv" method="GET"> 
                                                <button class="edit-btn">&#9997;Modificar</button>
                                                <input type="hidden" name="id" value="<%= appointment.getIdAppointment()%>">
                                            </form>

                                        </td>
                                    </tr>

                                    <% }%>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <% } else { %>
                    <p>Debe iniciar sesión como ciudadano para acceder a esta página.</p>
                    <% }%>

                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                    </body>
                    </html>
