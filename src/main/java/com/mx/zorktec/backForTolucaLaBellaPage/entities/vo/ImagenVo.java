package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import lombok.ToString;

@ToString
public class ImagenVo {

	private int numImagen;
    private String imagen;
    private String descripcion;
    private double precio;
    private boolean check;
    
    public ImagenVo() {
    	
    }
    
	public ImagenVo(int numImagen, String imagen, String descripcion, double precio, boolean check) {
		super();
		this.numImagen = numImagen;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.precio = precio;
		this.check = check;
	}
	public int getNumImagen() {
		return numImagen;
	}
	public void setNumImagen(int numImagen) {
		this.numImagen = numImagen;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
    
    
}
