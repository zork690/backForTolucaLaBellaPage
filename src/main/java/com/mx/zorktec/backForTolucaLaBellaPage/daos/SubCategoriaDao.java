package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.SubCategoria;

public interface SubCategoriaDao extends IGenericDao<SubCategoria>{

	public List<SubCategoria> getByCategoriaName(String categoria);
}
