package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;

//@Repository
public interface ImagenDao extends IGenericDao<Imagen>
{

	/*@Query("SELECT im FROM Imagen im WHERE im.estatus = true")
	public List<Imagen> listAvailableImg();
	
	default public List<Imagen> listarTodos(){
		System.out.println("ESTA PIDIENDO TODOS DE LA BASE DE DATOS");
		return this.findAll();
	}*/
	
	//@Query("SELECT im FROM IMAGEN im WHERE im.idNegocio.idNegocio = id")
	public Optional<List<Imagen>> getImagesById(String id);
}
