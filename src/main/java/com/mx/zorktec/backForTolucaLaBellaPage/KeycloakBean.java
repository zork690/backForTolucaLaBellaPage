package com.mx.zorktec.backForTolucaLaBellaPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakBean {
	
	private static final Logger LOG = LogManager.getLogger(KeycloakBean.class);

	@Value("${keycloak.auth-server-url}")
	private String urlServer;
	
	@Value("${zorktech.master.realm}")
	private String realm;
	
	@Value("${zorktech.master.clientId}")
	private String clientId;
	
	@Value("${zorktech.master.username}")
	private String userName;
	
	@Value("${zorktech.master.password}")
	private String password;

	@Value("${zorktech.keycloak.grantType}")
	private String grantType;
	
	@Bean
	Keycloak keycloak() {
		LOG.info("Getting Keycloak Bean...");
		
	    return KeycloakBuilder.builder()
	      .serverUrl(this.urlServer)
	      .realm(this.realm)
	      .clientId(this.clientId)
	      .grantType(this.grantType)
	      .username(this.userName)
	      .password(this.password)
	      .build();
	}
}
