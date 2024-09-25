package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloReceivedVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloVo;

public interface ArticuloService {

	public List<ArticuloVo> getArticulos();
	public void insertarArticulo(ArticuloReceivedVo articulo);
}
