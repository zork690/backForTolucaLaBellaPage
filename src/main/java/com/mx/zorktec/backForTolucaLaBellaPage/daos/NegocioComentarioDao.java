package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.NegocioComentario;

public interface NegocioComentarioDao extends IGenericDao<NegocioComentario> {
	
	List<NegocioComentario> findComentariosByIdNegocio(String idNegocio);

}
