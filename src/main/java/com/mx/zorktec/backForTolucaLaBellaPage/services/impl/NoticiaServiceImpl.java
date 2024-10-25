package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.NoticiaDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Noticia;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NoticiaVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.NoticiaService;

@Service
public class NoticiaServiceImpl implements NoticiaService {
	
	@Autowired
	private NoticiaDao noticiaDao;

	@Override
	public List<Noticia> getNoticias() {
		return this.noticiaDao.findAll("Noticia", Noticia.class).orElse(null);
	}

	@Override
	public void insertarNoticia(NoticiaVo noticia) {
		Noticia n = new Noticia();
		n.setId(noticia.getId());
		n.setNoticia(noticia.getNoticia());
		n.setImagen(noticia.getImagen());
		n.setValid(noticia.isValid());
		this.noticiaDao.saveOrUpdate(n);
	}

	@Override
	public Noticia getById(int id) {
		return this.noticiaDao.findById(Noticia.class, id).orElse(null);
	}

}
