package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

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

}
