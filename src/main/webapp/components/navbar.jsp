<%-- 
    Document   : sidebar
    Created on : 25 mar 2024, 21:44:32
    Author     : Stefy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
<style>
    .navbar {
        background-color: #007bff;
        overflow: hidden;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .navbar a {
        float: left;
        display: block;
        color: #fff;
        text-align: center;
        padding: 12px 16px;
        text-decoration: none;
        font-size: 16px;
        transition: background-color 0.3s;
    }

    .navbar a:hover {
        background-color: #0056b3;
    }

    .navbar a.active {
        background-color: #0056b3;
    }

    .navbar .right {
        float: right;
    }

    .content {
        margin-top: 56px; /* Adjust based on navbar height */
        padding: 20px;
    }
</style>

<body>
    <div class="navbar">
        <% if (request.getSession().getAttribute("citizen") != null) { %>
            <a href="AppointmentTurnSv" class="active">Solicitar Turno</a>
            <a href="TurnSv">Mis Turnos</a>
            <a href="LogOutSv" class="right">Cerrar Sesión</a>
        <% } else if (request.getSession().getAttribute("admin") != null) { %>
            <a href="ProcedureSv">Ver Trámites</a>
            <a href="AdminSv">Ver Ciudadanos y tramites</a>
            <a href="procedures.jsp">Crear Trámites</a>
            <a href="LogOutSv" class="right">Cerrar Sesión</a>
        <% } %>
    </div>

   
</body>
