
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    </head>
    <style>
        body {
            background-color: #697ad1;
        }
    </style>
    <body>
        <div class="container">
            <div class="row justify-content-end">
                <div class="col-md-6">
                    <div class="card shadow">
                        <div class="card-body">
                            <h1 class="card-title text-center mb-4">Registrar Ciudadano</h1>
                            <form action="RegisterSv" method="post">
                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" name="firstName" placeholder="First Name" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" name="lastName" placeholder="Last Name" required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" name="username" placeholder="Username" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" name="document" placeholder="Document" required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <input type="password" class="form-control" name="password" placeholder="Password" required>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block">Register</button>
                            </form>

                            <div class="mt-4">
                                <a type="button" class="btn btn-primary btn-block" href="loginCitizen.jsp">
                                    Login Ciudadano
                                </a>

                                <a type="button" class="btn btn-primary btn-block"  href="loginAdmin.jsp">
                                    Login Administrador
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>  
    </body>
</html>
