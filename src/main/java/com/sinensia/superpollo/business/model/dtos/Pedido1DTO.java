package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;

public class Pedido1DTO implements Serializable {
	
	private Long numero;
	private String fecha; // "24/07/2024"
	private String hora;  // "11:34"
	private String nombreEstablecimiento; // el nombre comercial
	private String estado;
	
	public Pedido1DTO(Long numero, String fecha, String hora, String nombreEstablecimiento, String estado) {
		this.numero = numero;
		this.fecha = fecha;
		this.hora = hora;
		this.nombreEstablecimiento = nombreEstablecimiento;
		this.estado = estado;
	}

	public Long getNumero() {
		return numero;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public String getNombreEstablecimiento() {
		return nombreEstablecimiento;
	}

	public String getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Pedido1DTO [numero=" + numero + ", fecha=" + fecha + ", hora=" + hora + ", nombreEstablecimiento="
				+ nombreEstablecimiento + ", estado=" + estado + "]";
	}

}
