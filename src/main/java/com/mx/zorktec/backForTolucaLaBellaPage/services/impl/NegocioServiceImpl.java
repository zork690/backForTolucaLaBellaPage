package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.PermisosPerfilesDAO;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.UbicacionesDao;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.PermisosPerfil;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Proveedor;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.PermisosVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UpdateNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UsuarioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;
import com.mx.zorktec.backForTolucaLaBellaPage.services.EnviaEmailService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class NegocioServiceImpl implements NegocioService{
	
	private static final Logger LOG = LogManager.getLogger(NegocioServiceImpl.class);

	@Autowired
	private NegocioDao negocioDao;
	
	@Autowired
	private UbicacionesDao ubicacionesDao;
	
	@Autowired
	private ImagenesNegocioService imagenesNegocioService;
	
	@Autowired
	private ImagenDao imagenesDao;
	
	//@Autowired
	//private PermisosPerfilesDAO permisosDao;
	
	//@Autowired
	//private EnviaEmailService enviaEmailService;
	
	@Value("${secure.secretKey}")
	private String secretKey;
	
	@Value("${secure.setJTI}")
	private String setJTI;
	
	@Value("${secure.timeTokenMs}")
	private int timeTokenMs;
	
	@Override
	public void insertarNegocio(NegocioVo negocio) {
		String randomId = Utilities.generateIdForClient();
		LOG.info("RANDOM STRING: "+randomId);
		Negocio p = new Negocio();
		p.setIdNegocio(randomId);
		p.setCalle(negocio.getCalle());
		p.setCategoria(negocio.getCategoria());
		p.setEmail(negocio.getCorreo());
		p.setDescripcion(negocio.getDescripcionComercial());
		
		Ubicacion u = this.ubicacionesDao
				.findById(Ubicacion.class, Integer.parseInt(negocio.getIdUbicacion())).orElse(null);
		p.setIdUbicacion(u);
		
		p.setNombre(negocio.getNombre());
		p.setNombreEmpresa(negocio.getNombreEmpresa());
		p.setNumeroExterior(negocio.getNumeroExterior());
		p.setTelefono(negocio.getTelefono());
		
		this.negocioDao.saveOrUpdate(p);
		this.imagenesNegocioService.processingImagefromNegocio(p, negocio.getImagenes(), randomId);
		//this.enviaEmailService.enviarEmail(proveedor.getCorreo());
	}
	
	@Override
	public void actualizarNegocio(UpdateNegocioVo negocio) throws NullPointerException {
		Optional<Negocio> p = this.negocioDao.findById(Negocio.class, negocio.getId());
		
		if(p == null) {
			throw new NullPointerException("negocio no encontrado");
		}
			
		
		if(p.isPresent()) {
			Negocio n = p.get();
		n.setCalle(negocio.getCalle());
		n.setEmail(negocio.getCorreo());
		n.setDescripcion(negocio.getDescripcionComercial());
		
		Ubicacion u = this.ubicacionesDao
				.findById(Ubicacion.class, Integer.parseInt(negocio.getIdUbicacion())).orElse(null);
		n.setIdUbicacion(u);
		
		n.setNombre(negocio.getNombre());
		n.setNombreEmpresa(negocio.getNombreEmpresa());
		n.setNumeroExterior(negocio.getNumeroExterior());
		n.setTelefono(negocio.getTelefono());
		n.setValido(negocio.isValido());
		
		this.negocioDao.saveOrUpdate(n);
		}
		
	}
	
	@Override
	public List<NegociosInfoVo> getNegocios() {
		List<NegociosInfoVo> listNegociosVo = new ArrayList<NegociosInfoVo>();
		
		List<Negocio> negocios = this.negocioDao.findOnlyValids()
				.orElse(null);
		List<Imagen> imagenesList = this.imagenesNegocioService.getOnlyValidImages();
		
		negocios.forEach((negocio)->{
			NegociosInfoVo negociosVo = new NegociosInfoVo();
			List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();
			
			negociosVo.setId(negocio.getId());
			negociosVo.setIdNegocio(negocio.getIdNegocio());
			negociosVo.setNombre(negocio.getNombre());
			negociosVo.setTelefono(negocio.getTelefono());
			negociosVo.setEmail(negocio.getEmail());
			negociosVo.setUbicacion(negocio.getIdUbicacion());
			negociosVo.setDescripcion(negocio.getDescripcion());
			negociosVo.setCalle(negocio.getCalle());
			negociosVo.setCategoria(negocio.getCategoria());
			negociosVo.setNombrEmpresa(negocio.getNombreEmpresa());
			negociosVo.setNumeroExterior(negocio.getNumeroExterior());
			negociosVo.setValid(negocio.isValido());
			
			imagenesList.forEach((imagen)->{
				ImagenNegocioVo imagenNegocioVo = new ImagenNegocioVo();
				if(negocio.getIdNegocio().equals(imagen.getIdNegocio().getIdNegocio())) {
					imagenNegocioVo.setId(imagen.getNumImagen());
					imagenNegocioVo.setIdNegocio(negocio.getIdNegocio());
					imagenNegocioVo.setNombre(imagen.getNombre());
					imagenNegocioVo.setValid(imagen.isValid());
					listImagenNegocio.add(imagenNegocioVo);
				}
			});
			
			negociosVo.setImagenes(listImagenNegocio);
			
			listNegociosVo.add(negociosVo);
		});
		return listNegociosVo;
	}
	
	@Override
	public List<NegociosInfoVo> getNegociosTodos() {
		List<NegociosInfoVo> listNegociosVo = new ArrayList<NegociosInfoVo>();

		List<Negocio> negocios = this.negocioDao.findAll()
				.orElse(null);
		List<Imagen> imagenesList = this.imagenesNegocioService.getAllImages();

		negocios.forEach((negocio)->{
			NegociosInfoVo negociosVo = new NegociosInfoVo();
			List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();

			negociosVo.setId(negocio.getId());
			negociosVo.setIdNegocio(negocio.getIdNegocio());
			negociosVo.setNombre(negocio.getNombre());
			negociosVo.setTelefono(negocio.getTelefono());
			negociosVo.setEmail(negocio.getEmail());
			negociosVo.setUbicacion(negocio.getIdUbicacion());
			negociosVo.setDescripcion(negocio.getDescripcion());
			negociosVo.setCalle(negocio.getCalle());
			negociosVo.setCategoria(negocio.getCategoria());
			negociosVo.setNombrEmpresa(negocio.getNombreEmpresa());
			negociosVo.setNumeroExterior(negocio.getNumeroExterior());
			negociosVo.setValid(negocio.isValido());

			imagenesList.forEach((imagen)->{
				ImagenNegocioVo imagenNegocioVo = new ImagenNegocioVo();
				if(negocio.getIdNegocio().equals(imagen.getIdNegocio().getIdNegocio())) {
					imagenNegocioVo.setId(imagen.getNumImagen());
					imagenNegocioVo.setIdNegocio(negocio.getIdNegocio());
					imagenNegocioVo.setNombre(imagen.getNombre());
					imagenNegocioVo.setValid(imagen.isValid());
					listImagenNegocio.add(imagenNegocioVo);
				}
			});

			negociosVo.setImagenes(listImagenNegocio);

			listNegociosVo.add(negociosVo);
		});
		return listNegociosVo;
	}
	
	@Override
	public NegociosInfoVo getNegocioById(String id) throws Exception {
		Optional<Negocio> negocio = this.negocioDao.findById(Negocio.class, id);
		Optional<List<Imagen>> imagenes =  this.imagenesDao.getImagesByIdOnlyValids(id);
		if(negocio.isPresent()) {
			if(imagenes.isPresent()) {
				NegociosInfoVo negocioVo = new NegociosInfoVo();
				List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();
				imagenes.get().forEach((imagen)->{
					ImagenNegocioVo imagenesVo = new ImagenNegocioVo();
					imagenesVo.setId(imagen.getNumImagen());
					imagenesVo.setIdNegocio(imagen.getIdNegocio().getIdNegocio());
					imagenesVo.setNombre(imagen.getNombre());
					listImagenNegocio.add(imagenesVo);
				});
				negocioVo.setId(negocio.get().getId());
				negocioVo.setIdNegocio(negocio.get().getIdNegocio());
				negocioVo.setNombre(negocio.get().getNombre());
				negocioVo.setTelefono(negocio.get().getTelefono());
				negocioVo.setEmail(negocio.get().getEmail());
				negocioVo.setUbicacion(negocio.get().getIdUbicacion());
				negocioVo.setDescripcion(negocio.get().getDescripcion());
				negocioVo.setCalle(negocio.get().getCalle());
				negocioVo.setCategoria(negocio.get().getCategoria());
				negocioVo.setNombrEmpresa(negocio.get().getNombreEmpresa());
				negocioVo.setNumeroExterior(negocio.get().getNumeroExterior());
				negocioVo.setImagenes(listImagenNegocio);
				negocioVo.setValid(negocio.get().isValido());
				LOG.info("El negocio consultado es: {}", negocioVo);
				return negocioVo;
			}else {
				throw new Exception("Null images");
			}
		}else {
			throw new Exception("Null exception");
		}
		
	}

	@Override
	public void setPassProveedor(CredencialesVo credenciales) throws ProveedorException {
		
		//BUSCAR EMAIL EN LA BASE Y REVISAR QUE YA ESTE VERIFICADO
		/*Proveedor p = this.proveedorDao.findByEmail(credenciales);
		
		//SI PROVEEDOR ES NULO O NO ESTA VERIFICADO LANZAR ProveedorException("Proveedor no encontrado o no verificado")
		if(p == null) {
			throw new ProveedorException("Proveedor no encontrado o no verificado");
		}else {
			
			if(!p.isValidated()) {
				throw new ProveedorException("Proveedor no encontrado o no verificado");
			}
			
			LOG.info("Agregando pass a proveedor: "+p.getCorreo());
			//this.proveedorDao.saveOrUpdate(p);
		}*/
		
		
	}
	

	@Override
	public void setPassProveedor(SettingPassProveedorVo credenciales) throws ProveedorException {
		LOG.info("Setting Pass a Proveedor: ");
		
		//crear el método en los daos que busca al usuario por codigo
	}

	/*@Override
	public LoginVo validarProveedor(CredencialesVo credenciales) throws NoSuchFieldException
	, IllegalAccessException {
		Usuario usuarioValidar = proveedorDao.validarProveedor(credenciales);
		UsuarioVo usuarioVo = new UsuarioVo();
		LoginVo loginVo = new LoginVo();
		
		if (usuarioValidar != null) {
			String s = new String(usuarioValidar.getPass());
			byte[] decoded = Base64.getDecoder().decode(s.trim());
			String contrasenaBD = new String(decoded);
			String contraEnviada = credenciales.getPass().trim();
			
			LOG.info(contraEnviada);
			
			if (!contrasenaBD.equals(contraEnviada)) {
				usuarioVo.setIdUsuario(usuarioValidar.getId());
				usuarioVo.setIdPerfil(usuarioValidar.getIdPerfil().getId());
				usuarioVo.setNombreUsuario(usuarioValidar.getNombre());
				usuarioVo.setContrasena(s.trim());
				
				PermisosVo permisosVo = new PermisosVo();
				List<PermisosPerfil> permisos = permisosDao
						.obtienePermisosByPerfil(usuarioValidar.getIdPerfil().getId());
				if(!permisos.isEmpty()) {
					for (PermisosPerfil permisosPerfiles : permisos) {
						permisosVo.setPermisoValue(permisosPerfiles.getIdPermiso().getNombre()
								, permisosPerfiles.getBandera());
					}
				}
				
				loginVo.setEstatus(true);
				loginVo.setUsuario(usuarioVo);
				loginVo.setPermisos(permisosVo);
				loginVo.setMsgDesripcion("Inicio de sesión correcto");
				loginVo.setToken(this.setJWTToken(usuarioVo.getNombreUsuario()));
				
			}else {
				loginVo.setEstatus(false);
				loginVo.setUsuario(null);
				loginVo.setPermisos(null);
				loginVo.setMsgDesripcion("La contraseña es incorrecta");
			}
		} else {
			loginVo.setEstatus(false);
			loginVo.setUsuario(null);
			loginVo.setPermisos(null);
			loginVo.setMsgDesripcion("El usuario no existe");
		}
		
		return loginVo;
	}*/
	
	private String setJWTToken(String username) {
		
		LOG.info("GENERATING JWT TOKEN...");
		
		/*List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		@SuppressWarnings("deprecation")
		String token = Jwts
				.builder()
				.setId(this.setJTI)
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.timeTokenMs))
				.signWith(SignatureAlgorithm.HS512,
						this.secretKey.getBytes()).compact();

		return "Bearer " + token;*/
		return "";
	}
}
