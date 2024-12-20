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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SimpleResponse;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloReceivedVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ListUpdateArticuloImagesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NoticiaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoria_Vo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ArticuloService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.CategoriaService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesArticuloService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NoticiaService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.SubCategoriaService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/panel-socios")
public class PanelSociosController {

	private static final Logger LOG = LogManager.getLogger(PanelSociosController.class);

	@Autowired
	private ArticuloService articuloService;

	@Autowired
	private ImagenesArticuloService imagenesArticuloService;
	
	@Autowired
	private NoticiaService noticiaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private SubCategoriaService subcategoriaService;

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

	@PostMapping("/articulos/imagenes/insertar")
	public ResponseEntity<SimpleResponse> insertarImagenArticulo(@Validated @RequestBody ImagenesArticuloVo imagenes){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Imágenes recibidas:");
			this.imagenesArticuloService.processingImagefromArticulo(imagenes);
			srResult.setResult("Imágenes insertadas correctamente");

		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Violación de regla de integridad al insertar imágenes: {} " ,cExc.getLocalizedMessage());
			srResult.setError("Violación de regla de integridad al insertar imágenes "
					+ cExc.getLocalizedMessage());
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOG.error("Ocurrio un error al guardar las imágenes: {}", e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}

	@PostMapping("/articulos/imagenes/editar")
	public ResponseEntity<SimpleResponse> actualizarImagenes(
			@RequestBody
			@Validated
			ListUpdateArticuloImagesVo imagenes
			){
		SimpleResponse srResult = new SimpleResponse();
		Map<String, String> resultados =  this.imagenesArticuloService.updateImages(imagenes);
		if(resultados.isEmpty()) {
			srResult.setResult("Imágenes actualizadas correctamente");
			srResult.setMessage("OK");
		}else {
			srResult.setError("Errores al editar imagenes");
			srResult.setValidations(resultados);
		}
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}
	
	
	@PostMapping("/noticias/crear")
	public ResponseEntity<SimpleResponse> crearNoticia(@Validated @RequestBody NoticiaVo noticia){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Noticia recibida: {}",noticia.getNoticia());

			this.noticiaService.insertarNoticia(noticia);
			srResult.setResult("Noticia insertada o editada correctamente");

		} 
		catch(org.springframework.dao.DataIntegrityViolationException cExc) {
			LOG.info("Noticia con error de integridad en la base: {} " ,cExc.getLocalizedMessage());
			srResult.setError("Existe un error de integridad al insertar o editar noticia: "
					+ cExc.getLocalizedMessage());
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			LOG.error("Ocurrio un error al guardar la noticia: {}", e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}
	
	
	@PostMapping("/categorias/crearCategoria")
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
	
	@PostMapping("/subcategorias/crear")
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
