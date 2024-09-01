package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;

public interface CategoriaDao extends IGenericDao<Categoria>{
	
	public List<Categoria> getCategorias();

}
