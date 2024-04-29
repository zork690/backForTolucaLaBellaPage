package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;

@Service
public class ImagenesNegocioServiceImpl implements ImagenesNegocioService{

	private static final Logger LOG = LogManager.getLogger(ImagenesNegocioServiceImpl.class);
	
	@Value("${server.base.path}")
	private String basePath;
	
	@Autowired
	private ImagenDao imagenDao;
	
	@Override
	public void processingImagefromNegocio(Negocio negocio, List<ImagenesNegociosVo> imagenes, String randomId) {
		LOG.info("Processing images of business id {} from base64 to a server folder...",randomId);
		LOG.info("DIRECTORY PATH: {}", this.basePath);
		
		imagenes.forEach((imagen)->{
			String base64 = imagen.getBaseContent();
			byte[] data = DatatypeConverter.parseBase64Binary(base64);
			String path = this.basePath+"/"+imagen.getNombre();
			File file = new File(path);
			
			try {
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
				outputStream.write(data);
				LOG.info("URL: {}",path);
				LOG.info("Saving data into table..");
				Imagen i = new Imagen();
				i.setNombre(imagen.getNombre());
				i.setIdNegocio(negocio);
				this.imagenDao.saveOrUpdate(i);
			}catch(Exception e) {
				LOG.error("A processing image error occurred {}",e.getLocalizedMessage());
			}
		});
		
	}

	@Override
	public List<Imagen> getAllImages() {
		return this.imagenDao.findAll("Imagen", Imagen.class).orElse(null);
	}

}
