<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800 text-center">Clases Buscadas</h1>

    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Actividad</th>
            <th scope="col">Fecha</th>
            <th scope="col">Horario</th>
            <th scope="col">Reservar</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clasesBuscadas}" var="c">
        <tr>
            <th scope="row">${c.id}</th>
            <td>${c.actividadString}</td>
            <td>${c.fechaString}</td>
            <td>${c.horarioString}</td>
            <td> <a href="/proyecto_limpio_spring_war_exploded/mostrar-clase/${c.id}">
                <small>Reservar Turno</small>
            </a></td>
        </tr>
        </c:forEach>
        </tbody>
    </table>




</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>
