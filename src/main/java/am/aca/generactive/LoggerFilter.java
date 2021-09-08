package am.aca.generactive;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "LoggerFilter", value = "/auth/*")
public class LoggerFilter extends HttpFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println(req.getRequestURI());
        res.setStatus(400);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
