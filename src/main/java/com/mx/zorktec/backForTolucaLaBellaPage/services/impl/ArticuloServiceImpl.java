package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ArticuloDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Articulo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.ImagenArticulo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloReceivedVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ArticuloService;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesArticuloService;

@Service
public class ArticuloServiceImpl implements ArticuloService{
	
	@Autowired
	private ArticuloDao articuloDao;
	
	@Autowired
	private ImagenesArticuloService imagenesArticuloService;

	@Override
	public List<ArticuloVo> getArticulos() {
		List<ArticuloVo> listArticulosVo = new ArrayList<ArticuloVo>();

		List<Articulo> articulos = this.articuloDao.findAll()
				.orElse(null);
		List<ImagenArticulo> imagenesList = this.imagenesArticuloService.getAllImages();
		
		articulos.forEach((articulo)->{
			ArticuloVo articuloVo = new ArticuloVo();
			articuloVo.setId(articulo.getId());
			articuloVo.setNombre(articulo.getNombre());
			articuloVo.setDescripcion(articulo.getDescripcion());
			List<ImagenArticuloVo> listImagenArticuloVo = new ArrayList<ImagenArticuloVo>();
			
			imagenesList.forEach((imagen)->{
				ImagenArticuloVo imagenArticuloVo = new ImagenArticuloVo();
				if( articulo.getId() == imagen.getArticulo().getId() ) {
					imagenArticuloVo.setId(imagen.getId());
					imagenArticuloVo.setIdArticulo(imagen.getArticulo().getId());
					imagenArticuloVo.setNombre(imagen.getNombre());
					imagenArticuloVo.setValid(imagen.isValid());
					listImagenArticuloVo.add(imagenArticuloVo);
				}
			});

			articuloVo.setImagenes(listImagenArticuloVo);
			articuloVo.setValid(articulo.isValido());
			listArticulosVo.add(articuloVo);
		});
		return listArticulosVo;
	}

	@Override
	public void insertarArticulo(ArticuloReceivedVo articulo) {
		Articulo a = new Articulo();
		a.setId(articulo.getId());
		a.setNombre(articulo.getNombre());
		a.setDescripcion(articulo.getDescripcion());
		a.setValido(articulo.isValid());
		this.articuloDao.saveOrUpdate(a);
	}

}
