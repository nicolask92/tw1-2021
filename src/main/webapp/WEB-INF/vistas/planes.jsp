<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>

<div class="container-fluid">

  <!-- Page Heading -->
  <h1 class="h3 mb-4 text-gray-800">Planes</h1>

  <c:forEach items="${planes}" var="plan" varStatus="loop">
    <div class="card text-center">
      <div class="card-header">
      </div>
      <div class="card-body">
        <h5 class="card-title">${plan.nombre}</h5>
        <p class="card-text">${plan.descripcion}</p>
        <a href="#" class="btn btn-primary">Contratar</a>
      </div>
      <div class="card-footer text-muted">
        $${plan.precio}
      </div>
    </div>
  </c:forEach>

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>