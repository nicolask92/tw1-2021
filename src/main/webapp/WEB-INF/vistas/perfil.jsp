<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800 text-center">Perfil</h1>

<%--    <label for="email">Email</label>--%>
<%--    <input id="email" type="text" value="${clienteDatos.email}" class="form-control" />--%>

    <div class="form-group row">

        <div class="col-sm-4 mb-3 mb-sm-0">

            <img  class="img-fluid" src="img/perfil.png">

        </div>

        <div class="col-sm-8">

            <div class="form-group row">
                <div class="col-sm-6 mb-3 mb-sm-0">
                    <label>Nombre</label>
                    <input type="text" class="form-control" name="nombre"
                           id="nombre" readonly value=${clienteDatos.nombre}>
                </div>

                <div class="col-sm-6">
                    <label>Apellido</label>
                    <input type="text" class="form-control" id="apellido"
                           name="apellido" readonly value=${clienteDatos.apellido}>
                </div>



            </div>

            <div>
                <label>N° Legajo</label>
                <input type="text" class="form-control" id="legajo"
                       name="legajo" readonly value=${clienteDatos.id}>
            </div>

            <div>
                <label>Email</label>
                <input type="text" class="form-control" id="email"
                       name="email" readonly value=${clienteDatos.email}>
            </div>



        </div>

    </div>

    <h1 class="h3 mb-4 text-gray-800 text-center">Plan</h1>

    <div>
        <label>Plan Acutual</label>
        <input type="text" class="form-control" id="plan"
               name="email" readonly value=${ultimoPago.plan.nombre}>
    </div>

    <div>
        <label>Vencimiento</label>
        <input type="text" class="form-control" id="vencimiento"
               name="email" readonly value=${ultimoPago.fechaDeFinalizacion}>
    </div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>