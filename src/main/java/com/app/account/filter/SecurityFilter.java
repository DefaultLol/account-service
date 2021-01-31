package com.app.account.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Here is my filter");
        System.out.println(request.getLocalName());
        System.out.println(request.getLocalAddr());
        chain.doFilter(request,response);
    }
}
