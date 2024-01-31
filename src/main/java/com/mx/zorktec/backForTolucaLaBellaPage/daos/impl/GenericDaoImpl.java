package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.IGenericDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.IGenericEntity;

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
	public void saveOrUpdate(T pElement) {
		if (pElement == null) {
			throw new IllegalArgumentException("No es posible guardar un elemento nulo");
		}
		getSession().saveOrUpdate(pElement);
		getSession().flush();
	}


	@Override
	public T findById(Long id) {
		Class<T> type = getType();
		T result = getSession().get(type, id);		
		return result;
	}
	
	
}
