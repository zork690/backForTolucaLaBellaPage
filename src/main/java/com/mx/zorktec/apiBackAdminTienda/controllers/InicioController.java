package com.mx.zorktec.apiBackAdminTienda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InicioController {

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String listar(Model datosPaVista) {	
		datosPaVista.addAttribute("titulo", "API BACK ADMIN TIENDA...");
		return "inicio";
	}
	
}
