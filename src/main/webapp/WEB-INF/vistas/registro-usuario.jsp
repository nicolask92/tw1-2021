<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Registro</title>
        <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/login.css">
    </head>
<body>
    <main class="d-flex align-items-center min-vh-100 py-3 py-md-0">
        <div class="container">
            <div class="card login-card">
                <div class="row no-gutters">
                    <div class="col-md-5">
                        <img src="img/login.jpg" alt="login" class="login-card-img">
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <div class="brand-wrapper center">
                                <img src="img/logo-negro.png" alt="logo" class="">
                            </div>
                            <p class="login-card-description">Registro</p>

                            <form:form action="registrarme" method="POST" modelAttribute="datos">
                                <form:input path="nombre" id="email" placeholder="Nombre" class="form-control" />
                                <form:input path="apellido" type="password" id="clave"  placeholder="Apellido" class="form-control"/>
                                <form:input path="email" id="email"  placeholder="Email" class="form-control" />
                                <form:input path="clave" type="password" id="clave" placeholder="***********" class="form-control"/>
                                <form:input path="repiteClave" type="password" id="clave" placeholder="***********" class="form-control"/>
                                <button id="btn-registrarme" class="btn btn-block login-btn mb-4" Type="Submit"/>Registrarme</button>
                            </form:form>

                            <c:if test="${not empty msg}">
                                <h4><span>${msg}</span></h4>
                                <br>
                            </c:if>

                            <p class="login-card-footer-text">¿Tienes una cuenta? <a href="login" class="text-reset">INICIAR SESI&Oacute;N</a></p>

                            <nav class="login-card-footer-nav">
                                <a href="#!">Condiciones de uso.</a>
                                <a href="#!">Pol&iacute;tica de privacidad</a>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </main>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <!-- Bootstrap Date-Picker Plugin -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

    </body>
</html>