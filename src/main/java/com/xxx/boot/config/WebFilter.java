package com.xxx.boot.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class WebFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
      HttpServletResponse httpResponse = (HttpServletResponse) response; 
      httpResponse.setHeader("Access-Control-Allow-Origin", "*"); //허용대상 도메인
      httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); 
      httpResponse.setHeader("Access-Control-Max-Age", "3600"); 
      httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept"); 
      chain.doFilter(request, response); 
        
        
    }

}
