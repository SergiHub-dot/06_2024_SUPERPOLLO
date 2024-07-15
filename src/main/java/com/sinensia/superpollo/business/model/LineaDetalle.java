package com.sinensia.superpollo.business.model;

import java.io.Serializable;

import com.sinensia.superpollo.integration.model.ProductoPL;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class LineaDetalle implements Serializable {

	@ManyToOne
	@JoinColumn(name="CODIGO_PRODUCTO")
	private ProductoPL producto;
	
	private int cantidad;
	private double precio;
	
	public LineaDetalle() {
		
	}

	public ProductoPL getProducto() {
		return producto;
	}

	public void setProducto(ProductoPL producto) {
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
