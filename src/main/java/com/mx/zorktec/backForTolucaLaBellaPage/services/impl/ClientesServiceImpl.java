package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ClientesDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Clientes;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ClientesService;

@Service
public class ClientesServiceImpl implements ClientesService{
	
	private static final Logger LOG = LogManager.getLogger(ClientesServiceImpl.class);

	@Autowired
	private ClientesDao clientesDao;
	
	@Override
	public Clientes getConteoClientes() 
	{
		Clientes c = clientesDao.findById(1).orElse(null);
		
		LOG.info("Conteo de clientes es: "+c.getConteo());
		
		return c;
		
	}

}
