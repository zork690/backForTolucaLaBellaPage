package com.mx.zorktec.backForTolucaLaBellaPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@KeycloakConfiguration
public class APISecurityConfig 
extends KeycloakWebSecurityConfigurerAdapter
{

	private static final Logger LOG = LogManager.getLogger(APISecurityConfig.class);
	
	/*@Autowired
	private RequestHeaderFilter requestHeaderFilter;*/
	
	@Autowired
	private AuthenticationEntryPoint entryPoint;
	
	private static final String[] AUTH_LIST = {
			"/inicio"
			, "/ubicaciones"
			, "/getConteoClientes"
			, "/negocios/listarNegocios"
			, "/negocios/listarNegocios/{negocioId}"
			, "/negocios/listar/{subcategoria}"
			, "/negocios/insertarNegocio"
			, "/negocios/listarNegociosFavoritos"
			, "/negocios/listarNegociosNuevos"
			, "/usuarios/registrar"
			, "/usuarios/login"
			, "/usuarios/enviarEmailConfirmacion"
			, "/usuarios/resetPassword"
			, "/swagger-resources/**"
			, "/swagger-ui.html"
			, "/webjars/**"
			, "/v2/api-docs"
			, "/categorias/listarCategorias"
			, "/subcategorias/listar"
			, "/subcategorias/listar/{categoriaName}"
			,"/noticias/listar"
			,"/articulos/listar"
	};
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }
	
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
	
	/*@Bean
    @Primary
    public KeycloakConfigResolver keycloakConfigResolver(KeycloakSpringBootProperties properties) {
        return new CustomKeycloakSpringBootConfigResolver(properties);
    } */
	
	@Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
        http.csrf(csrf ->csrf.disable())
            .exceptionHandling(handling -> handling.authenticationEntryPoint(entryPoint))
            //.addFilterAfter(myFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests(requests -> {
            	requests
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(AUTH_LIST).permitAll()
                        .antMatchers("/panel-socios/listarNegocios/**").hasRole("user")
                        .antMatchers("/negocios/listarNegociosTodos/**").hasRole("admin")
                        .anyRequest().authenticated();
                        });
        
	}
	
	//@Bean
	/*public JWTAuthorizationFilter myFilter() {
	  return new JWTAuthorizationFilter();
	}*/
	
	
	//@Bean
    /*public SimpleAuthorityMapper grantedAuthorityMapper() {
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");
        grantedAuthorityMapper.setConvertToUpperCase(true);
        return grantedAuthorityMapper;
    }*/

	/*@Bean
	  public FilterRegistrationBean<RequestHeaderFilter> loggingFilter() {
	    FilterRegistrationBean<RequestHeaderFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(requestHeaderFilter);
	    registrationBean.addUrlPatterns("/api/back/tienda/*");
	    //registrationBean.setOrder(1);
	    return registrationBean;
	  }*/
}
