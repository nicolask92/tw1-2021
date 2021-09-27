<jsp:include page="/partial/header.jsp"></jsp:include>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800">PAGINA DEFAULT</h1>

        <c:forEach items = "${calendario.clases}" var = "dia" begin = "0" end = "${calendario.clases.size()}">
            <c:out value="${dia.toString()}" />
        </c:forEach>
    <!--
        <div id='calendar'></div>
    -->
</div>

<!-- /.container-fluid -->

<jsp:include page="/partial/footer.jsp"></jsp:include>