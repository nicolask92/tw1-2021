<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login</title>
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
                            <p class="login-card-description">Iniciar sesi&oacute;n en su cuenta</p>
                            <form:form action="validar-login" method="POST" modelAttribute="datosLogin">

                                <%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
                                <form:input path="email" id="email" type="email" placeholder="email" class="form-control" />
                                <form:input path="password" type="password" id="password" placeholder="***********" class="form-control"/>

                                <button class="btn btn-block login-btn mb-4" Type="Submit"/>Login</button>
                            </form:form>
                            <%--Bloque que es visible si el elemento error no esta vacio	--%>
                            <c:if test="${not empty error}">
                                <h4><span>${error}</span></h4>
                                <br>
                            </c:if>
                            ${msg}
                        <a href="#!" class="forgot-password-link">¿Se te olvid&oacute; tu contrase&ntilde;a?</a>
                        <p class="login-card-footer-text">¿No tienes una cuenta? <a href="ir-a-registrarme" class="text-reset">REGISTRARSE AQU&Iacute;</a></p>
                        <a href="/registro/codigo" class="text-reset">ACTIVA TU CUENTA AQU&Iacute;</a>
                        <nav class="login-card-footer-nav">
                            <a href="#!">Condiciones de uso.</a>
                            <a href="#!">Pol&iacute;tica de privacidad</a>
                        </nav>
                    </div>
                </div>
            </div>  

        </div>
    </main>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>

</html>