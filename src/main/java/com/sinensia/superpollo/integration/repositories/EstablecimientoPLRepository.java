package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;
import com.sinensia.superpollo.integration.model.EstablecimientoPL;

public interface EstablecimientoPLRepository extends JpaRepository<EstablecimientoPL, Long>{

	@Query("SELECT new com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO(CONCAT(e.nombreComercial, ' (',e.direccion.poblacion,')')) "
		 + "  FROM EstablecimientoPL e")
	List<Establecimiento1DTO> getEstablecimiento1DTO();
}
