package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SubCategoria_Vo;

public interface SubCategoriaService {

	public List<SubCategoriaVo> getSubCategorias();
	public void insertarSubCategoria(SubCategoria_Vo subcategoriaVo);
	public List<SubCategoriaVo> getSubCategoriasByCategoriaName(String categoria);
}
