package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SetRoleKeycloakUserVo implements Serializable {


	private static final long serialVersionUID = 4059494909394L;
	private String id;
	private String name;
	private String description;
	private boolean composite;
	private boolean clientRole;
	private String containerId;

}
