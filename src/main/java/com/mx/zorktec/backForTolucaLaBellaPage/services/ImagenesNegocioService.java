package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;

public interface ImagenesNegocioService {
	
	public void processingImagefromNegocio(Negocio negocio
			, List<ImagenesNegociosVo> imagenes
			, String randomId);
	public List<Imagen> getAllImages();
	public List<Imagen> getOnlyValidImages();
	public void actualizarImagenes(List<ImagenNegocioVo> imagenes) throws NullPointerException;
	public void procesarImagenesNegocio(ImagenesNegocioVo imagenes, TokenPayloadVo tokenInfo) throws Exception;

}
