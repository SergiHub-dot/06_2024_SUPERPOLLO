package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;

public class Establecimiento1DTO implements Serializable {

	private String establecimiento; // En formato: "La Vaguada (Mataró)"
	
	public  Establecimiento1DTO(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	@Override
	public String toString() {
		return "Establecimiento1DTO [establecimiento=" + establecimiento + "]";
	}
	
}
