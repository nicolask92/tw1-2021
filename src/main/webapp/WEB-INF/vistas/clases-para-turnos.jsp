<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800">Calendario de turnos</h1>
    <c:if test="${not empty param['msg']}">
        <div class="alert alert-danger" role="alert">
                ${param['msg']}
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
            <th scope="row">
                <h6>Dia ${dia.dia}</h6>
                <c:forEach items="${dia.clases}" varStatus="loop" var="clase">
                    <a href="mostrar-clase/${clase.id}">
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
                            <h5 class="card-title">Actividad: ${t.clase.actividadString}</h5>
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