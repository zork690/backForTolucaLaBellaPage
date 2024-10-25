package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Noticia;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NoticiaVo;

public interface NoticiaService {

	public List<Noticia> getNoticias();
	public void insertarNoticia(NoticiaVo noticia);
	public Noticia getById(int id);
}
