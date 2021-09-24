<jsp:include page="/partial/header.jsp"></jsp:include>
<link href='${pageContext.request.contextPath}/fullcalendar/main.css' rel='stylesheet' />
<script src='${pageContext.request.contextPath}/fullcalendar/main.js'></script>
<script>

    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth'
        });
        calendar.render();
    });

</script>
<jsp:include page="/partial/barraLateral.jsp"></jsp:include>
<jsp:include page="/partial/barraTop.jsp"></jsp:include>
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-4 text-gray-800">LA No se actualiza si no borra la carpeta out</h1>
                    <div id='calendar'></div>
                </div>
                <!-- /.container-fluid -->

<jsp:include page="partial/footer.jsp"></jsp:include>