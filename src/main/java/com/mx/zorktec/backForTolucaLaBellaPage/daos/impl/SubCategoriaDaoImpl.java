package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.SubCategoriaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;

@Repository
public class SubCategoriaDaoImpl extends GenericDaoImpl<SubCategoria> implements SubCategoriaDao{

	@Override
	protected Class<SubCategoria> getType() {
		return null;
	}

	@Override
	public List<SubCategoria> getByCategoriaName(String categoria) {
		String q = " FROM SubCategoria s WHERE s.categoria.categoria LIKE :catego";
		TypedQuery<SubCategoria> query = super.getSession().createQuery(q, SubCategoria.class);
		List<SubCategoria> subcategorias = query
		.setParameter("catego", categoria)
		.getResultList();
		return subcategorias;
	}

}
