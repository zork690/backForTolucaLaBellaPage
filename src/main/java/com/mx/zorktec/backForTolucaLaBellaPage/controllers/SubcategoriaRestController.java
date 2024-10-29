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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoria_Vo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.SubCategoriaService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/subcategorias")
public class SubcategoriaRestController {

	private static final Logger LOG = LogManager.getLogger(SubcategoriaRestController.class);

	@Autowired
	private SubCategoriaService subcategoriaService;
	
	@GetMapping("/listar")
	public ResponseEntity<SimpleResponse> listarSubCategorias(){
		SimpleResponse response = new SimpleResponse();
		try {
			LOG.info("Getting subcategorias ...");
			response.setResult(new ArrayList<SubCategoriaVo>(this.subcategoriaService.getSubCategorias()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar las subcategorias: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listar/{categoriaName}")
	public ResponseEntity<SimpleResponse> listarByCategoriaName(@PathVariable String categoriaName){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<SubCategoriaVo>
			(this.subcategoriaService.getSubCategoriasByCategoriaName(categoriaName))
					);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar las subcategorias: {}",error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/crear")
	public ResponseEntity<SimpleResponse> insertarSubCategoria(@Validated @RequestBody SubCategoria_Vo subcategoria){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("SubCategoria enviada: {}", subcategoria);
			this.subcategoriaService.insertarSubCategoria(subcategoria);
			srResult.setResult("Subcategoría insertada o actualizada correctamente");
		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Violación de reglas al insertar o actualizar subcategoría: {} " ,cExc.getLocalizedMessage());
			srResult.setError("Violación de reglas al insertar o actualizar subcategoría.");
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
