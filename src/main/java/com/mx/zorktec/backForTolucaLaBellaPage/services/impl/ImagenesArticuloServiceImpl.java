package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mx.zorktec.backForTolucaLaBellaPage.daos.ImagenArticuloDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Articulo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.ImagenArticulo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenesArticuloVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ListUpdateArticuloImagesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.ImagenesArticuloService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;

@Service
public class ImagenesArticuloServiceImpl implements ImagenesArticuloService {

	private static final Logger LOG = LogManager.getLogger(ImagenesArticuloServiceImpl.class);

	@Value("${server.base.path}")
	private String basePath;

	@Autowired
	private ImagenArticuloDao genericDao;

	@Override
	public void processingImagefromArticulo(ImagenesArticuloVo imagenes) throws Exception {
		LOG.info("Processing images of article id {} from base64 to a server folder..."
				, imagenes.getIdArticulo());
		LOG.info("DIRECTORY PATH: {}", this.basePath);

		imagenes.getImagenes().forEach((imagen) -> {
			String timeStamp = Utilities.generateTimeStampString();
			String nombreImagen = timeStamp + "_" + imagen.getNombre().replaceAll("\\s+", "");
			String base64 = imagen.getBaseContent();
			byte[] data = DatatypeConverter.parseBase64Binary(base64);
			String path = this.basePath + "/" + nombreImagen;
			File file = new File(path);

			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){
				outputStream.write(data);
				LOG.info("URL: {}", path);
				LOG.info("Saving data into table..");
				ImagenArticulo i = new ImagenArticulo(); 
				i.setNombre(nombreImagen);
				Articulo a = new Articulo();
				a.setId( Integer.valueOf( imagenes.getIdArticulo() ) );
				i.setArticulo(a);
				i.setValid(i.isValid());
				this.genericDao.saveOrUpdate(i);
			} catch (IOException e) {
				LOG.error("A processing image error occurred {}", e.getLocalizedMessage());
			}

		});
	}

	@Override
	public List<ImagenArticulo> getAllImages() {
		return this.genericDao.findAll("ImagenArticulo", ImagenArticulo.class).orElse(null);
	}

	@Override
	public Map<String, String> updateImages(ListUpdateArticuloImagesVo imagenes) {
		Map<String, String> resultados = new HashMap<String, String>();
		imagenes.getImagenes().forEach((imagen)->{
			LOG.info("Buscando imagen {}", imagen);
			try {
				ImagenArticulo imagenArticulo =	this.genericDao
						.findById( ImagenArticulo.class, Integer.valueOf( imagen.getId() ) )
						.orElse(null);
				imagenArticulo.setValid( Boolean.valueOf( imagen.getValid() ) );
				this.genericDao.saveOrUpdate(imagenArticulo);

			}catch(NullPointerException e) {
				LOG.info("Imagen con id {} no ha sido encontrada", imagen.getId());
				resultados.put(imagen.getId(), String.format( "Imagen con id %s no ha sido encontrada"
						, imagen.getId() ) );
			}
		});

		return resultados;
	}

}
