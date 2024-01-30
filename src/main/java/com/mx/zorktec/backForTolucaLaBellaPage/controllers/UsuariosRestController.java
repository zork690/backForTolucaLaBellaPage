package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ProveedorService;

//@RestController
//@RequestMapping("/api/back/tienda")
public class UsuariosRestController {
	
private static final Logger LOG = LogManager.getLogger(UsuariosRestController.class);
	
/*	@Autowired
	private ProveedorService proveedorService;
	
	
	@PostMapping("/usuarios/login")
	public ResponseEntity<SimpleResponse> validarUsuario(@RequestBody CredencialesVo usuarioValidar) {
		
		SimpleResponse response = new SimpleResponse();
		try {
			
			LoginVo login = proveedorService.validarProveedor(usuarioValidar);
			if (login == null) {
				response.setError("No se encontró el usuario a validar");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setResult(login);
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			LOG.info("Ocurrio un error al validar al usuario: " ,e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoSuchFieldException e) {
			LOG.info("Error al validar a el usuario: " ,e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalAccessException e) {
			LOG.info("Error al momento de validar el usuario " ,e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} */

}
