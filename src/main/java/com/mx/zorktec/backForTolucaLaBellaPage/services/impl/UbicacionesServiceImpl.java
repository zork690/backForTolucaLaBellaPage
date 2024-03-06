package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.UbicacionesDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;
import com.mx.zorktec.backForTolucaLaBellaPage.services.UbicacionesService;

@Service
public class UbicacionesServiceImpl implements UbicacionesService{

	@Autowired
	private UbicacionesDao ubicacionesDao;
	
	@Override
	public List<Ubicacion> getUbicaciones() {
		return this.ubicacionesDao.findAll();
	}

}
