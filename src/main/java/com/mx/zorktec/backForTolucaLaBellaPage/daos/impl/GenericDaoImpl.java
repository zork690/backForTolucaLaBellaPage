package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.IGenericDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.IGenericEntity;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;

@Repository
public abstract class GenericDaoImpl<T extends IGenericEntity> implements IGenericDao<T>{

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	protected abstract Class<T> getType();
	
	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	
	@Override
	@Transactional
	public void saveOrUpdate(T pElement) {
		if (pElement == null) {
			throw new IllegalArgumentException("No es posible guardar un elemento nulo");
		}
		getSession().saveOrUpdate(pElement);
		getSession().flush();
	}
	
	
	@Override
	public Optional<T> findById(Class<T>clazz, Integer id) {
		//Class<Ubicacion> type = getType();
		Optional<T> result =  Optional.of(getSession().get(clazz, id));		
		return result;
	}
}
