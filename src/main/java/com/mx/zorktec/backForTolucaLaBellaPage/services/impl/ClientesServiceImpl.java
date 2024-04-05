package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NegocioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Clientes;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ClientesService;

@Service
public class ClientesServiceImpl implements ClientesService{
	
	private static final Logger LOG = LogManager.getLogger(ClientesServiceImpl.class);
	
	@Autowired
	private NegocioDao negocioDao;
	
	@Override
	public Clientes getConteoClientes() 
	{
		Clientes c = new Clientes();
		c.setConteo(Long.valueOf(this.negocioDao.conteoNegocios()).intValue());
		LOG.info("Conteo de negocios van: {}",c.getConteo());
		return c;
		
	}

}
