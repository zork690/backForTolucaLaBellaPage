package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;

public interface ImagenesNegocioService {
	
	void processingImagefromNegocio(List<ImagenesNegociosVo> imagenes, String randomId);

}
