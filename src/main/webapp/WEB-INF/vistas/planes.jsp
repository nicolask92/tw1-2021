<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>

<div class="container-fluid">

  <!-- Page Heading -->
  <h1 class="h3 mb-4 text-gray-800 text-center">Planes</h1>

  <c:if test="${not empty param['msg']}">
    <div class="alert alert-success" role="alert">
        ${param['msg']}
    </div>
  </c:if>

  <c:if test="${not empty param['noExistePlan']}">
    <div class="alert alert-danger" role="alert">
        ${param['noExistePlan']}
    </div>
  </c:if>

  <c:if test="${not empty yaTuvoPlan}">
    <div class="alert alert-danger" role="alert">
        ${yaTuvoPlan}
    </div>
  </c:if>

  <c:set var = "tienePlanContratado" scope = "session" value = "${planes.stream().anyMatch( plan -> plan.activo )}"/>

  <c:forEach items="${planes}" var="plan" varStatus="loop">
    <div class="card text-center mb-3">
      <div class="card-header bg-primary">
        <h5 class="card-title text-white m-0">${plan.plan.nombre}</h5>
      </div>
      <div class="card-body">
        <p class="card-text">${plan.plan.descripcion}</p>
        <c:if test="${plan.puedeContratar && tienePlanContratado.get()}">
          <span style="text-decoration: line-through;"><p class="card-text">Precio $${plan.plan.precio}</p></span>
          <p>Por tener un Plan ya contratado cuesta <strong>$${plan.importeAPagar.get()}</strong></p>
        </c:if>
        <c:if test="${!plan.puedeContratar}}">
          <span style="text-decoration: line-through;"><p class="card-text">Precio $${plan.plan.precio}</p></span>
        </c:if>


        <c:choose>
          <c:when test='${plan.conDebitoActivado.present && plan.conDebitoActivado.get()}'>
            <a href="/proyecto_limpio_spring_war_exploded/cancelar-suscripcion/${plan.plan.nombre.toString()}" class="btn btn-primary">Cancelar Suscripcion</a>
          </c:when>

          <c:otherwise>
            <form:form action="contratar-plan" method="POST" modelAttribute="datosPlan">
              <c:choose>
                <c:when test='${plan.puedeContratar}'>

                  <div class="form-check">
                    <form:input path="nombre" id="nombre" type="hidden" value="${plan.plan.nombre.toString()}" />
                    <form:checkbox path="conDebito" id="conDebito" class="form-check-input"/>
                    <label class="form-check-label" for="conDebito">
                      Debito automatico
                    </label>
                  </div>

                  <button type="submit" class="btn btn-primary">Contratar</button>
                </c:when>
                <c:otherwise>
                  <c:if test="${plan.activo}">
                    <button type="submit" disabled class="btn btn-primary">Contratado</button>
                  </c:if>
                  <c:if test="${!plan.activo}">
                    <button type="submit" disabled class="btn btn-primary">Tiene un plan superior</button>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </form:form>

          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </c:forEach>

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>