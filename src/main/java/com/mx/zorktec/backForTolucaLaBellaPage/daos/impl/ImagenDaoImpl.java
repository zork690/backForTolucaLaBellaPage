package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;

@Repository
public class ImagenDaoImpl extends GenericDaoImpl<Imagen> implements ImagenDao {

	private static final Logger LOG = LogManager.getLogger(ImagenDaoImpl.class);
	
	@Override
	protected Class<Imagen> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
