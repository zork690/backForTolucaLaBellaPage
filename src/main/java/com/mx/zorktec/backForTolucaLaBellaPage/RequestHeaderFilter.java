package com.mx.zorktec.backForTolucaLaBellaPage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


//@Component
public class RequestHeaderFilter 
//implements Filter
{

	private static final Logger LOG = LogManager.getLogger(RequestHeaderFilter.class);
	
	/*private int MAX_REQUESTS_PER_MINUTE = 1;
	
	@Autowired
	private RedisTemplate<String, Integer> requestCountsPerIpAddress;
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    ServletRequest requestWrapper = null;
	    String body = null;
		
		LOG.info("GETTING DE REQUEST ...");
		LOG.info("GETTING THE DATE: "+ new Timestamp(System.currentTimeMillis()));
		String ipAddress =  getClientIpAddr(((HttpServletRequest)request));
		final String url = ((HttpServletRequest)request).getRequestURL().toString();
		LOG.info("GETTING THE IP: "+ipAddress);
		LOG.info("GETTING THE URL: "+url);
		
		
		if(url.contains("/insertarProveedor")) {
			requestWrapper = this.creatingRequestWrapper(request, requestWrapper);
			body = this.gettingBody(requestWrapper);
		}
		
		if(body != null) {
			if(isMaximumRequestsPerSecondExceeded(this.gettingEmail(body))){
				httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		        httpServletResponse.getWriter().write("Too many requests");
		        return;
			}
		}
		
		
		if(Objects.isNull(requestWrapper)){
			chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
		
	}
	
	private boolean isMaximumRequestsPerSecondExceeded(String email){
	      Integer requests = 0;
	      final ValueOperations<String, Integer> operations = this.requestCountsPerIpAddress.opsForValue();
	      boolean ipKey = this.requestCountsPerIpAddress.hasKey(email);
	      
	      if (ipKey){
	    	  requests = operations.get(email);
	          if(requests > MAX_REQUESTS_PER_MINUTE) {
	        	  requestCountsPerIpAddress.delete(email);
	        	  return true;
		      }
	    	  
	      }else {
	    	  requests = 0;
	    	  
	      }
	      requests++;
    	  operations.set(email, requests, 5, TimeUnit.MINUTES);
	      
	      return false;
	     
	}
	
	private static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	private ServletRequest creatingRequestWrapper(ServletRequest request, ServletRequest requestWrapper) {
		requestWrapper = new RequestHttpWrapper((HttpServletRequest)request);
		return requestWrapper;
	}

	private String gettingBody(ServletRequest requestWrapper) {
		String body = null;
		try {
			body = requestWrapper.getReader()
					.lines().collect(Collectors.joining(System.lineSeparator()));
			LOG.info("El body es: "+ body);
			return body;
		} catch (IOException e) {
			LOG.error("Error obteniendo el body: "+e.getMessage());
			return body;
		}
		
	}
	
	private String gettingEmail(String body) {
		try {
			final JSONObject payload = new JSONObject(body);
			final String email = payload.getString("correo");
			LOG.info("El email es: "+email);
			return email;
		} catch (JSONException e) {
			LOG.error("ERROR JSON_EXCEPTION: "+ e.getLocalizedMessage());
			return null;
		}
	} */
}
