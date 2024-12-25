package com.mx.zorktec.backForTolucaLaBellaPage.daos;


import java.util.List;
import java.util.Optional;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;

public interface NegocioDao extends IGenericDao<Negocio>{

	//public Proveedor findByEmail(CredencialesVo credenciales);
	//public Usuario validarProveedor(CredencialesVo usuario);
	long conteoNegocios();
	Optional<List<Negocio>> findAll();
	Optional<List<Negocio>> findOnlyValids();
	List<Negocio> getBySubcategoria(String subcategoria);
	List<Negocio> getByUser(String email);
}
