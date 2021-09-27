<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800">Calendario de turnos</h1>


    <table class="table">
        <thead>
            <tr>
                <th scope="col">Lunes</th>
                <th scope="col">Mares</th>
                <th scope="col">Miercoles</th>
                <th scope="col">Jueves</th>
                <th scope="col">Viernes</th>
                <th scope="col">Sabado</th>
                <th scope="col">Domingo</th>
            </tr>
        </thead>
        <tbody>

        <c:forEach items="${calendario.clases}" varStatus="loop" var="dia" begin="0" end="${calendario.clases.size()}">
            <c:if test="${(loop.index % 7) == 0}">
                <tr>
            </c:if>
                    <th scope="row"><c:out value="${dia.toString()}"/></th>
            <c:if test="${(loop.index) == 6 || loop.index == 13 || loop.index == 20 || loop.index == 27}">
                </tr>
            </c:if>
        </c:forEach>

        </tbody>
    </table>

    <!--
        <div id='calendar'></div>
    -->

</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>