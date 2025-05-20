package com.mx.zorktec.backForTolucaLaBellaPage.utilities;

import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;

public class Utilities {

	private static final Logger LOG = LogManager.getLogger(Utilities.class);

	public static final String limpiaCadena(String texto) {

		// Quita espacios de más que estén en medio(lo deja en un solo espacio)
		// Quita espacios al inicio y al final
		// Convierte a mayúsculas
		return texto.replaceAll("\\s+", " ").trim().toUpperCase();

	}

	public static final String generateIdForClient() {
		Long ms = System.currentTimeMillis();
		return String.valueOf(ms);
	}


	public static final String generateTimeStampString() {
		Timestamp ts = Timestamp.from(Instant.now());
		return String.valueOf(ts.getTime());
	}

	public static final TokenPayloadVo getInfoFromToken(String token) {
		String t = token.replace("Bearer", "");
		LOG.info("Token: {}", t);
		String[] chunks = t.split("\\.");
		Base64 decoder = new Base64(true);
		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		LOG.info("PAYLOAD: {}", payload);
		JsonObject o = new Gson().fromJson(payload, JsonObject.class);
		TokenPayloadVo tokenPayload = new TokenPayloadVo();
		tokenPayload.setEmail(o.get("email").getAsString());
		LOG.info("Email from payload: {}", tokenPayload.getEmail());
		return tokenPayload;

	}

	public static boolean imageIsSmallThanAllowedSize(String imagenBase64, long sizeInBytesAllowed) {
		String lastTwoChars = imagenBase64.substring(imagenBase64.length() - 2);
		LOG.info("Last two characters of image: {}", lastTwoChars);
		int y = 0;
		if (lastTwoChars.equals("==")) {
			y = 2;
		} else if (lastTwoChars.equals("=")) {
			y = 1;
		}
		long sizeImageInBytes = (long)Math.ceil((imagenBase64.length() * 3) / 4.0) - y;
		LOG.info("SIZE IMAGE BYTES: {}", sizeImageInBytes);
		return sizeImageInBytes <= sizeInBytesAllowed;
	}

}
