package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Clientes;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ConteoClientesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ClientesService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.RespuestasRest;

@RestController
public class ClientesController {

	private static final Logger LOG = LogManager.getLogger(ClientesController.class);
	
	@Autowired
	private ClientesService clientesService;
	
	@GetMapping("/getConteoClientes")
	public ResponseEntity<RespuestasRest> getImagenes() {
		
		RespuestasRest response = new RespuestasRest();
		ConteoClientesVo conteoClientesVo = new ConteoClientesVo();
		
		try {
			Clientes c = this.clientesService.getConteoClientes();
			if (c == null) {
				response.setError("No se encontró el conteo de clientes");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			conteoClientesVo.setId(c.getId());
			conteoClientesVo.setConteo(c.getConteo());
			response.setResult(conteoClientesVo);
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}catch (DataAccessException e) {
			LOG.error("Error al consultar imágenes", e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
