package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;
import java.util.Map;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.ImagenArticulo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ListUpdateArticuloImagesVo;

public interface ImagenesArticuloService {
	
	public void processingImagefromArticulo(ImagenesArticuloVo imagenes) throws Exception;
	public List<ImagenArticulo> getAllImages();
	public Map<String, String> updateImages(ListUpdateArticuloImagesVo imagenes);

}
