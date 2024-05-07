package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
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

	@Override
	public Optional<List<Imagen>> getImagesById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Imagen");
		sb.append(" where id_negocio = ");
		sb.append(id);
		String sql = sb.toString();
		Query<Imagen> query = super.getSession().createQuery(sql, Imagen.class);
		return Optional.of(query.getResultList());
	}

}
