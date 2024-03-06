package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.IGenericEntity;

public interface IGenericDao<T extends IGenericEntity> 
//extends JpaRepository<IGenericEntity, Integer>
{

	public void saveOrUpdate(T objeto);
	public Optional<T> findById(Class<T>clazz, Integer id);
}
