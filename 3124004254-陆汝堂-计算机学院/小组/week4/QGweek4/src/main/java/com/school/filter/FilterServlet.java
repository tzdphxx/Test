package com.school.filter;

import com.school.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class FilterServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 在Servlet中设置响应头
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");


        String uri = request.getRequestURI();

        if(uri.contains("/login.html") || uri.contains("/user/selectByUserName") ||
                uri.contains("/css/") || uri.contains("/js/")) {
            filterChain.doFilter(request, response);
            return;
        }


        HttpSession session = request.getSession(false);
        if (session == null||session.getAttribute("user")==null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
