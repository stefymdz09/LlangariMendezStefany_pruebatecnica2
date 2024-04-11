<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eliminar Turno By Admin</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <div class="card card-container shadow">
                <div class="card-header">
                    <h2 class="text-center">Confirmación de Eliminación</h2>
                </div>
                <div class="card-body">
                    <% if (request.getAttribute("eliminacionExitosa") != null) { %>
                    <% if ((boolean) request.getAttribute("eliminacionExitosa")) { %>
                    <div class="alert alert-success" role="alert">
                        ¡El turno ha sido eliminado correctamente!
                    </div>
                    <% } else { %>
                    <div class="alert alert-danger" role="alert">
                        <% String errorMessage = (String) request.getAttribute("errorMessage");%>
                        <%= errorMessage != null ? errorMessage : "Hubo un error al intentar eliminar el turno. Por favor, intentelo de nuevo."%>
                    </div>
                    <% } %>
                    <% }%>

                    <p>A continuación, se muestra el turno que se eliminará. Por favor, ingrese el DNI del ciudadano para confirmar la eliminación:</p>

                    <p>ID del Turno: <%= request.getParameter("idTurn")%></p>


                    <form action="DeleteAppointmentByAdminSv" method="post">
                        <input type="hidden" name="idTurn" value="<%= request.getParameter("idTurn")%>">
                        <div class="form-group">
                            <label for="dniCiudadano">DNI del Ciudadano:</label>
                            <input type="text" class="form-control" name="dniCitizen">
                        </div>
                        <button type="submit" class="btn btn-danger">Eliminar Turno</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>