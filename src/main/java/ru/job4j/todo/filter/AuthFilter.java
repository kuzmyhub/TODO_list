package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private final Set<String> addresses = Set.of(
            "/formRegistrationUser",
            "/registration",
            "/formLoginUser",
            "/formLoginUser?loginSuccess=false",
            "/login"
    );

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (findAddress(uri)) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/formLoginUser");
            return;
        }
        filterChain.doFilter(req, res);
    }

    private boolean findAddress(String uri) {
        return addresses.stream().anyMatch(uri::endsWith);
    }
}
