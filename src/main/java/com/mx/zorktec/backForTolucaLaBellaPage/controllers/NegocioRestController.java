package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NegocioService;


@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class NegocioRestController {
	
	private static final Logger LOG = LogManager.getLogger(NegocioRestController.class);
	
	@Autowired
	private NegocioService negocioService;

	@PostMapping("/negocios/insertarNegocio")
	public ResponseEntity<SimpleResponse> insertarNegocio(@Validated @RequestBody NegocioVo negocio){
		SimpleResponse srResult = new SimpleResponse();
		
		try {
			LOG.info("Negocio enviado: "+negocio);
			if(negocio.getCorreo()!= null) {
				this.negocioService.insertarNegocio(negocio);
				srResult.setResult("Negocio insertado correctamente");
			}
		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Proveedor repetido: " ,cExc);
			srResult.setError("Ya existe un proveedor con el correo indicado.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		 }catch (DataAccessException e) {
			LOG.info("Ocurrio un error al guardar el proveedor: " ,e);
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
		
		
	}
	

	/*@PostMapping("/proveedores/settingPassProveedor")
	public ResponseEntity<SimpleResponse> settingPassProveedor(@Valid @RequestBody SettingPassProveedorVo credenciales){
		SimpleResponse srResult = new SimpleResponse();
		
		try {
			if(credenciales.getPass().equals(credenciales.getConfirm())) {
				this.proveedorService.setPassProveedor(credenciales);
				srResult.setResult("Contraseña generada correctamente");
			}else {
				srResult.setError("Contraseña y confirmación deben ser iguales");
				return new ResponseEntity<>(srResult, HttpStatus.BAD_REQUEST);
			}
		}catch(ProveedorException e) {
			LOG.info("Proveedor exception: " ,e);
			srResult.setError("Proveedor no encontrado o no verificado.");
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}
		catch (DataAccessException e) {
			LOG.info("Ocurrio un error al setear la contraseña: " ,e);
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
		
		
	}*/
	
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
