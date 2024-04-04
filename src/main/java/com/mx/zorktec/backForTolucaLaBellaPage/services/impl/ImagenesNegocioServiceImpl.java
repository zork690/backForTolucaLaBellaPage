package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;

@Service
public class ImagenesNegocioServiceImpl implements ImagenesNegocioService{

	private static final Logger LOG = LogManager.getLogger(ImagenesNegocioServiceImpl.class);
	
	@Value("${server.base.path}")
	private String basePath;
	
	@Override
	public void processingImagefromNegocio(List<ImagenesNegociosVo> imagenes, String randomId) {
		LOG.info("Processing images of business id {} from base64 to a server folder...",randomId);
		
		imagenes.forEach((imagen)->{
			String base64 = imagen.getBaseContent();
			byte[] data = DatatypeConverter.parseBase64Binary(base64);
			String path = this.basePath+"/"+imagen.getNombre();
			File file = new File(path);
			
			try {
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
				outputStream.write(data);
				LOG.info("URL: {}",path);
			}catch(Exception e) {
				LOG.error("A processing image error occurred {}",e.getLocalizedMessage());
			}
		});
		
	}

}
