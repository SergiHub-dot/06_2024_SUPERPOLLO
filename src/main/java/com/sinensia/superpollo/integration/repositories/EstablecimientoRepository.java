package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long>{

	@Query("SELECT new com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO(CONCAT(e.nombreComercial, ' (',e.direccion.poblacion,')')) "
		 + "  FROM Establecimiento e")
	List<Establecimiento1DTO> getEstablecimiento1DTO();
}
