package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;

public class Pedido3DTO implements Serializable {

	private Long numero;
	private String establecimiento;
	private String estado;
	private String comentario; // Mostramos solo los 20 primeros caracteres y añadimos...   Ejemplo: "No entragra los vaso..."
	private String trimestre;  // En formato "1T", "2T", "3T" o "4T"
	
	public Pedido3DTO(Long numero, String establecimiento, String estado, String comentario, String trimestre) {
		this.numero = numero;
		this.establecimiento = establecimiento;
		this.estado = estado;
		this.comentario = comentario;
		this.trimestre = trimestre;
	}

	public Long getNumero() {
		return numero;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public String getEstado() {
		return estado;
	}

	public String getComentario() {
		return comentario;
	}

	public String getTrimestre() {
		return trimestre;
	}

	@Override
	public String toString() {
		return "Pedido3DTO [numero=" + numero + ", establecimiento=" + establecimiento + ", estado=" + estado
				+ ", comentario=" + comentario + ", trimestre=" + trimestre + "]";
	}
	
}
