package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaVo;

public interface CategoriaService {

	public List<Categoria> getCategorias();
	public void insertarCategoria(CategoriaVo categoria);
}
