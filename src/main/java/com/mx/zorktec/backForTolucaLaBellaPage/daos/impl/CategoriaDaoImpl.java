package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.CategoriaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;

@Repository
public class CategoriaDaoImpl extends GenericDaoImpl<Categoria> implements CategoriaDao{

	@Override
	public List<Categoria> getCategorias() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Categoria");
		//sb.append(" where isValid = 1");
		String sql = sb.toString();
		return super.getSession().createQuery(sql, Categoria.class).getResultList();
	}

	@Override
	protected Class<Categoria> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
