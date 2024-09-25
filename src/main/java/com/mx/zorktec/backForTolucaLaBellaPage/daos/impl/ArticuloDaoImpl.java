package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ArticuloDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Articulo;

@Repository
public class ArticuloDaoImpl extends GenericDaoImpl<Articulo> implements ArticuloDao{

	@Override
	public Optional<List<Articulo>> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Articulo");
		//sb.append(" where isValid = 1");
		String sql = sb.toString();
		return Optional.of((super.getSession().createQuery(sql, Articulo.class).getResultList()));
	}

	@Override
	protected Class<Articulo> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
