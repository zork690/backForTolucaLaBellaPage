package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo implements Serializable{

	private static final long serialVersionUID = 1990485858L;
	
	private String email;
	private String password;

}
