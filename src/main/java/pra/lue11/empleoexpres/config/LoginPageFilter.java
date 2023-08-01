package pra.lue11.empleoexpres.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

class LoginPageFilter extends GenericFilterBean {

    private static final String HOME_PATH = "/home";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
              && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
              && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
            ((HttpServletResponse)response).sendRedirect(HOME_PATH);
        }
        chain.doFilter(request, response);
    }
}