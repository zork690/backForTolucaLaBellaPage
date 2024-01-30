package com.mx.zorktec.apiBackAdminTienda.services;

import java.util.List;

import com.mx.zorktec.apiBackAdminTienda.entities.Imagen;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.ImagenVo;

public interface ImagenService {

	void insertarImagen(ImagenVo imagen);
	void eliminarImagenes(List<ImagenVo> imagenes);
	List<ImagenVo> findAllImagenes();
	void actualizarImagen(ImagenVo imagen);
	List<Imagen> encontrarTodas();
}
