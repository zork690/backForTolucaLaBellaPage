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
import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioComentarioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.NegocioComentario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaSubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ComentarioNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioComentarioVo;
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
	
	@Autowired
	private NegocioComentarioDao negocioComentarioDao;

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
		n.setIdNegocio(negocio.getIdNegocio());
		n.setIdUsuario(usuario);
		n.setCalle(negocio.getCalle());
		n.setSubCategoria(s);
		n.setDescripcion(negocio.getDescripcionComercial());
		n.setIdUbicacion(ubicacion);
		n.setNombreEmpresa(negocio.getNombreEmpresa());
		n.setNumeroExterior(negocio.getNumeroExterior());
		n.setTelefono(negocio.getTelefono());
		n.setValido(negocio.isValido());
		n.setId(Integer.valueOf(negocio.getId()));

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
	public List<NegociosInfoVo> getNegociosFavoritos() {
		List<NegociosInfoVo> listNegociosVo = new ArrayList<NegociosInfoVo>();
		List<Negocio> negociosFavoritos = this.negocioDao.findFavoritos();
		
		List<Imagen> imagenesList = this.imagenesNegocioService.getOnlyValidImages();
		
		negociosFavoritos.forEach((negocio)->{
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
			negociosVo.setLikes(negocio.getLikes());

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
	public List<NegociosInfoVo> getNegociosNuevos() {
		List<NegociosInfoVo> listNegociosVo = new ArrayList<NegociosInfoVo>();
		List<Negocio> negociosFavoritos = this.negocioDao.findNuevos();
		
		List<Imagen> imagenesList = this.imagenesNegocioService.getOnlyValidImages();
		
		negociosFavoritos.forEach((negocio)->{
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
			negociosVo.setLikes(negocio.getLikes());

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
	public List<NegociosInfoVo> getNegociosRelacionadosBySubCategoria(String subcategoria) {
		List<Negocio> negocios = this.negocioDao.getRelacionadosBySubcategoria(subcategoria);
		List<NegociosInfoVo> negociosVo = new ArrayList<NegociosInfoVo>();
		List<Imagen> imagenesList = this.imagenesNegocioService.getOnlyValidImages();

		negocios.forEach((negocio)->{
			NegociosInfoVo iNegocioVo = new NegociosInfoVo();
			List<ImagenNegocioVo> listImagenNegocio = new ArrayList<ImagenNegocioVo>();
			iNegocioVo.setNombrEmpresa(negocio.getNombreEmpresa());
			iNegocioVo.setValid(negocio.isValido());
			iNegocioVo.setIdNegocio(negocio.getIdNegocio());
			
			this.setSubcategoriaNegocio(negocio, iNegocioVo);

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
	public List<NegocioComentarioVo> getNegocioComentarios(String negocioId) {
		List<NegocioComentario> negocioComentariosList = this.negocioComentarioDao
				.findComentariosByIdNegocio(negocioId);
		List<NegocioComentarioVo> negocioComentarioVoList = new ArrayList<>();
		
		negocioComentariosList.forEach(negocioComentario->{
			NegocioComentarioVo nVo = new NegocioComentarioVo();
			nVo.setComentario(negocioComentario.getComentario());
			String email = negocioComentario.getIdUsuario().getEmail();
			int index = email.indexOf('@');
			nVo.setNickName(email.substring(0, index));
			
			negocioComentarioVoList.add(nVo);
		});
		
		return negocioComentarioVoList;
	}

	@Override
	public void insertarNegocioComentario(ComentarioNegocioVo comentarioNegocio, TokenPayloadVo tokenInfo)
			throws Exception {
		NegocioComentario negocioCom = new NegocioComentario();
		negocioCom.setComentario(comentarioNegocio.getComentario());
		Negocio negocio = new Negocio();
		negocio.setIdNegocio(comentarioNegocio.getIdNegocio());
		negocioCom.setIdNegocio(negocio);
		
		Usuario usuario = this.usuarioService.getByEmail(tokenInfo.getEmail());
		negocioCom.setIdUsuario(usuario);
		
		this.negocioComentarioDao.saveOrUpdate(negocioCom);
		
	}


	@Override
	public void setPassProveedor(SettingPassProveedorVo credenciales) throws ProveedorException {
		LOG.info("Setting Pass a Proveedor: ");

		//crear el método en los daos que busca al usuario por codigo
	}

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
