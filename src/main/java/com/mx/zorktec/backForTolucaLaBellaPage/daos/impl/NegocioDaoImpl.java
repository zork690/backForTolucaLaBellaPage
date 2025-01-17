package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;

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

	@Override
	public long conteoNegocios() {
		StringBuilder sb = new StringBuilder();
		sb.append("select count (id) as negocios");
		sb.append(" from Negocio");
		sb.append(" WHERE valido = 1");
		String sql = sb.toString();
		Query<Long> query = super.getSession().createQuery(sql, Long.class);
		return query.uniqueResult();
	}

	@Override
	public Optional<List<Negocio>> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Negocio");
		//sb.append(" where isValid = 1");
		String sql = sb.toString();
		return Optional.of((super.getSession().createQuery(sql, Negocio.class).getResultList()));
	}

	@Override
	public Optional<List<Negocio>> findOnlyValids() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Negocio");
		sb.append(" where valido = 1");
		String sql = sb.toString();
		return Optional.of((super.getSession().createQuery(sql, Negocio.class).getResultList()));
	}
	
	@Override
	public List<Negocio> getBySubcategoria(String subcategoria) {
		String q = " FROM Negocio n WHERE n.subCategoria.subcategoria LIKE :subCatego";
		TypedQuery<Negocio> query = super.getSession().createQuery(q, Negocio.class);
		List<Negocio> negocios = query
		.setParameter("subCatego", subcategoria)
		.getResultList();
		return negocios;
	}

	@Override
	public List<Negocio> getByUser(String mail) {
		String q = "SELECT n FROM Negocio n JOIN n.idUsuario u WHERE u.email LIKE :mail";
		TypedQuery<Negocio> query = super.getSession().createQuery(q, Negocio.class);
		List<Negocio> negocios = query
				.setParameter("mail", mail)
				.getResultList();
		LOG.info("NEGOCIOS LIST: {}", negocios.get(0).getIdNegocio());
		return negocios;
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
