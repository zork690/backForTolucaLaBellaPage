package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Perfil;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Proveedor;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;

@Repository
public class NegocioDaoImpl extends GenericDaoImpl<Negocio> implements NegocioDao
{

	private static final Logger LOG = LogManager.getLogger(NegocioDaoImpl.class);
	
	//@Override
	//public Proveedor findByEmail(CredencialesVo credenciales) {
		
		/*StringBuilder sb = new StringBuilder();
		sb.append(" from Proveedor");
		sb.append(" where correo = ");
		sb.append(credenciales.getEmail());
		String sql = sb.toString();
		Query<Proveedor> query = super.getSession().createQuery(sql, Proveedor.class);
		return query.uniqueResult(); */
		
		/*Proveedor p = new Proveedor();
		
		p.setCorreo(credenciales.getEmail());
		p.setNombre("Nombre de prueba");
		p.setCelular("0000000000");
		p.setValidated(true);
		return p; 
		
	}*/

	@Override
	protected Class<Negocio> getType() {
		return null;
	}

	/*@Override
	public Usuario validarProveedor(CredencialesVo usuario) {
		Usuario p = new Usuario();
		
		p.setCorreo(usuario.getEmail());
		p.setNombre("Nombre de prueba");
		p.setTelefono("0000000000");
		p.setValidated(true);
		p.setPass("dskfjfjgkgjgkjgfjgjkkj");
		Perfil perfil = new Perfil();
		perfil.setId(1L);
		p.setIdPerfil(perfil);
		return p;
		//return null;
	} */

}
