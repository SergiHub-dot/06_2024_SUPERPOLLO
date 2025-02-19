package com.sinensia.superpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;

public interface EstablecimientoServices {

	/**
	 * Devuelve el codigo que le ha otorgado el sistema al nuevo establecimiento
	 * 
	 * Si el codigo del establecimiento NO es null lanza IllegalStateException
	 * 
	 */
	Long create(Establecimiento establecimiento);
	
	Optional<Establecimiento> read(Long codigo);
	
	List<Establecimiento> getAll();
	
	List<Establecimiento1DTO> getEstablecimiento1DTOs();

}
