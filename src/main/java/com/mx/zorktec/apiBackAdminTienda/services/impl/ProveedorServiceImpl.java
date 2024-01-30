package com.mx.zorktec.apiBackAdminTienda.services.impl;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.mx.zorktec.apiBackAdminTienda.daos.PermisosPerfilesDAO;
import com.mx.zorktec.apiBackAdminTienda.daos.ProveedorDao;
import com.mx.zorktec.apiBackAdminTienda.entities.PermisosPerfil;
import com.mx.zorktec.apiBackAdminTienda.entities.Proveedor;
import com.mx.zorktec.apiBackAdminTienda.entities.Usuario;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.CredencialesVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.LoginVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.PermisosVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.ProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.UsuarioVo;
import com.mx.zorktec.apiBackAdminTienda.exceptions.ProveedorException;
import com.mx.zorktec.apiBackAdminTienda.services.EnviaEmailService;
import com.mx.zorktec.apiBackAdminTienda.services.ProveedorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ProveedorServiceImpl implements ProveedorService{
	
	private static final Logger LOG = LogManager.getLogger(ProveedorServiceImpl.class);

	@Autowired
	private ProveedorDao proveedorDao;
	
	@Autowired
	private PermisosPerfilesDAO permisosDao;
	
	@Autowired
	private EnviaEmailService enviaEmailService;
	
	@Value("${secure.secretKey}")
	private String secretKey;
	
	@Value("${secure.setJTI}")
	private String setJTI;
	
	@Value("${secure.timeTokenMs}")
	private int timeTokenMs;
	
	@Override
	public void insertarProveedor(ProveedorVo proveedor) {
		Proveedor p = new Proveedor();
		p.setCorreo(proveedor.getCorreo());
		p.setCelular(proveedor.getTelefono());
		p.setNombre(proveedor.getNombre());
		
		
		
		//this.proveedorDao.saveOrUpdate(p);
		this.enviaEmailService.enviarEmail(proveedor.getCorreo());
	}

	@Override
	public void setPassProveedor(CredencialesVo credenciales) throws ProveedorException {
		
		//BUSCAR EMAIL EN LA BASE Y REVISAR QUE YA ESTE VERIFICADO
		Proveedor p = this.proveedorDao.findByEmail(credenciales);
		
		//SI PROVEEDOR ES NULO O NO ESTA VERIFICADO LANZAR ProveedorException("Proveedor no encontrado o no verificado")
		if(p == null) {
			throw new ProveedorException("Proveedor no encontrado o no verificado");
		}else {
			
			if(!p.isValidated()) {
				throw new ProveedorException("Proveedor no encontrado o no verificado");
			}
			
			LOG.info("Agregando pass a proveedor: "+p.getCorreo());
			//this.proveedorDao.saveOrUpdate(p);
		}
		
		
	}
	

	@Override
	public void setPassProveedor(SettingPassProveedorVo credenciales) throws ProveedorException {
		LOG.info("Setting Pass a Proveedor: ");
		
		//crear el método en los daos que busca al usuario por codigo
	}

	@Override
	public LoginVo validarProveedor(CredencialesVo credenciales) throws NoSuchFieldException, IllegalAccessException {
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
	}
	
	private String setJWTToken(String username) {
		
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
	}

}
