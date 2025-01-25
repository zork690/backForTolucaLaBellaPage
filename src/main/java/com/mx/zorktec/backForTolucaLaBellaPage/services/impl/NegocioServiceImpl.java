package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.UbicacionesDao;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaSubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UpdateNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.UsuarioService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;

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

	@Autowired
	private UsuarioService usuarioService;

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
	@Transactional
	public void insertarNegocio(NegocioVo negocio) {
		String randomId = Utilities.generateIdForClient();

		this.usuarioService.insertarUsuario(this.usuarioService.generateUsuarioVo(negocio));
		Usuario usuario = this.usuarioService.getByEmail(negocio.getCorreo());

		if(usuario == null) {
			throw new NullPointerException("Usuario no encontrado por el email: "+negocio.getCorreo());
		}

		LOG.info("RANDOM STRING: "+randomId);
		Negocio p = new Negocio();
		p.setIdNegocio(randomId);
		p.setIdUsuario(usuario);
		p.setCalle(negocio.getCalle());
		SubCategoria s = new SubCategoria();
		s.setId( Integer.valueOf( negocio.getIdSubcategoria() ) );
		p.setSubCategoria( s );
		p.setDescripcion(negocio.getDescripcionComercial());

		Ubicacion u = this.ubicacionesDao
				.findById(Ubicacion.class, Integer.parseInt(negocio.getIdUbicacion())).orElse(null);
		p.setIdUbicacion(u);

		p.setNombreEmpresa(negocio.getNombreEmpresa());
		p.setNumeroExterior(negocio.getNumeroExterior());
		p.setTelefono(negocio.getTelefono());

		this.negocioDao.saveOrUpdate(p);
		this.imagenesNegocioService.processingImagefromNegocio(p, negocio.getImagenes(), randomId);
		//this.enviaEmailService.enviarEmail(proveedor.getCorreo());
	}

	@Override
	public void actualizarNegocio(UpdateNegocioVo negocio, TokenPayloadVo tokenInfo) throws NullPointerException {
		Usuario usuario = this.usuarioService.getByEmail(tokenInfo.getEmail());
		LOG.info("USUARIO ID: {}", usuario.getIdUsuario());
		Negocio n = new Negocio();
		SubCategoria s = new SubCategoria();
		s.setId(Integer.valueOf(negocio.getIdSubcategoria()));
		Ubicacion ubicacion = new Ubicacion();
		ubicacion.setId(Integer.valueOf(negocio.getIdUbicacion()));
		n.setIdNegocio(negocio.getId());
		n.setIdUsuario(usuario);
		n.setCalle(negocio.getCalle());
		n.setSubCategoria(s);
		n.setDescripcion(negocio.getDescripcionComercial());
		n.setIdUbicacion(ubicacion);
		n.setNombreEmpresa(negocio.getNombreEmpresa());
		n.setNumeroExterior(negocio.getNumeroExterior());
		n.setTelefono(negocio.getTelefono());
		n.setValido(negocio.isValido());

		this.negocioDao.saveOrUpdate(n);	

	}

	@Override
	public void insertarNegocioUserLoggued(UpdateNegocioVo negocio, TokenPayloadVo tokenInfo) {
		Usuario usuario = this.usuarioService.getByEmail(tokenInfo.getEmail());
		LOG.info("USUARIO ID: {}", usuario.getIdUsuario());
		String randomId = Utilities.generateIdForClient();
		Negocio bussiness = new Negocio();
		SubCategoria s = new SubCategoria();
		s.setId(Integer.valueOf(negocio.getIdSubcategoria()));
		Ubicacion ubicacion = new Ubicacion();
		ubicacion.setId(Integer.valueOf(negocio.getIdUbicacion()));
		bussiness.setIdNegocio(randomId);
		bussiness.setIdUsuario(usuario);
		bussiness.setCalle(negocio.getCalle());
		bussiness.setSubCategoria(s);
		bussiness.setDescripcion(negocio.getDescripcionComercial());
		bussiness.setIdUbicacion(ubicacion);
		bussiness.setNombreEmpresa(negocio.getNombreEmpresa());
		bussiness.setNumeroExterior(negocio.getNumeroExterior());
		bussiness.setTelefono(negocio.getTelefono());
		bussiness.setValido(negocio.isValido());

		this.negocioDao.saveOrUpdate(bussiness);
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
			negociosVo.setNombre(negocio.getIdUsuario().getNombre());
			negociosVo.setTelefono(negocio.getTelefono());
			negociosVo.setEmail(negocio.getIdUsuario().getEmail());
			negociosVo.setUbicacion(negocio.getIdUbicacion());
			negociosVo.setDescripcion(negocio.getDescripcion());
			negociosVo.setCalle(negocio.getCalle());

			this.setSubcategoriaNegocio(negocio, negociosVo);

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
			negociosVo.setNombre(negocio.getIdUsuario().getNombre());
			negociosVo.setTelefono(negocio.getTelefono());
			negociosVo.setEmail(negocio.getIdUsuario().getEmail());
			negociosVo.setUbicacion(negocio.getIdUbicacion());
			negociosVo.setDescripcion(negocio.getDescripcion());
			negociosVo.setCalle(negocio.getCalle());

			this.setSubcategoriaNegocio(negocio, negociosVo);

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
				negocioVo.setNombre(negocio.get().getIdUsuario().getNombre());
				negocioVo.setTelefono(negocio.get().getTelefono());
				negocioVo.setEmail(negocio.get().getIdUsuario().getEmail());
				negocioVo.setUbicacion(negocio.get().getIdUbicacion());
				negocioVo.setDescripcion(negocio.get().getDescripcion());
				negocioVo.setCalle(negocio.get().getCalle());

				this.setSubcategoriaNegocio(negocio.get(), negocioVo);

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
	public List<NegociosInfoVo> getNegociosBySubCategoria(String subcategoria) {
		List<Negocio> negocios = this.negocioDao.getBySubcategoria(subcategoria);
		List<NegociosInfoVo> negociosVo = new ArrayList<NegociosInfoVo>();
		List<Imagen> imagenesList = this.imagenesNegocioService.getOnlyValidImages();

		negocios.forEach((negocio)->{
			NegociosInfoVo iNegocioVo = new NegociosInfoVo();
			List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();
			iNegocioVo.setNombrEmpresa(negocio.getNombreEmpresa());
			iNegocioVo.setValid(negocio.isValido());
			iNegocioVo.setIdNegocio(negocio.getIdNegocio());

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

			iNegocioVo.setImagenes(listImagenNegocio);
			negociosVo.add(iNegocioVo);

		});

		return negociosVo;
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
	public List<NegociosInfoVo> getNegociosbyUser(TokenPayloadVo tokenInfo) {
		List<Negocio> negocios = this.negocioDao.getByUser(tokenInfo.getEmail());
		List<NegociosInfoVo> negociosVo = new ArrayList<NegociosInfoVo>();
		List<Imagen> imagenesList = this.imagenesNegocioService.getAllImages();

		negocios.forEach((negocio)->{
			NegociosInfoVo iNegocioVo = new NegociosInfoVo();
			List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();

			iNegocioVo.setId(negocio.getId());
			iNegocioVo.setIdNegocio(negocio.getIdNegocio());
			iNegocioVo.setNombre(negocio.getIdUsuario().getNombre());
			iNegocioVo.setTelefono(negocio.getTelefono());
			iNegocioVo.setEmail(negocio.getIdUsuario().getEmail());
			iNegocioVo.setUbicacion(negocio.getIdUbicacion());
			iNegocioVo.setDescripcion(negocio.getDescripcion());
			iNegocioVo.setCalle(negocio.getCalle());

			this.setSubcategoriaNegocio(negocio, iNegocioVo);
			iNegocioVo.setNombrEmpresa(negocio.getNombreEmpresa());
			iNegocioVo.setNumeroExterior(negocio.getNumeroExterior());

			iNegocioVo.setValid(negocio.isValido());			

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

			iNegocioVo.setImagenes(listImagenNegocio);
			negociosVo.add(iNegocioVo);
		});

		return negociosVo;
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

	/*private String setJWTToken(String username) {

		LOG.info("GENERATING JWT TOKEN...");

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
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

		return "Bearer " + token;
		return "";
	}*/

	private void setSubcategoriaNegocio(Negocio negocio, NegociosInfoVo negociosVo) {
		CategoriaSubCategoriaVo cSVo = new CategoriaSubCategoriaVo();
		cSVo.setId(negocio.getSubCategoria().getCategoria().getId());
		cSVo.setNombre(negocio.getSubCategoria().getCategoria().getCategoria());

		SubCategoriaVo sVo = new SubCategoriaVo();
		sVo.setId(negocio.getSubCategoria().getId());
		sVo.setCategoria(cSVo);
		sVo.setNombre(negocio.getSubCategoria().getSubcategoria());
		sVo.setValid(negocio.getSubCategoria().isValid());

		negociosVo.setSubcategoria(sVo);
	}

}
