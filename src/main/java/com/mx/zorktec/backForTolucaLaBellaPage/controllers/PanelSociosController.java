package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/panel-socios")
public class PanelSociosController {
	
	private static final Logger LOG = LogManager.getLogger(PanelSociosController.class);
	
	@GetMapping("/listarNegocios")
	public ResponseEntity<SimpleResponse> listarNegocios(){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult("Listando negocios para el socio logueado...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los negocios del socio: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
