package com.mx.zorktec.apiBackAdminTienda.entities.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PassVo implements Serializable{
	
	
	private static final long serialVersionUID = 104985765790098L;
	
	@NotBlank(message="Contraseña requerida")
	private String pass;
	
	@NotBlank(message="Confirmación requerida")
	private String confirm;

	
}
