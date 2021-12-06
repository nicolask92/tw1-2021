<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800 text-center">Historial de Pagos</h1>

    <!-- /.container-fluid -->

    <table class="table table-striped table-bordered">
        <thead class="bg-primary text-white">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Fecha contratacion</th>
            <th scope="col">Plan</th>
            <th scope="col">Costo del Plan</th>
            <th scope="col">Importe Pagado</th>
            <th scope="col">Fecha de Vencimiento</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${pagos}" var="pago">
        <tr>
            <th scope="row">${pago.id}</th>
            <td>${pago.mes.toString()}/${pago.anio.toString()}</td>
            <td>${pago.plan.nombre.toString()}</td>
            <td>$ ${pago.plan.precio.toString()}</td>
            <td>$ ${pago.importePagado.toString()}</td>
            <td>${pago.fechaDeFinalizacion}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>


</div>
<jsp:include page="/partial/footer.jsp"></jsp:include>