package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Noticia;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NoticiaService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/noticias")
public class NoticiaRestController {
	
	private static final Logger LOG = LogManager.getLogger(NoticiaRestController.class);
	
	@Autowired
	private NoticiaService noticiaService;
	
	@GetMapping("/listar")
	public ResponseEntity<SimpleResponse> listar(){
		SimpleResponse response = new SimpleResponse();
		try {
			LOG.info("Getting noticias ...");
			response.setResult( new ArrayList<Noticia>( this.noticiaService.getNoticias() ) );
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar las noticias: {}", error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
