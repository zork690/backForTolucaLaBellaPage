package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.RegistroVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ResponseAccessTokenVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UsuarioKeycloakVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.KeycloakService;

@Service
public class KeycloakServiceImpl implements KeycloakService {

	private static final Logger LOG = LogManager.getLogger(KeycloakServiceImpl.class);

	@Value("${keycloak.resource}")
	private String clientId;

	@Value("${keycloak.credentials.secret}")
	private String clientSecret;

	@Value("${zorktech.keycloak.username}")
	private String userName;

	@Value("${zorktech.keycloak.password}")
	private String password;

	@Value("${zorktech.keycloak.grantType}")
	private String grantType;
	
	@Value("refresh_token")
	private String grantTypeForRefresh;

	@Value("${zorktech.keycloak.url.token}")
	private String tokenUrl;

	@Value("${zorktech.keycloak.url.register}")
	private String urlRegister;

	/*@Value("${zorktech.keycloak.url.send.mail}")
	private String urlSendMail;*/

	@Value("${zorktech.keycloak.url.execute.actions.mail}")
	private String urlSendMail;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${zorktech.keycloak.url.get.resource.id}")
	private String gettingClientIdUrl;

	@Value("${zorktech.keycloak.url.get.roles.info}")
	private String gettingRolesInfoUrl;
	
	@Value("${zorktech.keycloak.url.set.role.user}")
	private String settingRoleUrl;

	@Autowired
	Keycloak keycloak;

	@Override
	public void registrarUsuario(RegistroVo usuario) throws JSONException, RestClientException, Exception {
		UsuarioKeycloakVo usuarioKeycloak = new UsuarioKeycloakVo();
		usuarioKeycloak.setUsername(usuario.getUsuario());
		usuarioKeycloak.setFirstName(usuario.getNombre());
		usuarioKeycloak.setLastName(usuario.getApellido());
		usuarioKeycloak.setEmail(usuario.getCorreo());
		usuarioKeycloak.setEmailVerified(false);
		usuarioKeycloak.setEnabled(true);
		CredencialesVo credenciales = new CredencialesVo();
		credenciales.setTemporary(false);
		credenciales.setType("password");
		credenciales.setValue(usuario.getPassword());
		List<CredencialesVo> credencialesList = new ArrayList<CredencialesVo>();
		credencialesList.add(credenciales);
		usuarioKeycloak.setCredentials(credencialesList);

		this.sendRegistrationRequest(this.generateAccessToken(), usuarioKeycloak);
		String id = this.getClientIdRequest();
		JSONObject roleInfo = this.gettingRolesInfo(id);
		this.sendSettingRoleRequest(usuarioKeycloak, roleInfo);
	}

	@Override
	public ResponseAccessTokenVo loginUsuario(LoginVo usuario) throws JSONException, RestClientException {
		LOG.info("Haciendo login ...");
		JSONObject responseJsonObject = this.generateAccessToken(usuario);
		ResponseAccessTokenVo responseAcessTokenVo = new ResponseAccessTokenVo();
		responseAcessTokenVo.setAccessToken(responseJsonObject.getString("access_token"));
		responseAcessTokenVo.setExpiresIn(
				Integer.parseInt(responseJsonObject.getString("expires_in"))
				);
		responseAcessTokenVo.setRefreshExpiresIn(
				Integer.parseInt(responseJsonObject.getString("refresh_expires_in"))
				);
		responseAcessTokenVo.setRefreshToken(responseJsonObject.getString("refresh_token"));
		responseAcessTokenVo.setTokenType(responseJsonObject.getString("token_type"));
		responseAcessTokenVo.setNotBeforePolicy(
				Integer.parseInt(responseJsonObject.getString("not-before-policy"))
				);
		responseAcessTokenVo.setSessionState(responseJsonObject.getString("session_state"));
		responseAcessTokenVo.setScope(responseJsonObject.getString("scope"));

		return responseAcessTokenVo;

	}
	
