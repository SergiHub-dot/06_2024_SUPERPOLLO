package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;
import java.util.Date;

public class Pedido2DTO implements Serializable {
	
	private Long numero;
	private Date fechaHora;
	private String estado;
	private int numeroLineas;
	private double importeTotal;
	
	public Pedido2DTO(Long numero, Date fechaHora, String estado, int numeroLineas, double importeTotal) {
		this.numero = numero;
		this.fechaHora = fechaHora;
		this.estado = estado;
		this.numeroLineas = numeroLineas;
		this.importeTotal = importeTotal;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public String getEstado() {
		return estado;
	}

	public int getNumeroLineas() {
		return numeroLineas;
	}

	public double getImporteTotal() {
		return importeTotal;
	}

	@Override
	public String toString() {
		return "Pedido2DTO [numero=" + numero + ", fechaHora=" + fechaHora + ", estado=" + estado + ", numeroLineas="
				+ numeroLineas + ", importeTotal=" + importeTotal + "]";
	}

}
