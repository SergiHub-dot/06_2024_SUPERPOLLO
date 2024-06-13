package com.sinensia.superpollo.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Pedido implements Serializable {

	private Long numero;
	private Date fechaHora;
	private Establecimiento establecimiento;
	private String observaciones;
	private List<LineaDetalle> lineasDetalle;
	
	public Pedido() {
		
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<LineaDetalle> getLineasDetalle() {
		return lineasDetalle;
	}

	public void setLineasDetalle(List<LineaDetalle> lineasDetalle) {
		this.lineasDetalle = lineasDetalle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Pedido [numero=" + numero + ", fechaHora=" + fechaHora + ", establecimiento=" + establecimiento
				+ ", observaciones=" + observaciones + ", lineasDetalle=" + lineasDetalle + "]";
	}
}