	@Override
	public ResponseAccessTokenVo refreshUsuario(String refreshToken) throws JSONException, RestClientException {
		LOG.info("Haciendo refresh ...");
		JSONObject responseJsonObject = this.generateAccessTokenWithRefreshToken(refreshToken);
		ResponseAccessTokenVo responseAcessTokenVo = new ResponseAccessTokenVo();
		responseAcessTokenVo.setAccessToken(responseJsonObject.getString("access_token"));
		responseAcessTokenVo.setExpiresIn(
				Integer.parseInt(responseJsonObject.getString("expires_in"))
				);
		responseAcessTokenVo.setRefreshExpiresIn(
				Integer.parseInt(responseJsonObject.getString("refresh_expires_in"))
				);
		responseAcessTokenVo.setRefreshToken(responseJsonObject.getString("refresh_token"));
		responseAcessTokenVo.setTokenType(responseJsonObject.getString("token_type"));
		responseAcessTokenVo.setNotBeforePolicy(
				Integer.parseInt(responseJsonObject.getString("not-before-policy"))
				);
		responseAcessTokenVo.setSessionState(responseJsonObject.getString("session_state"));
		responseAcessTokenVo.setScope(responseJsonObject.getString("scope"));

		return responseAcessTokenVo;

	}

	@Override
	public void enviarEmail(String email) throws Exception {
		List<UserRepresentation> userList = this.findUserByEmail(email);
		if(userList.isEmpty()) {
			throw new Exception("Email no ha sido encontrado");
		}
		UserRepresentation userRepresentation = userList.stream().findFirst().orElse(null);
		this.sendConfirmationEmailRequest(this.generateAccessToken(), userRepresentation.getId()
				, new String[] {"VERIFY_EMAIL"});
	}

	@Override
	public void resetPassword(String email) throws Exception {
		List<UserRepresentation> userList = this.findUserByEmail(email);
		if(userList.isEmpty()) {
			throw new Exception("Email no ha sido encontrado");
		}
		UserRepresentation userRepresentation = userList.stream().findFirst().orElse(null);
		this.sendConfirmationEmailRequest(this.generateAccessToken(), userRepresentation.getId()
				, new String[] {"UPDATE_PASSWORD"});
	}

	private List<UserRepresentation> findUserByEmail(String email){
		boolean exact = true;
		List<UserRepresentation> user = this.keycloak.realm(this.realm)
				.users()
				.searchByEmail(email, exact);

		LOG.info("User with email {}", user.stream()
				.map(u->u.getEmail())
				.collect(Collectors.toList())
				);

		LOG.info("ID: {}", user.stream()
				.map(u->
				u.getId()
						)
				.collect(Collectors.toList())
				);
		return user;
	}

	private void sendRegistrationRequest(String token, UsuarioKeycloakVo usuario) throws RestClientException {
		RestTemplate restTemplateRegister = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+token);

		Gson gson = new Gson();
		String gsonString = gson.toJson(usuario);
		HttpEntity<String> entity = new HttpEntity<String>(gsonString.toString(),headers);
		String response = restTemplateRegister.exchange(urlRegister
				, HttpMethod.POST
				, entity
				, String.class).getBody();

