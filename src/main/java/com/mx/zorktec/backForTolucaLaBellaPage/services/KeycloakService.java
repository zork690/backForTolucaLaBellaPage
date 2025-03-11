package com.mx.zorktec.backForTolucaLaBellaPage.services;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.client.RestClientException;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.RegistroVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ResponseAccessTokenVo;

public interface KeycloakService {
	public void registrarUsuario(RegistroVo usuario) throws JSONException, RestClientException, Exception;
	public ResponseAccessTokenVo loginUsuario(LoginVo usuario) throws JSONException, RestClientException;
	public ResponseAccessTokenVo refreshUsuario(String refreshToken) throws JSONException, RestClientException;
	public void enviarEmail(String email) throws Exception;
	public void resetPassword(String email) throws Exception;
}
