package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenService;

//@Service
public class ImagenServiceImpl implements ImagenService{
	
	private static final Logger LOG = LogManager.getLogger(ImagenServiceImpl.class);
	
	/*@Autowired
	ImagenDao imagenDao;
	
	@Autowired
	private RedisTemplate<String, List<Imagen>> imagenesTodas;
	
	@Autowired
	private RedisTemplate<String, List<ImagenVo>> imagenesTodasDisponibles;
	
	//private Gson gson = new Gson();

	@Override
	public void eliminarImagenes(List<ImagenVo> imagenes) {
		imagenes.forEach((imagen)->{
			Imagen i = this.imagenDao.findById(imagen.getNumImagen()).orElse(null);
			i.setEstatus(false);
			this.imagenDao.save(i);
		});
		
	}

	@Override
	public void insertarImagen(ImagenVo imagen) {
		Imagen i = new Imagen();
		i.setDescripcion(imagen.getDescripcion());
		i.setImagen(imagen.getImagen());
		i.setPrecio(0.0);
		i.setEstatus(true);
		i.setRegistro(new Timestamp(System.currentTimeMillis()));
		this.imagenDao.save(i);
		
		
	}

	@Override
	public List<ImagenVo> findAllImagenes() {
		final String key = "imagenesTodasDisponibles";
		boolean hasKey = this.imagenesTodasDisponibles.hasKey(key);
		final ValueOperations<String, List<ImagenVo>> operations = this.imagenesTodasDisponibles.opsForValue();
		if (hasKey) {
			final List<ImagenVo> post = operations.get(key);
			return post;
		}else {
			Optional <List<ImagenVo>> imagenes = Optional.ofNullable(new ArrayList<ImagenVo>());
			List<ImagenVo> imagenesB = new ArrayList<ImagenVo>();
			List<Imagen> imagenesList = 
					this.imagenDao.listAvailableImg();
			if (imagenesList != null) {
				imagenesList.forEach((imagen)->{
					imagenesB.add(new ImagenVo(imagen.getNumImagen(), 
							imagen.getImagen(), 
							imagen.getDescripcion(), 
							imagen.getPrecio(), 
							false));
				});
				imagenes = Optional.of(imagenesB);
				operations.set( key, imagenes.get(), 1, TimeUnit.MINUTES);
				return imagenes.get();
			}else {
				return null;
			}
		}		
		
	}

	@Override
	public void actualizarImagen(ImagenVo imagen) {
		LOG.info("IMAGEN A ACTUALIZAR: "+imagen);
		Imagen i = this.imagenDao.findById(imagen.getNumImagen()).orElse(null);
		i.setDescripcion(imagen.getDescripcion());
		i.setImagen(imagen.getImagen());
		this.imagenDao.save(i);
	}
	

	@Override
	public List<Imagen> encontrarTodas() {
		final String key = "imagenesTodas";
		boolean hasKey = imagenesTodas.hasKey(key);
		final ValueOperations<String, List<Imagen>> operations = imagenesTodas.opsForValue();
		if (hasKey) {
			final List<Imagen> post = operations.get(key);
			//LOG.info("encontrar Todas las imágenes : cache post >> " +  gson.toJson(post));
			return post;
					
		}else {
			final Optional<List<Imagen>> imagenesList = Optional
					.ofNullable(this.imagenDao.listarTodos());
			if (imagenesList.isPresent()) {
				operations.set( key, imagenesList.get(), 1, TimeUnit.MINUTES);
				//LOG.info("encontrar Todas las imágenes : cache insert >> " + gson.toJson(imagenesList.get()));
				return imagenesList.get();
			}else {
				
				return null;
			}
			
		}

		
	}*/
	
	

}
