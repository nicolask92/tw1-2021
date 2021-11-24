<%@ page import="ar.edu.unlam.tallerweb1.modelo.Plan" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/proyecto_limpio_spring_war_exploded/mostrar-clases">
           <img src="/proyecto_limpio_spring_war_exploded/img/logo-blanco-chico.png" class="img-fluid mt-2  d-md-none d-lg-none d-sm-inline">
            <div class="sidebar-brand-text mx-3"><img src="/proyecto_limpio_spring_war_exploded/img/logo-blanco.png" class="img-fluid"></div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider mt-2 mb-0">


        <!-- Divider -->
        <hr class="sidebar-divider">


        <c:if test="${(sessionScope.cliente == true) && ((empty sessionScope.plan) || sessionScope.plan.nombre != 'Ninguno' )}">
            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="/proyecto_limpio_spring_war_exploded/mostrar-clases">
                    <i class="far fa-calendar-alt"></i>
                    <span>Reservar turno</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <li class="nav-item">
                <a class="nav-link" href="/proyecto_limpio_spring_war_exploded/mostrar-turno">
                    <i class="far fa-bookmark"></i>
                    <span>Mis turno</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/proyecto_limpio_spring_war_exploded/historial-pagos">
                    <i class="far fa-credit-card"></i>
                    <span>Historial Pagos</span></a>
            </li>

            <hr class="sidebar-divider d-none d-md-block">


        </c:if>

        <li class="nav-item">
            <a class="nav-link" href="/proyecto_limpio_spring_war_exploded/planes">
                <i class="far fa-bookmark"></i>
                <span>Planes</span></a>
        </li>

        <!-- Sidebar Toggler (Sidebar)
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>
        -->
    </ul>
    <!-- End of Sidebar -->
