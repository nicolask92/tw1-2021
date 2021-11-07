package ar.edu.unlam.tallerweb1.intercepters;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServiceInterceptorTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    ModelAndView modelAndView = mock(ModelAndView.class);
    Object handler = new Object();

    LoginServiceInterceptor loginServiceInterceptor = new LoginServiceInterceptor();

    @Test
    public void siNoHayUsuarioLogueadoRedirigeAlLogin() throws Exception {
        boolean pasoElPreHandler = whenUnUsuarioIntentaEntrarAUnaUrlQueNoSeaLoginYNoEstaLogueadoElPreHandlerDevuelveFalseParaCortarEjecucionYRedirige();
        thenFueRedirigedoPorElPrehandler(pasoElPreHandler);
    }

    @Test
    public void siHayUsuarioLogueadoDejaContinuar() throws Exception {
        givenUnUsuarioLogueado();
        boolean pasoElPreHandler = whenUsuarioLogueadoEntraAUrlPreHandlerLeDejaContinuar();
        thenElPreHandlerDejaContinuarYNoRedirige(pasoElPreHandler);
    }

    @Test
    public void siHayUsuarioLogueadoYQuiereEntrarAlLoginLoMandaALosTurnos() throws Exception {
        givenUnUsuarioLogueado();
        whenUsuarioEntraAlLoginLoMandaALosTurnos();
        thenElPostHandlerRedirigeAsuarioALosTurnos();
    }

    @Test
    public void siHayUsuarioLogueadoYQuiereEntrarACualquierPaginaQueNoSeaLoginElPostHandleNoHaceNada() throws Exception {
        givenUnUsuarioLogueado();
        whenUsuarioEntraALosTurnosNoHaceNadaElPostHandle();
        thenElPostHandlerNoHaceNada();
    }

    @Test
    public void siElClienteLogueadoEntraAUnaVistaSinTenerPlanLoRedirigeALaVistaParaComprarPlan() throws Exception {
        givenUnUsuarioLogueado();
        whenUsuarioEntraAVerLasClasesYNoTienePlan();
        thenElPreHandlerRedirigeALaVistaDePLanes();
    }

    private void givenUnUsuarioLogueado() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("usuarioId")).thenReturn(1L);
    }

    private void givenElLlamadoAlMetodoParaConseguirLaUrl() {
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("elSuperGym.com");
        when(request.getServerPort()).thenReturn(80);
        when(request.getContextPath()).thenReturn("/miApp");
        when(request.getServletPath()).thenReturn("/servletCtx");
        when(request.getPathInfo()).thenReturn("/mostrar-turnos");
        when(request.getQueryString()).thenReturn("");
    }

    private void whenUsuarioEntraALosTurnosNoHaceNadaElPostHandle() throws Exception {
        when(modelAndView.getViewName()).thenReturn("http://misupergym.com/mostrar-clases");
        when(request.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        when(request.getSession().getAttribute("cliente")).thenReturn(true);
        loginServiceInterceptor.postHandle(request, response, handler, modelAndView);
    }

    private void whenUsuarioEntraAlLoginLoMandaALosTurnos() throws Exception {
        when(modelAndView.getViewName()).thenReturn("http://misupergym.com/login");
        when(request.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        when(request.getSession().getAttribute("cliente")).thenReturn(true);
        loginServiceInterceptor.postHandle(request, response, handler, modelAndView);
    }

    private boolean whenUsuarioLogueadoEntraAUrlPreHandlerLeDejaContinuar() throws Exception {
        return loginServiceInterceptor.preHandle(request, response, handler);
    }

    private boolean whenUnUsuarioIntentaEntrarAUnaUrlQueNoSeaLoginYNoEstaLogueadoElPreHandlerDevuelveFalseParaCortarEjecucionYRedirige() throws Exception {
        givenElLlamadoAlMetodoParaConseguirLaUrl();
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("usuarioId")).thenReturn(null);
        return loginServiceInterceptor.preHandle(request, response, handler);
    }

    private void whenUsuarioEntraAVerLasClasesYNoTienePlan() throws Exception {
        when(modelAndView.getViewName()).thenReturn("http://misupergym.com/mostrar-clases");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("plan")).thenReturn(Plan.NINGUNO);
        when(request.getSession().getAttribute("cliente")).thenReturn(true);
        loginServiceInterceptor.preHandle(request, response, handler);
    }

    private void thenElPostHandlerNoHaceNada() throws IOException {
        verify(response, times(0)).sendRedirect("/proyecto_limpio_spring_war_exploded/mostrar-clases");
    }

    private void thenElPostHandlerRedirigeAsuarioALosTurnos() throws IOException {
        verify(response, times(1)).sendRedirect("/proyecto_limpio_spring_war_exploded/mostrar-clases");
    }

    private void thenFueRedirigedoPorElPrehandler(boolean pasoElPreHandler) throws IOException {
        verify(response, times(1)).sendRedirect("/proyecto_limpio_spring_war_exploded/login");
        Assert.assertFalse(pasoElPreHandler);
    }

    private void thenElPreHandlerDejaContinuarYNoRedirige(boolean pasoElPreHandler) throws IOException {
        verify(response, times(0)).sendRedirect("/proyecto_limpio_spring_war_exploded/login");
        Assert.assertTrue(pasoElPreHandler);
    }

    private void thenElPreHandlerRedirigeALaVistaDePLanes() throws IOException {
        verify(response, times(1)).sendRedirect("/proyecto_limpio_spring_war_exploded/planes");
    }
}