package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.RegistroVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UsuarioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.KeycloakService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/usuarios")
public class UsuariosRestController {

	private static final Logger LOG = LogManager.getLogger(UsuariosRestController.class);

	@Autowired
	private KeycloakService keycloakService;
	
	@GetMapping("/enviarEmailConfirmacion")
	public ResponseEntity<SimpleResponse> enviarEmail(@RequestParam String email) {
		//HERE requestparam email is null
		SimpleResponse response = new SimpleResponse();
		try {
			this.keycloakService.enviarEmail(email);
			response.setResult("email enviado ...");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Ocurrio un error al enviar email: {}", e.getMessage());
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/resetPassword")
	public ResponseEntity<SimpleResponse> resetPassword(@RequestParam String email) {

		SimpleResponse response = new SimpleResponse();
		try {
			this.keycloakService.resetPassword(email);
			response.setResult("email enviado ...");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Ocurrio un error al enviar email: {}", e.getLocalizedMessage());
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/registrar")
	public ResponseEntity<SimpleResponse> registrarUsuario(@Validated @RequestBody RegistroVo usuario) {

		SimpleResponse response = new SimpleResponse();
		try {
			LOG.info("Usuario a registrar: {}", usuario.toString());
			this.keycloakService.registrarUsuario(usuario);
			UsuarioVo usuarioVo = new UsuarioVo();
			usuarioVo.setEmail(usuario.getCorreo());
			response.setResult(usuarioVo);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException | JSONException | RestClientException e) {
			LOG.info("Ocurrio un error al registrar al usuario: {}", e.getMessage());
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<SimpleResponse> loginUusario(@RequestBody LoginVo usuario) {

		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(this.keycloakService.loginUsuario(usuario));
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException | JSONException | RestClientException e) {
			LOG.info("Ocurrio un error al loguear al usuario: {} " ,e.getMessage());
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<SimpleResponse> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		SimpleResponse resultado = new SimpleResponse();
		resultado.setError(errors);
		resultado.setMessage("NOT OK");
		return new ResponseEntity<SimpleResponse>(resultado, HttpStatus.BAD_REQUEST);
	}

}
