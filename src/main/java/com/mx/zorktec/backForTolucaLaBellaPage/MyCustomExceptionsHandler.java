package com.mx.zorktec.backForTolucaLaBellaPage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;

//@Component
public class MyCustomExceptionsHandler 
//implements AuthenticationEntryPoint
{

	/*@Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleResponse sr = new SimpleResponse();
        
        sr.setError(e.getMessage());
        
        if(httpServletRequest.getAttribute("expired") != null){
        	sr.setError("JWT Token expired");
        }
        
        if(httpServletRequest.getAttribute("badSignature") != null){
        	sr.setError("JWT Token signature exception");
        }
        
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(mapper.writeValueAsString(sr));
    } */

}
