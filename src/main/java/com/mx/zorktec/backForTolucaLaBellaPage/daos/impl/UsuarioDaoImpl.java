package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.UsuarioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;

@Repository
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario> implements UsuarioDao{
	
	private static final Logger LOG = LogManager.getLogger(UsuarioDaoImpl.class);

	@Override
	public Optional<List<Usuario>> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Usuario");
		//sb.append(" where isValid = 1");
		String sql = sb.toString();
		return Optional.of((super.getSession().createQuery(sql, Usuario.class).getResultList()));
	}

	@Override
	public Optional<List<Usuario>> findOnlyValids() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Usuario");
		sb.append(" where valido = 1");
		String sql = sb.toString();
		return Optional.of((super.getSession().createQuery(sql, Usuario.class).getResultList()));
	}
	
	@Override
	public Optional<Usuario> findByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Usuario");
		sb.append(" where email = ");
		sb.append(":email");
		String sql = sb.toString();
		TypedQuery<Usuario> query = super.getSession().createQuery(sql, Usuario.class);
		query.setParameter("email", email);
		return Optional.of(query.getSingleResult());
	}
	
	@Override
	protected Class<Usuario> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
