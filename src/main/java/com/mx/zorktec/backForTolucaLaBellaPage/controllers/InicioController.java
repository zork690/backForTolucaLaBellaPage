package com.mx.zorktec.backForTolucaLaBellaPage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InicioController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listar(Model datosPaVista) {	
		datosPaVista.addAttribute("titulo", "RUNNING Back For Toluca La Bella Page ...");
		return "inicio";
	}
	
}
