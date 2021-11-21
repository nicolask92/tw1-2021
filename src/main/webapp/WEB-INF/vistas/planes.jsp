<%@ page import="ar.edu.unlam.tallerweb1.modelo.Plan" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>

<div class="container-fluid">

  <!-- Page Heading -->
  <h1 class="h3 mb-4 text-gray-800 text-center">Planes</h1>

  <c:forEach items="${planes}" var="plan" varStatus="loop">
    <div class="card text-center mb-3">
      <div class="card-header bg-primary">
        <h5 class="card-title text-white m-0">${plan.nombre}</h5>
      </div>
      <div class="card-body">
        <p class="card-text">${plan.descripcion}</p>
        <p class="card-text">$${plan.precio}</p>

        <c:if test='${sessionScope.plan != "NINGUNO" || sessionScope.plan != ""}'>
        <c:if test='${sessionScope.plan == "BASICO"}'>
          <a href="/cancelar-plan/${plan.nombre.toString()}" class="btn btn-primary">Cancelar Suscripcions</a>
        </c:if>

        <c:if test='${sessionScope.plan == "ESTANDAR"}'>
          <a href="/cancelar-plan/${plan.nombre.toString()}" class="btn btn-primary">Cancelar Suscripcions</a>
        </c:if>

        <c:if test='${sessionScope.plan == "PREMIUM"}'>
          <a href="/cancelar-plan/${plan.nombre.toString()}" class="btn btn-primary">Cancelar Suscripcions</a>
        </c:if>

            <a href="/proyecto_limpio_spring_war_exploded/contratar-plan/${plan.nombre.toString()}" class="btn btn-primary">Contratar</a>
        </c:if>
      </div>

    </div>
  </c:forEach>

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>