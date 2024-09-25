package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;
import java.util.Optional;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Articulo;

public interface ArticuloDao extends IGenericDao<Articulo>{
	
	Optional<List<Articulo>> findAll();

}
