<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800 text-center">Mis Turnos</h1>

    <c:if test="${not empty param['msgBorrado']}">
        <div class="alert alert-success" role="alert">
           ${param['msgBorrado']}
        </div>
       <br>

    </c:if>
    <c:if test="${not empty param['msgGuardado']}">
        <div class="alert alert-success" role="alert">
            ${param['msgGuardado']}
        </div>
        <br>
    </c:if>

    <c:if test="${not empty param['msgTurnoExpiro']}">
        <div class="alert alert-danger" role="alert">
                ${param['msgTurnoExpiro']}
        </div>
        <br>
    </c:if>

    <c:if test="${not empty turnos}">

        <div class="row">
            <c:forEach items="${turnos}" var="t">
                <div class="col-lg-4 mb-2">
                    <div class="card">
                        <div class="card-header bg-gradient-primary text-white text-center h-50">
                            <h5>Actividad: ${t.clase.actividadString}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text"><span>Fecha: ${t.clase.fechaString}</span> </p>
                            <p class="card-text"><span>Hora: ${t.clase.horarioString}</span> </p>

                        <div class="text-center">
                            <a href="borrar-turno/${t.id}" class="btn btn-primary">Cancelar turno</a>
                        </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </c:if>




</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>
