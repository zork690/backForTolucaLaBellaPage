package com.mx.zorktec.apiBackAdminTienda.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.apiBackAdminTienda.entities.Imagen;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.CatalogoVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.ImagenVo;
import com.mx.zorktec.apiBackAdminTienda.services.ImagenService;
import com.mx.zorktec.apiBackAdminTienda.utilities.RespuestasRest;

@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "http://localhost:8082" ,"http://localhost" })

@RestController
@RequestMapping("/imagenes")
public class ImagenesRestController {

	private static final Logger LOG = LogManager.getLogger(ImagenesRestController.class);
	
	@Autowired
	private ImagenService imagenService;
	
	@GetMapping("/listarCatalogo")
	public ResponseEntity<RespuestasRest> listarCatalogo() {
		
		RespuestasRest respuesta = new RespuestasRest();
		
		try {
			List<String> imagenesCatalogo = new ArrayList<String>();
			imagenesCatalogo.add("IMAGEN UNO");
			imagenesCatalogo.add("IMAGEN DOS");
			respuesta.setResult(new ArrayList<>(imagenesCatalogo));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}catch(Exception e){
			LOG.error("Ocurrio un error al listar el catálogo", e);
			respuesta.setError(e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
	
	@PostMapping("/insertarCatalogo")
	public ResponseEntity<RespuestasRest> insertarCatalogo(@RequestBody CatalogoVo catalogo) {
	
		RespuestasRest respuesta = new RespuestasRest();
		
		try {
			
			LOG.error("CATALOGO RECIBIDO: "+catalogo);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
		}catch(Exception e){
			LOG.error("Ocurrio un error al insertar el catálogo: "+e);
			respuesta.setError(e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	@PostMapping("/insertar")
	public ResponseEntity<RespuestasRest> insertarImagen(@RequestBody ImagenVo imagen) {
	
		RespuestasRest respuesta = new RespuestasRest();
		
		try {
			
			this.imagenService.insertarImagen(imagen);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
		}catch(Exception e){
			LOG.error("Ocurrio un error al insertar la imágen: "+e);
			respuesta.setError(e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/eliminar")
	public ResponseEntity<RespuestasRest> eliminarImagen(@RequestBody List<ImagenVo> imagenes) {
	
		RespuestasRest respuesta = new RespuestasRest();
		
		try {
			
			this.imagenService.eliminarImagenes(imagenes);
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
		}catch(Exception e){
			LOG.error("Ocurrio un error al eliminar la imágen: "+e);
			respuesta.setError(e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getImagenes")
	public ResponseEntity<RespuestasRest> getImagenes() {
		
		RespuestasRest response = new RespuestasRest();
		List<ImagenVo> imagenesList = new ArrayList<ImagenVo>();
		
		try {
			imagenesList = this.imagenService.findAllImagenes();
			if (imagenesList == null) {
				response.setError("No se encontró información");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setResult(new ArrayList<>(imagenesList));
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}catch (DataAccessException e) {
			LOG.error("Error al consultar imágenes", e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getImagenesTodas")
	public ResponseEntity<RespuestasRest> getImagenesTodas() {
		
		RespuestasRest response = new RespuestasRest();
		List<Imagen> imagenesList = new ArrayList<Imagen>();
		
		try {
			imagenesList = this.imagenService.encontrarTodas();
			if (imagenesList == null) {
				response.setError("No se encontró información");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setResult(new ArrayList<>(imagenesList));
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}catch (DataAccessException e) {
			LOG.error("Error al consultar imágenes", e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/actualizar")
	public ResponseEntity<RespuestasRest> actualizarImagen(@RequestBody ImagenVo imagen) {
		
		RespuestasRest response = new RespuestasRest();	
		try {
			
			this.imagenService.actualizarImagen(imagen);
			
		}catch (DataAccessException e) {
			LOG.error("Error al actualizar la imágen ", e);
			response.setError(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
