package com.mx.zorktec.apiBackAdminTienda.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mx.zorktec.apiBackAdminTienda.entities.SimpleResponse;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.ProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.exceptions.ProveedorException;
import com.mx.zorktec.apiBackAdminTienda.services.ProveedorService;



@RestController
@RequestMapping("/api/back/tienda")
public class ProveedoresRestController {
	
	private static final Logger LOG = LogManager.getLogger(ProveedoresRestController.class);
	
	@Autowired
	private ProveedorService proveedorService;

	@PostMapping("/proveedores/insertarProveedor")
	public ResponseEntity<SimpleResponse> insertarProveedor(@Valid @RequestBody ProveedorVo proveedor){
		SimpleResponse srResult = new SimpleResponse();
		
		try {
			if(proveedor.getCorreo()!= null) {
				this.proveedorService.insertarProveedor(proveedor);
				srResult.setResult("Proveedor insertado correctamente");
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
	

	@PostMapping("/proveedores/settingPassProveedor")
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
