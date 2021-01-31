package com.app.account.filter;

import com.app.account.exception.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Here is my filter");
        System.out.println(request.getLocalName());
        System.out.println(request.getLocalAddr());
        System.out.println(request.getServerName());
        System.out.println(request.getServerPort());
        System.out.println(request.getServletContext());
        if(!request.getLocalAddr().equals("172.19.99.90")){
            //ANY POJO CLASS
            // ErrorResponse is a public return object that you define yourself
            ErrorDetails errorResponse = new ErrorDetails();
            errorResponse.setDate(new Date());
            errorResponse.setMessage("Unauthorized Access");
            errorResponse.setDetails("Access not authorized from this address");

            byte[] responseToSend = restResponseBytes(errorResponse);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(401);
            response.getOutputStream().write(responseToSend);
            return;
        }

        chain.doFilter(request,response);
    }

    private byte[] restResponseBytes(ErrorDetails eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }
}
