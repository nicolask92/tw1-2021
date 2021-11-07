<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800">Calendario de turnos del mes de ${mes} - ${anio}</h1>
    <c:if test="${not empty param['msg']}">
        <div class="alert alert-danger" role="alert">
                ${param['msg']}
        </div>
    </c:if>

    <c:if test="${not empty param['msgTurnoExistente']}">
        <div class="alert alert-danger" role="alert">
                ${param['msgTurnoExistente']}
        </div>
    </c:if>

    <table class="table">
        <thead>
            <tr>
                <c:forEach items="${calendario.conjuntoDias}" varStatus="loop" var="dia" begin="0" end="${calendario.conjuntoDias.size() - 1}">
                    <th scope="col"><c:out value="${dia.toString()}"/></th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>

        <c:forEach items="${calendario.fechasYSusClases}" varStatus="loop" var="dia" begin="0" end="${calendario.fechasYSusClases.size()}">
            <c:if test="${(loop.index % 7) == 0}">
                <tr>
            </c:if>
            <c:if test="${dia.esDomingo}">
                <th scope="row" class="p-3 mb-2 bg-danger text-white">
            </c:if>
            <c:if test="${dia.esDomingo == false}">
                <th scope="row">
            </c:if>
                <h6>Dia ${dia.dia}</h6>
                <c:forEach items="${dia.clases}" varStatus="loop" var="clase">
                    <a href="/proyecto_limpio_spring_war_exploded/mostrar-clase/${clase.id}">
                        <small>${clase.getActividadString()} - ${clase.horarioString}</small>
                    </a><br>
                </c:forEach>
            </th>

            <c:if test="${(loop.index) == 6 || loop.index == 13 || loop.index == 20 || loop.index == 27}">
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <!--
        <div id='calendar'></div>
    -->
    <br>
    <hr class="sidebar-divider">
    <br>

    <c:if test="${not empty turnosDelDia}">

        <h2>Turnos para hoy</h2>

        <div class="row">
            <c:forEach items="${turnosDelDia}" var="t">
                <div class="col-lg-4 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <div class="card-header bg-gradient-primary text-white text-center h-50">
                                <h5>Actividad: ${t.clase.actividadString}</h5>
                            </div>
                            <p class="card-text"><span>Fecha: ${t.clase.fechaString}</span> </p>
                            <p class="card-text"><span>Hora: ${t.clase.horarioString}</span> </p>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </c:if>

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>