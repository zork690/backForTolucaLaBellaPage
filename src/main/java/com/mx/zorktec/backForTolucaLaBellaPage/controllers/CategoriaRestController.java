package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
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
