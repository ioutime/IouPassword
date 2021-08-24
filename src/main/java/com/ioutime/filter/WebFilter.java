package com.ioutime.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.apache.http.HttpStatus;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:08
 */

public class WebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String method = request.getMethod();
        String uri = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("XDomainRequestAllowed","1");
        if(Objects.equals(method,"OPTIONS"))
            response.setStatus(HttpStatus.SC_OK);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
