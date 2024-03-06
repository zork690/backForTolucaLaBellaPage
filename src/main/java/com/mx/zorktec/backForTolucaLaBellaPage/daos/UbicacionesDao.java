package com.mx.zorktec.backForTolucaLaBellaPage.daos;


import java.util.List;
import java.util.Optional;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;

public interface UbicacionesDao extends IGenericDao<Ubicacion>{

	public List<Ubicacion> findAll();
	//public Optional<Ubicacion> findById(Integer id);
}
