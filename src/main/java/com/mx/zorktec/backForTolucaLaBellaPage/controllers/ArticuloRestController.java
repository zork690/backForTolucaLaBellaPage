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

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ArticuloService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/articulos")
public class ArticuloRestController {
	
	private static final Logger LOG = LogManager.getLogger(ArticuloRestController.class);
	
	
	@Autowired
	private ArticuloService articuloService;
	
	@GetMapping("/listar")
	public ResponseEntity<SimpleResponse> listarArticulos(){
		SimpleResponse response = new SimpleResponse();
		try {
			LOG.info("Getting articulos...");
			response.setResult(new ArrayList<ArticuloVo>(this.articuloService.getArticulos()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los articulos: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
