package com.ioutime.filter;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.util.JwtUtil;
import com.ioutime.util.ReqBody;
import com.ioutime.util.RespBody;

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
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        if( Objects.equals(method, "GET") ){
            String token = request.getParameter("token");
            boolean verifys = JwtUtil.verifys(token);
            if(token==null || verifys){
                filterChain.doFilter(request, response);
            }else {
                RespBody.response(response,"400","Bad Request2!","");
                System.out.println("Bad Request2!");
            }
        }else{
            if(Objects.equals(method,"OPTIONS"))
                response.setStatus(HttpStatus.SC_OK); // 或者直接输入204、HttpStatus.SC_OK、200，等这些都可以   import org.apache.http.HttpStatus;
            filterChain.doFilter(request, response);
        }


//        if(Objects.equals(method,"OPTIONS")){
//            filterChain.doFilter(request, response);
//        }
//        /*/api/hello*/
//        else if(Objects.equals(method,"GET") && Objects.equals(uri, "/api/hello")){
//            filterChain.doFilter(request, response);
//        }
//        /*/api/login*/
//        else if(Objects.equals(method,"POST") && Objects.equals(uri, "/api/login")){
//            filterChain.doFilter(request, response);
//        }
//        /*/api/register*/
//        else if(Objects.equals(method,"POST") && Objects.equals(uri, "/api/register")){
//            filterChain.doFilter(request, response);
//        }
//        else if( Objects.equals(method, "GET") ){
//            String token = request.getParameter("token");
//            boolean verifys = JwtUtil.verifys(token);
//            if(token==null || verifys){
//                filterChain.doFilter(request, response);
//            }else {
//                RespBody.response(response,"400","Bad Request2!","");
//                System.out.println("Bad Request2!");
//            }
//        }
//        else if(Objects.equals(method,"PUT") || Objects.equals(method,"DELETE")){
//            filterChain.doFilter(request, response);
//        }
//        else {
//            RespBody.response(response,"400","Bad Request4!","");
//            System.out.println("Bad Request4!");
//        }
    }

    @Override
    public void destroy() {

    }
}
