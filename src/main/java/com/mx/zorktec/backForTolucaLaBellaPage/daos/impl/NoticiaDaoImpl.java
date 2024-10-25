package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NoticiaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Noticia;

@Repository
public class NoticiaDaoImpl extends GenericDaoImpl<Noticia> implements NoticiaDao {

	@Override
	protected Class<Noticia> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
