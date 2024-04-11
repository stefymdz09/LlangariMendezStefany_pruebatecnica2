
<%@page import="com.development.appointmentmanagement.models.ProcedureTable"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrar Tramites</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <style>


        .edit-btn, .delete-btn {
            padding: 5px 10px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 16px;
        }

        .edit-btn:hover, .delete-btn:hover {
            background-color: #4CAF50;
            color: white;
        }


    </style>
    <body>
        
        <% if (request.getSession().getAttribute("admin") != null) { %>
       <div class="content">
            <div class="container-fluid">

                <h2>Trámites</h2>

                <div class="card shadow">

                    <div class="card-body">


                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Tratamiento</th>
                                        <th>Descripción</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>


                                <%
                                    List<ProcedureTable> procedures = (List) request.getSession().getAttribute("proceduresList");
                                %>

                                <tbody>
                                    <% for (ProcedureTable procedure : procedures) {%>
                                    <tr>
                                        <td><%= procedure.getIdProcedure()%> </td>
                                        <td><%= procedure.getName()%></td>
                                        <td><%= procedure.getDescription()%></td>
                                        <td>
                                            <form action="DeleteProcedureSv" method="POST"> 

                                                <button class="delete-btn">&#128465; Eliminar</button>

                                                <input type="hidden" name="id" value="<%= procedure.getIdProcedure()%>">
                                            </form>
                                            <hr>
                                            <form  action="ModifyProcedureSv" method="GET"> 

                                                <button class="edit-btn">&#9997;Modificar</button>

                                                <input type="hidden" name="id" value="<%= procedure.getIdProcedure()%>">
                                            </form>
                                        </td>
                                    </tr>

                                    <% }%>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>

            </div>
        <% } else { %>
        <p>Debe iniciar sesión como administrador para acceder a esta página.</p>
        <% }%>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>
