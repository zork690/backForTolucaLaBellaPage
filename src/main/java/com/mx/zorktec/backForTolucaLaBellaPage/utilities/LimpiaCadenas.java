package com.mx.zorktec.backForTolucaLaBellaPage.utilities;

public class LimpiaCadenas {

	public static final String limpiaCadena(String texto) {

		// Quita espacios de más que estén en medio(lo deja en un solo espacio)
		// Quita espacios al inicio y al final
		// Convierte a mayúsculas
		return texto.replaceAll("\\s+", " ").trim().toUpperCase();

	}

}
