package com.mx.zorktec.apiBackAdminTienda;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	
	@Value("${secure.secretKey}")
	private String SECRET;		
	private static final Logger LOG = LogManager.getLogger(JWTAuthorizationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			if (existeJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
					SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		}catch(SignatureException e){
			LOG.error("OCURRIO UN ERROR CON EL TOKEN: "+ e.getLocalizedMessage());
			request.setAttribute("badSignature", e.getMessage());
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}catch(ExpiredJwtException e) {
			request.setAttribute("expired", e.getMessage());
			LOG.error("OCURRIO UN ERROR CON EL TOKEN: "+ e.getLocalizedMessage());
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}catch(UnsupportedJwtException | MalformedJwtException e) {
			LOG.error("OCURRIO UN ERROR CON EL TOKEN: "+ e.getLocalizedMessage());
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		
		LOG.info("JWT TOKEN: "+jwtToken);
		
		return Jwts.parser().setSigningKey(this.SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
}
