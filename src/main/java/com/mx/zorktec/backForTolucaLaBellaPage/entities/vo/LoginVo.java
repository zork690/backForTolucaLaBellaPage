package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

public class LoginVo implements Serializable{

	private static final long serialVersionUID = 1990485858L;
	
	private boolean estatus;
	private String msgDesripcion;
	private String token;
	private UsuarioVo usuario;
	private PermisosVo permisos;
	
	
	public boolean isEstatus() {
		return estatus;
	}
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
	public String getMsgDesripcion() {
		return msgDesripcion;
	}
	public void setMsgDesripcion(String msgDesripcion) {
		this.msgDesripcion = msgDesripcion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UsuarioVo getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioVo usuario) {
		this.usuario = usuario;
	}
	public PermisosVo getPermisos() {
		return permisos;
	}
	public void setPermisos(PermisosVo permisos) {
		this.permisos = permisos;
	}
	

}
