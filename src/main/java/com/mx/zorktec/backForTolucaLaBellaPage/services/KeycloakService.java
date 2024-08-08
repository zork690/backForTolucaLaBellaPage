package com.mx.zorktec.backForTolucaLaBellaPage.services;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.client.RestClientException;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.RegistroVo;

public interface KeycloakService {
	public void registrarUsuario(RegistroVo usuario) throws JSONException, RestClientException;
}
