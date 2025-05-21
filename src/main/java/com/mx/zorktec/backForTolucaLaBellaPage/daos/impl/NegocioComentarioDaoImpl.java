package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioComentarioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.NegocioComentario;

@Repository
public class NegocioComentarioDaoImpl extends GenericDaoImpl<NegocioComentario> implements NegocioComentarioDao {

	@Override
	protected Class<NegocioComentario> getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NegocioComentario> findComentariosByIdNegocio(String idNegocio) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM NegocioComentario");
		sb.append(" where id_negocio = ");
		sb.append(idNegocio);
		String sql = sb.toString();
		return super.getSession().createQuery(sql, NegocioComentario.class).getResultList();
	}

}
