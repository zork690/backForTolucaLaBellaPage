package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Clientes;

@Repository
public interface ClientesDao 
//extends IGenericDao<Clientes>
extends JpaRepository<Clientes, Integer>
{

}
