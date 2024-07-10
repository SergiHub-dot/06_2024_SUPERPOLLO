package com.sinensia.superpollo.business.model.dtos;

import java.io.Serializable;

public class Pedido3DTO implements Serializable {

	private Long numero;
	private String establecimiento;
	private String estado;
	private String comentario; // Mostramos solo los 20 primeros caracteres y añadimos...   Ejemplo: "No entragra los vaso..."
	private String trimestre; // En formato "1T", "2T", "3T" o "4T"
	
	// TODO
}
