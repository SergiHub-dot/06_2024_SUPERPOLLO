package com.sinensia.superpollo.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="PEDIDOS")
public class Pedido implements Serializable {

	@Id
	@Column(name="CODIGO")
	private Long numero;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHora;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_ESTABLECIMIENTO")
	private Establecimiento establecimiento;
	
	@Column(name="COMENTARIO")
	private String observaciones;
	
	@ElementCollection
	@JoinTable(name="LINEAS_PEDIDO", joinColumns = @JoinColumn(name="CODIGO_PEDIDO"))
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
