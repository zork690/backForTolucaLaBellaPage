package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

public class UsuarioVo {

	private Long idUsuario;
	private String nombreUsuario;
	private  String contrasena;
	private Long idPerfil;
	private String descPerfil;
	
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getDescPerfil() {
		return descPerfil;
	}
	public void setDescPerfil(String descPerfil) {
		this.descPerfil = descPerfil;
	}
}
