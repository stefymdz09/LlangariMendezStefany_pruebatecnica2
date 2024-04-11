
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.development.appointmentmanagement.models.Appointment"%>
<%@page import="java.util.List"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    </head>
    <body>

        <% if (request.getSession().getAttribute("admin") != null) { %>
        <div class="content">


            <%
                List<Appointment> appointments = (List) request.getSession().getAttribute("adminAppointmentList");
            %>

            <h3 >Ver los trámites de los ciudadnos</3>
                <h4>Actualizar Estado de Citas</h4>

                <form action="ModifyStatusServlet" method="post">
                  
                <button type="button" class="btn btn-warning">Actualizar estado de  Turnos </button>
                </form>


                <div class="container">
                    <h2>Formulario de Filtrado</h2>
                    <form id="formFilter" action="filterAdminSv"  method="GET">
                        <input type="date" class="form-control" id="inputDate" name="fecha" placeholder="Seleccione una fecha...">
                        <div class="radio-group">
                            <input type="radio" id="attended" name="estado" value="true">
                            <label for="attended">Atendido</label>
                            <input type="radio" id="waiting" name="estado" value="false">
                            <label for="waiting">En Espera</label>
                        </div>
                        <button type="submit" class="submit-btn">Aplicar Filtro</button>
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-info">
                            <tr>
                                <th>Id</th>
                                <th>Ciudadano</th>
                                <th>Trámite</th>
                                <th>Informacion Adicional</th>
                                <th>Fecha turno</th>
                                <th>Estado</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
                            <%for (Appointment appointment : appointments) {%>
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
                                    <form action="DeleteProcedureSv" method="POST">

                                        <% String idTurn = String.valueOf(appointment.getIdAppointment()); %>
                                        <% String dniCitizen = appointment.getCitizen().getDocument(); %>
                                        <% String url = "confirmDelete.jsp?idTurn=" + idTurn + "&dniCitizen=" + dniCitizen;%>
                                        <a href="<%= url%>" class="delete-btn">&#128465; Eliminar</a>

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
        <p>Debe tener permiso de administrador para acceder en esta página.</p>
        <% }%>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>
