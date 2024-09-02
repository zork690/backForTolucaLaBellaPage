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

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.CategoriaService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/categorias")
public class CategoriaRestController {

	private static final Logger LOG = LogManager.getLogger(CategoriaRestController.class);

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/listarCategorias")
	public ResponseEntity<SimpleResponse> listarCategorias(){
		SimpleResponse response = new SimpleResponse();
		try {
			LOG.info("Getting categorias ...");
			response.setResult(new ArrayList<Categoria>(this.categoriaService.getCategorias()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar las categorias: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/crearCategoria")
	public ResponseEntity<SimpleResponse> insertarCategoria(@Validated @RequestBody CategoriaVo categoria){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Categoria enviada: {}", categoria);
			this.categoriaService.insertarCategoria(categoria);
			srResult.setResult("Categoría insertada o actualizada correctamente");
		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Violación de reglas al insertar o actualizar categoría: {}" ,cExc.getLocalizedMessage());
			srResult.setError("Violación de reglas al insertar o actualizar categoría.");
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			LOG.error("Ocurrio un error al guardar la categoría: {}", e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
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
