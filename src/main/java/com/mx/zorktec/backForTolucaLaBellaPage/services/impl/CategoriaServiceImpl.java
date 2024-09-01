package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.CategoriaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Categoria;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CategoriaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	public List<Categoria> getCategorias() {
		return this.categoriaDao.getCategorias();
		
	}

	@Override
	public void insertarCategoria(CategoriaVo categoria) {
		Categoria c = new Categoria();
		c.setId(categoria.getId());
		c.setCategoria(categoria.getCategoria());
		c.setImagen(categoria.getImagen());
		c.setValid(categoria.isValid());
		this.categoriaDao.saveOrUpdate(c);
	}

}
