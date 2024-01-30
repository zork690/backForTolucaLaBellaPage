package com.mx.zorktec.apiBackAdminTienda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class APISecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private RequestHeaderFilter requestHeaderFilter;
	
	@Autowired
	private AuthenticationEntryPoint entryPoint;
	
	private static final String[] AUTH_LIST = {
			"/api/back/tienda/proveedores/insertarProveedor"
			,"/api/back/tienda/proveedores/settingPassProveedor/*"
			,"/api/back/tienda/usuarios/login"
			,"/inicio"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
        	.exceptionHandling().authenticationEntryPoint(entryPoint)
        	.and()
			.addFilterAfter(myFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers(AUTH_LIST).permitAll()
			.anyRequest().authenticated();
	}
	
	@Bean
	public JWTAuthorizationFilter myFilter() {
	  return new JWTAuthorizationFilter();
	}

	@Bean
	  public FilterRegistrationBean<RequestHeaderFilter> loggingFilter() {
	    FilterRegistrationBean<RequestHeaderFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(requestHeaderFilter);
	    registrationBean.addUrlPatterns("/api/back/tienda/*");
	    //registrationBean.setOrder(1);
	    return registrationBean;
	  }
}
