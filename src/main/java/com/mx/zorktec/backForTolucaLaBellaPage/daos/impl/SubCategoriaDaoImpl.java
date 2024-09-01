package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.SubCategoriaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;

@Repository
public class SubCategoriaDaoImpl extends GenericDaoImpl<SubCategoria> implements SubCategoriaDao{

	@Override
	protected Class<SubCategoria> getType() {
		return null;
	}

}
