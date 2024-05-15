package com.mx.zorktec.backForTolucaLaBellaPage.daos;


import java.util.List;
import java.util.Optional;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Proveedor;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;

public interface NegocioDao extends IGenericDao<Negocio>{

	//public Proveedor findByEmail(CredencialesVo credenciales);
	//public Usuario validarProveedor(CredencialesVo usuario);
	long conteoNegocios();
	Optional<List<Negocio>> findAll();
}
