<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

  <!-- Page Heading -->
  <h1 class="h3 mb-4 text-gray-800 text-center">Informaci&oacute;n de Clase</h1>

    <div class="row justify-content-center">

        <div class="col-lg-4 mb-2">
          <div class="card">
            <div class="card-body">
                <h5 class="card-title text-center">Actividad ${clase.actividadString} </h5>
                <p class="card-text"><span>Fecha: ${clase.fechaString}</span> </p>
                <p class="card-text"><span>Hora: ${clase.horarioString}</span> </p>
                <p class="card-text"><span>Precio: ${clase.actividad.precio}$</span> </p>
                <p class="card-text"><span>Modalidad: ${clase.modalidad}</span> </p>
                <div class="text-center">
                  <a href="/proyecto_limpio_spring_war_exploded/reservar-Turno/${clase.id}" class="btn btn-primary">Reservar turno</a>
                </div>
            </div>
          </div>
        </div>


    </div>





</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>
