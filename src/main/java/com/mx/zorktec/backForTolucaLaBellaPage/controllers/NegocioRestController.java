package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UpdateNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;


@RestController
@CrossOrigin(origins = {"*"})
public class NegocioRestController {

	private static final Logger LOG = LogManager.getLogger(NegocioRestController.class);

	@Autowired
	private NegocioService negocioService;

	@Autowired
	private ImagenesNegocioService imagenesNegocioService;

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
			LOG.info("Negocio repetido: {} " ,cExc.getLocalizedMessage());
			srResult.setError("Ya existe un negocio con el correo o con el teléfono indicado.");
			return new ResponseEntity<>(srResult,HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			LOG.error("Ocurrio un error al guardar el negocio:" +e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}

	@PostMapping("/negocios/createNegocioUserLogged")
	public ResponseEntity<SimpleResponse> insertarNegocioUserLogged(@Validated @RequestBody UpdateNegocioVo negocio, HttpServletRequest request){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Negocio enviado: "+negocio);
			TokenPayloadVo tokenInfo = Utilities.getInfoFromToken(request.getHeader("Authorization"));

			this.negocioService.insertarNegocioUserLoggued(negocio, tokenInfo);
			srResult.setResult("Negocio actualizado correctamente");

		} catch (DataAccessException e) {
			LOG.error("Ocurrio un error al crear el negocio:" +e.getLocalizedMessage());
			srResult.setError(e.getLocalizedMessage());
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NullPointerException e) {
			LOG.error("Ocurrio un error al crear el negocio:"+e.getLocalizedMessage());
			srResult.setError(e.getLocalizedMessage());
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}

	@PostMapping("/negocios/actualizarNegocio")
	public ResponseEntity<SimpleResponse> actualizarNegocio(@Validated @RequestBody UpdateNegocioVo negocio, HttpServletRequest request){
		SimpleResponse srResult = new SimpleResponse();

		try {
			TokenPayloadVo tokenInfo = Utilities.getInfoFromToken(request.getHeader("Authorization"));
			LOG.info("Negocio enviado: "+negocio);
			this.negocioService.actualizarNegocio(negocio, tokenInfo);
			srResult.setResult("Negocio actualizado correctamente");
		} catch (DataAccessException e) {
			LOG.error("Ocurrio un error al actualizar el negocio:" +e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NullPointerException e) {
			LOG.error("Ocurrio un error al actualizar el negocio:"+e.getLocalizedMessage());
			srResult.setError("Negocio no encontrado.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}

	@PostMapping("/negocios/actualizarImagenes")
	public ResponseEntity<SimpleResponse> actualizarImagenes(@RequestBody List<ImagenNegocioVo> imagenes){
		SimpleResponse srResult = new SimpleResponse();

		try {
			LOG.info("Imagenes enviadas: "+imagenes);

			this.imagenesNegocioService.actualizarImagenes(imagenes);
			srResult.setResult("Imágenes actualizadas correctamente");

		} catch (DataAccessException e) {
			LOG.error("Ocurrio un error al actualizar las imágenes:" +e.getLocalizedMessage());
			srResult.setError("Existe un problema accesando a la base.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NullPointerException e) {
			LOG.error("Ocurrio un error al actualizar la imagen:"+e.getLocalizedMessage());
			srResult.setError("Imagen no encontrada.");
			return new ResponseEntity<>(srResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		srResult.setMessage("OK");
		return new ResponseEntity<>(srResult, HttpStatus.OK);
	}

	@GetMapping("/negocios/listarNegocios")
	public ResponseEntity<SimpleResponse> listarNegocios(){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<NegociosInfoVo>(this.negocioService.getNegocios()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los negocios: "+ error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/negocios/listarNegocios/{negocioId}")
	public ResponseEntity<SimpleResponse> listarNegocioById(@PathVariable String negocioId){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(this.negocioService.getNegocioById(negocioId));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar el negocio: "+error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/negocios/listar/{subcategoria}")
	public ResponseEntity<SimpleResponse> listarNegociosBySubcategoria(@PathVariable String subcategoria){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<NegociosInfoVo>(
					this.negocioService.getNegociosBySubCategoria(subcategoria)
					));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los negocios: {}", error.getMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/negocios/listarNegociosTodos")
	public ResponseEntity<SimpleResponse> listarNegociosTodos(){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<NegociosInfoVo>(this.negocioService.getNegociosTodos()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los negocios:"+ error.getLocalizedMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/negocios/listarNegociosFavoritos")
	public ResponseEntity<SimpleResponse> listarNegociosFavoritos(){
		SimpleResponse response = new SimpleResponse();
		try {
			response.setResult(new ArrayList<NegociosInfoVo>(this.negocioService.getNegociosFavoritos()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception error) {
			LOG.error("Error al consultar los negocios favoritos:"+ error.getLocalizedMessage());
			response.setError(error.getMessage());
			return new ResponseEntity<SimpleResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
