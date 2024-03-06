package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.UbicacionesDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;

@Repository
public class UbicacionDaoImpl extends GenericDaoImpl<Ubicacion> implements UbicacionesDao{

	@Override
	protected Class<Ubicacion> getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Ubicacion> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Ubicacion");
		String sql = sb.toString();
		return getSession().createQuery(sql, Ubicacion.class).getResultList();
	}

}
