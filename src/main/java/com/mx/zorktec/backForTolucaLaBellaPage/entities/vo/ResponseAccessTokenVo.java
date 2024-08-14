package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAccessTokenVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String accessToken;
	private int expiresIn;
	private int refreshExpiresIn;
	private String refreshToken;
	private String tokenType;
	private int notBeforePolicy;
	private String sessionState;
	private String scope;

}
