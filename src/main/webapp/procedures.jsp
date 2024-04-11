
<%@page import="com.development.appointmentmanagement.models.Citizen"%>
<%@page import="com.development.appointmentmanagement.models.Administrator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/navbar.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Trámite</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f3f4f6;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                width: 400px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
            }

            h2 {
                margin-top: 0;
                color: #333;
                text-align: center;
            }

            label {
                font-weight: bold;
                color: #555;
                display: block;
                margin-bottom: 5px;
            }

            input[type="text"],
            textarea {
                width: calc(100% - 20px);
                padding: 10px;
                margin: 5px 0 20px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                width: 100%;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <!-- Verificar si el administrador está autenticado -->
        <% if (request.getSession().getAttribute("admin") != null) { %>
        <form action="ProcedureSv" method="POST">
            <label>Trámite</label><br>
            <input type="text"name="name">
       <label>Descripción:</label><br>
            <textarea id="description" name="description" rows="4" cols="50"></textarea><br><br>
            <input type="submit" value="Crear Procedimiento">
        </form>
        <% } else { %>
        <p>Se requiere permiso-rol de administrador para poder acceder a esta página</p>
        <% }%>
    </form>
</body>
</html>
