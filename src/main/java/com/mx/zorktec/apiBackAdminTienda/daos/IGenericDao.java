package com.mx.zorktec.apiBackAdminTienda.daos;

import com.mx.zorktec.apiBackAdminTienda.entities.IGenericEntity;

public interface IGenericDao<T extends IGenericEntity>{

	public void saveOrUpdate(T objeto);
	public T findById(Long id);
}
