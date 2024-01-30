package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;

//@Repository
public interface ImagenDao 
//extends JpaRepository<Imagen, Integer> 
{

	/*@Query("SELECT im FROM Imagen im WHERE im.estatus = true")
	public List<Imagen> listAvailableImg();
	
	default public List<Imagen> listarTodos(){
		System.out.println("ESTA PIDIENDO TODOS DE LA BASE DE DATOS");
		return this.findAll();
	}*/
}
