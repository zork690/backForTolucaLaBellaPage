package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;
import com.mx.zorktec.backForTolucaLaBellaPage.services.UbicacionesService;

@RestController
@CrossOrigin(origins = "*")
public class UbicacionesController{
	
	private static final Logger LOG = LogManager.getLogger(UbicacionesController.class);

	@Autowired
	private UbicacionesService ubicacionesService;
	
	@GetMapping("/ubicaciones")
	public ResponseEntity<SimpleResponse> getUbicaciones() {
		LOG.info("getting locations ...");
		this.ubicacionesService.getUbicaciones();
		SimpleResponse response = new SimpleResponse();
		response.setResult(new ArrayList<Ubicacion>(this.ubicacionesService.getUbicaciones()));
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
}
