package com.ioutime.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:08
 */

public class WebFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            servletRequest.setCharacterEncoding("utf-8");
            servletResponse.setCharacterEncoding("utf-8");
            filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
