package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioKeycloakVo {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private boolean emailVerified;
	private boolean enabled;
	private List<CredencialesVo> credentials;

}
