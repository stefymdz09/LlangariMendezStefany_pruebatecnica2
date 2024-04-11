

<%@page import="com.development.appointmentmanagement.models.ProcedureTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Trámite</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <% if (request.getSession().getAttribute("admin") != null) { %>

        <%
            ProcedureTable procedure = (ProcedureTable) request.getSession().getAttribute("idProcedure");
        %>
        <div class="content">

            <div class="form-container">
                <form id="myForm" class="user" action="ModifyProcedureSv" method="POST">
                    <h2 class="center">Editar Trámite</h2>

                    <label>Nombre Trámite</label>
                    <div class="form-group">
                        <input type="text" class="form-control" name="name" value="<%= procedure.getName()%>">
                    </div>
                    <label>Descripción Trámite</label>
                    <div class="form-group">
                        <textarea class="form-control" name="description">
                            <%= procedure.getDescription()%>
                        </textarea>
                    </div>
                    <input type="hidden" name="id" value="<%= procedure.getIdProcedure()%>">
                    <button  type="submit" class="btn btn-primary" >
                        Modificar
                    </button>

                </form>
            </div>
        </div>
        <% } else { %>
        <p>Debe iniciar sesión como admin para acceder a esta página.</p>
        <% }%>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>
