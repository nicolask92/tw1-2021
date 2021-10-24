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

    <c:if test="${not empty turnos}">

        <div class="row">
            <c:forEach items="${turnos}" var="t">
                <div class="col-lg-4 mb-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Actividad ${t.clase.actividadString}</h5>
                            <p class="card-text"><span>Fecha ${t.clase.diaClase}</span> </p>
                            <p class="card-text"><span>Hora</span> </p>
                            <a href="borrar-turno/${t.id}" class="btn btn-primary">Cancelar turno</a>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </c:if>




</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>
