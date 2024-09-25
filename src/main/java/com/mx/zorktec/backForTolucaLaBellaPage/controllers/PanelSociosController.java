package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloReceivedVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ArticuloService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/panel-socios")
public class PanelSociosController {

	private static final Logger LOG = LogManager.getLogger(PanelSociosController.class);

	@Autowired
	private ArticuloService articuloService;

	@PostMapping("/articulos/insertar")
	public ResponseEntity<SimpleResponse> insertarArticulo(@Validated @RequestBody ArticuloReceivedVo articulo){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Articulo enviado: "+articulo);

			this.articuloService.insertarArticulo(articulo);
			srResult.setResult("Articulo insertado o editado correctamente");

		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Articulo con error de integridad en la base: {} " ,cExc.getLocalizedMessage());
			srResult.setError("Existe un error de integridad al insertar o editar articulo: "
					+ cExc.getLocalizedMessage());
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			LOG.error("Ocurrio un error al guardar el articulo: {}", e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}
	
	@GetMapping("/articulos/listar")
	public ResponseEntity<SimpleResponse> listarArticulos(){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<ArticuloVo>(this.articuloService.getArticulos()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los articulos del socio: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

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
