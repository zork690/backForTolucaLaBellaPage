package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioVo implements Serializable{

	private String nombre;
	private String email;
	private String telefono;
	private String password;
}