		LOG.info("Respuesta del registro: {}", response);
	}

	private void sendConfirmationEmailRequest(String token, String idUser
			,String[] bodyArray) throws RestClientException {
		Map<String, String> urlParamsMap = new HashMap<String, String>();
		urlParamsMap.put("id", idUser);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.urlSendMail);	
		String url  = urlBuilder.buildAndExpand(urlParamsMap).toUriString();

		LOG.info("URL to send email: {}", url);

		RestTemplate restTemplateRegister = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+token);		

		LOG.info("body Array: {}", Arrays.toString(bodyArray));

		HttpEntity<String[]> entity = new HttpEntity<String[]>(bodyArray, headers);
		restTemplateRegister.exchange(url
				, HttpMethod.PUT
				, entity
				, String[].class).getBody();
	}


	private String generateAccessToken() throws JSONException, RestClientException {
		String token = null;
		RestTemplate restTemplateToken = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", this.grantType);
		map.add("client_id", this.clientId);
		map.add("client_secret", this.clientSecret);
		map.add("username", this.userName);
		map.add("password", this.password);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		LOG.info("Token solicitud: {}", entity.getBody());
		String jsonResponse = restTemplateToken.exchange(tokenUrl
				, HttpMethod.POST
				, entity
				, String.class).getBody();

		JSONObject jsonObject;

		jsonObject = new JSONObject(jsonResponse);
		token = jsonObject.getString("access_token");
		LOG.info("Keycloak token generado: {}", token);
		return token;
	}


	private JSONObject generateAccessToken(LoginVo usuario) throws JSONException, RestClientException {
		RestTemplate restTemplateToken = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", this.grantType);
		map.add("client_id", this.clientId);
		map.add("client_secret", this.clientSecret);
		map.add("username", usuario.getEmail());
		map.add("password", usuario.getPassword());
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		LOG.info("Token solicitud: {}", entity.getBody());
		String jsonResponse = restTemplateToken.exchange(tokenUrl
				, HttpMethod.POST
				, entity
				, String.class).getBody();

		JSONObject jsonObject;

		jsonObject = new JSONObject(jsonResponse);
		return jsonObject;
	}
	
	private JSONObject generateAccessTokenWithRefreshToken(String refreshToken) throws JSONException, RestClientException {
		RestTemplate restTemplateToken = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", this.grantTypeForRefresh);
		map.add("client_id", this.clientId);
		map.add("client_secret", this.clientSecret);
		map.add("refresh_token", refreshToken);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		LOG.info("Token solicitud: {}", entity.getBody());
		String jsonResponse = restTemplateToken.exchange(tokenUrl
				, HttpMethod.POST
				, entity
				, String.class).getBody();

		JSONObject jsonObject;

		jsonObject = new JSONObject(jsonResponse);
		return jsonObject;
	}

	private void sendSettingRoleRequest(UsuarioKeycloakVo usuarioKeycloak, JSONObject roleInfo) throws Exception {
		LOG.info("sending request to set user role...");
		
		List<UserRepresentation> userList = this.findUserByEmail(usuarioKeycloak.getEmail());
		if(userList.isEmpty()) {
			throw new Exception("Email no ha sido encontrado");
		}
		UserRepresentation userRepresentation = userList.stream().findFirst().orElse(null);
		
		RestTemplate restTemplateRegister = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+this.generateAccessToken());
		
		

		Map<String, String> urlParamsMap = new HashMap<String, String>();
		urlParamsMap.put("userId", userRepresentation.getId());
		urlParamsMap.put("clientUid", roleInfo.getString("containerId"));

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.settingRoleUrl);	
		String url  = urlBuilder.buildAndExpand(urlParamsMap).toUriString();
		
		
		JSONArray dataList = new JSONArray();
		dataList.put(roleInfo);
		LOG.info("Data to role: {}", dataList);
		
		HttpEntity<String> entity = new HttpEntity<String>(dataList.toString()
				,headers);
		String response = restTemplateRegister.exchange(url
				, HttpMethod.POST
				, entity
				, String.class).getBody();

		LOG.info("RESPONSE: {}", response);
		
	}

	private String getClientIdRequest() throws JSONException {
		LOG.info("getting client id ...");
		RestTemplate restTemplateRegister = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+this.generateAccessToken());

		Map<String, String> urlParamsMap = new HashMap<String, String>();
		urlParamsMap.put("sourceId", this.clientId);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.gettingClientIdUrl);	
		String url  = urlBuilder.buildAndExpand(urlParamsMap).toUriString();

		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		String response = restTemplateRegister.exchange(url
				, HttpMethod.GET
				, entity
				, String.class).getBody();

		LOG.info("RESPONSE: {}", response);
		JSONArray jsonArray = new JSONArray(response);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String id = jsonObject.getString("id");		
		LOG.info("id client: {}", id);
		return id;
	}

	private JSONObject gettingRolesInfo(String sourceId) throws JSONException {
		String roleToSearch = "user";
		JSONObject roleInfo = null;
		LOG.info("getting roles info ...");
		RestTemplate restTemplateRegister = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+this.generateAccessToken());

		Map<String, String> urlParamsMap = new HashMap<String, String>();
		urlParamsMap.put("clientId", sourceId);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.gettingRolesInfoUrl);	
		String url  = urlBuilder.buildAndExpand(urlParamsMap).toUriString();

		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		String response = restTemplateRegister.exchange(url
				, HttpMethod.GET
				, entity
				, String.class).getBody();

		LOG.info("RESPONSE: {}", response);
		JSONArray jsonArray = new JSONArray(response);
		for(int i=0; i<jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(jsonObject.getString("name").equals(roleToSearch)) {
				LOG.info("ROLE INFO: {}", jsonObject);
				roleInfo = jsonObject;
			}
		}
		return roleInfo;
	}
}
