package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;

public interface ImagenesNegocioService {
	
	public void processingImagefromNegocio(Negocio negocio
			, List<ImagenesNegociosVo> imagenes
			, String randomId);
	public List<Imagen> getAllImages();
	public List<Imagen> getOnlyValidImages();

}
