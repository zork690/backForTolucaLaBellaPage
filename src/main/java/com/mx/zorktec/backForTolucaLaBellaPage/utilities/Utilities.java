package com.mx.zorktec.backForTolucaLaBellaPage.utilities;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class Utilities {

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

}
