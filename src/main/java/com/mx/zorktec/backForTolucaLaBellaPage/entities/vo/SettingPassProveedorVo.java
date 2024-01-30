package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SettingPassProveedorVo implements Serializable{
	
	private static final long serialVersionUID = 1456068658L;
	
	@NotBlank(message="Contraseña requerida")
	private String pass;
	
	@NotBlank(message="Confirmación requerida")
	private String confirm;
	
	@NotBlank(message="Código de validación es requerido")
	private String code;

}
