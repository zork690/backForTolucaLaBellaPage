package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.lang.reflect.Field;

public class PermisosVo implements Serializable{

	private static final long serialVersionUID = 1769796858596868L;
	
	private boolean altaUsuarios;
	private boolean altaImagenes;
	private boolean consultaImagenes;
	private boolean editarImagenes;
	private boolean eliminarImagenes;
	
	
	public PermisosVo() {
		this.altaUsuarios = false;
		this.altaImagenes = false;
		this.consultaImagenes = false;
		this.editarImagenes = false;
		this.eliminarImagenes = false;
	}
	
	
	public void setPermisoValue(String varName, boolean value)
	        throws NoSuchFieldException, IllegalAccessException {
	    Field field = this.getClass().getDeclaredField(varName);
	    field.set(this, value);
	}
	
	public boolean isAltaUsuarios() {
		return altaUsuarios;
	}
	public void setAltaUsuarios(boolean altaUsuarios) {
		this.altaUsuarios = altaUsuarios;
	}
	public boolean isAltaImagenes() {
		return altaImagenes;
	}
	public void setAltaImagenes(boolean altaImagenes) {
		this.altaImagenes = altaImagenes;
	}
	public boolean isConsultaImagenes() {
		return consultaImagenes;
	}
	public void setConsultaImagenes(boolean consultaImagenes) {
		this.consultaImagenes = consultaImagenes;
	}
	public boolean isEditarImagenes() {
		return editarImagenes;
	}
	public void setEditarImagenes(boolean editarImagenes) {
		this.editarImagenes = editarImagenes;
	}
	public boolean isEliminarImagenes() {
		return eliminarImagenes;
	}
	public void setEliminarImagenes(boolean eliminarImagenes) {
		this.eliminarImagenes = eliminarImagenes;
	}

}
