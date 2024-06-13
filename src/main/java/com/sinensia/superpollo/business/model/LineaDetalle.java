package com.sinensia.superpollo.business.model;

import java.io.Serializable;

public class LineaDetalle implements Serializable {

	private Producto producto;
	private int cantidad;
	private double precio;
	
	public LineaDetalle() {
		
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "LineaDetalle [producto=" + producto + ", cantidad=" + cantidad + ", precio=" + precio + "]";
	}

}
