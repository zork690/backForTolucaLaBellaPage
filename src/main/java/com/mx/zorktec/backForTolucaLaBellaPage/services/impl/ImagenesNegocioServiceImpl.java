package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Imagen;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Negocio;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesNegociosVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesNegocioService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;

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
			String timeStamp = Utilities.generateTimeStampString();
			String nombreImagen = timeStamp+"_"+imagen.getNombre();
			String base64 = imagen.getBaseContent();
			byte[] data = DatatypeConverter.parseBase64Binary(base64);
			String path = this.basePath+"/"+nombreImagen;
			File file = new File(path);
			
			try {
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
				outputStream.write(data);
				LOG.info("URL: {}",path);
				LOG.info("Saving data into table..");
				Imagen i = new Imagen();
				i.setNombre(nombreImagen);
				i.setIdNegocio(negocio);
				this.imagenDao.saveOrUpdate(i);
			}catch(Exception e) {
				LOG.error("A processing image error occurred {}",e.getLocalizedMessage());
			}
		});
		
	}
	
	@Override
	public void procesarImagenesNegocio(ImagenesNegocioVo imagenes, TokenPayloadVo tokenInfo) {
		LOG.info("Usuario email: {}", tokenInfo.getEmail());
		//TODO sacar el id del usuario por su email y hacer select de idNegocios que tiene el usuario y en caso que no coincida con el idNegocio enviado mandar una excepción
		Negocio n = new Negocio();
		n.setIdNegocio(imagenes.getIdNegocio());
		this.processingImagefromNegocio(n, imagenes.getImagenes(), imagenes.getIdNegocio());
	}

	@Override
	public List<Imagen> getAllImages() {
		return this.imagenDao.findAll();
	}

	@Override
	public List<Imagen> getOnlyValidImages() {
		return this.imagenDao.findOnlyValids();
	}

	@Override
	public void actualizarImagenes(List<ImagenNegocioVo> imagenes) throws NullPointerException {
		imagenes.forEach((imagen)->{
			Optional<Imagen> i = this.imagenDao.findById(Imagen.class, imagen.getId());
			if(i == null) {
				throw new NullPointerException("imagen no encontrada");
			}
			if(i.isPresent()) {
				Imagen im = i.get();
				im.setIdNegocio(i.get().getIdNegocio());
				im.setNombre(i.get().getNombre());
				im.setValid(imagen.isValid());
				this.imagenDao.saveOrUpdate(im);
			}
		});
		
		
	}

}
