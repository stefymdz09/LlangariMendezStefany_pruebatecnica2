
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Administrador</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    </head>
    <style>
        body {
            background-color: #697ad1;
        }
    </style>
    <body>

        <div class="container mt-4">
            <div class="row justify-content-end">
                <div class="col-md-6">
                    <div class="card shadow">
                        <div class="card-body">
                            <h5 class="card-title">Login Administrador</h5>
                            <form action="AdminSv" method="post">
                                <div class="mb-3">
                                    <label for="exampleInputUsername" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="exampleInputUsername" name="username">
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPassword" name="password">
                                </div>
                                <button type="submit" class="btn btn-success">Login </button>
                            </form>
                            <div class="mt-4">
                                <a type="button" class="btn btn-primary btn-block" href="registerAdmin.jsp">
                                    Registrar Administrador
                                </a>

                                <a type="button" class="btn btn-primary btn-block" href="registerCitizen.jsp">
                                    Registrar Ciudadano
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
