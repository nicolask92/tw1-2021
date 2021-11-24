package ar.edu.unlam.tallerweb1.intercepters;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginServiceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long usuarioId = (Long) request.getSession().getAttribute("usuarioId");
        Boolean esCliente = (Boolean) request.getSession().getAttribute("cliente");
        Plan planUsuario = (Plan) request.getSession().getAttribute("plan");
        String uri = this.getURL(request);
        // Judge whether the user logs in or not
        if (usuarioId == null && !uri.endsWith(("login"))) {
            // User not logged in, redirect to login page
            response.sendRedirect("/proyecto_limpio_spring_war_exploded/login");
            return false;
        } else if ((esCliente != null) && (!esCliente || (planUsuario == null || planUsuario.getNombre().equals("Ninguno")))) {
            if (!uri.endsWith(("planes"))) {
                response.sendRedirect("/proyecto_limpio_spring_war_exploded/planes");
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long usuarioId = (Long) request.getSession().getAttribute("usuarioId");

        if (modelAndView.getViewName().endsWith("login") && usuarioId != null) {
            // The login page will no longer be displayed
            response.sendRedirect("/proyecto_limpio_spring_war_exploded/mostrar-clases");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }

    private String getURL(HttpServletRequest req) {

        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
        String contextPath = req.getContextPath();   // /mywebapp
        String servletPath = req.getServletPath();   // /servlet/MyServlet
        String pathInfo = req.getPathInfo();         // /a/b;c=123
        String queryString = req.getQueryString();   // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }
}