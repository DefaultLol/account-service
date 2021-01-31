package com.app.account.filter;

import com.app.account.exception.ErrorDetails;
import com.app.account.utils.JwtHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Here is my filter");
        System.out.println(request.getLocalName());
        System.out.println(request.getLocalAddr());
        System.out.println(request.getServerName());
        System.out.println(request.getServerPort());
        System.out.println(request.getServletContext());
        System.out.println(request.getHeader("Authorization"));
        JwtHandler.token=request.getHeader("Authorization");
        if(!request.getServerName().equals("ensaspay-zuul-gateway.herokuapp.com")){
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
    }

    private byte[] restResponseBytes(ErrorDetails eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }
}
