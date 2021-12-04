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

  <c:forEach items="${planes}" var="plan" varStatus="loop">
    <div class="card text-center mb-3">
      <div class="card-header bg-primary">
        <h5 class="card-title text-white m-0">${plan.key.nombre}</h5>
      </div>
      <div class="card-body">
        <p class="card-text">${plan.key.descripcion}</p>
        <p class="card-text">$${plan.key.precio}</p>

        <c:choose>
          <c:when test='${plan.value}'>
            <a href="/proyecto_limpio_spring_war_exploded/cancelar-plan/${plan.key.nombre.toString()}" class="btn btn-primary">Cancelar Suscripcions</a>
          </c:when>

          <c:otherwise>
            <form:form action="contratar-plan" method="POST" modelAttribute="datosPlan">
              <div class="form-check">
                <form:input path="nombre" id="nombre" type="hidden" value="${plan.key.nombre.toString()}" />
                <form:checkbox path="conDebito" id="conDebito" class="form-check-input"/>
                <label class="form-check-label" for="conDebito">
                  Debito automatico
                </label>
              </div>
              <button type="submit" class="btn btn-primary">Contratar</button>
            </form:form>
          </c:otherwise>
        </c:choose>



      </div>

    </div>
  </c:forEach>

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>