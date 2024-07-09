package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;

public class Producto1DTO implements Serializable{

	private String nombre; // Incorporando código en formato... PATATAS BRAVAS [103]
	private Double precio;
	private String categoria;
	
	public Producto1DTO(String nombre, Double precio, String categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public String getCategoria() {
		return categoria;
	}

	@Override
	public String toString() {
		return "Producto1DTO [nombre=" + nombre + ", precio=" + precio + ", categoria=" + categoria + "]";
	}
	
}
