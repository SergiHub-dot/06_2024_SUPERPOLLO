package com.sinensia.superpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;

@DataJpaTest
@Sql(scripts={"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class EstablecimientoPLRepositoryTest {

	@Autowired
	private EstablecimientoPLRepository establecimientoPLRepository;
	
	@Test
	void getEstablecimiento1DTOTest() {
		
		Set<String> resultadosEsperados = new HashSet<>();
		
		resultadosEsperados.add("Vaguada (Madrid)");
		resultadosEsperados.add("Gran Via 2 (Barcelona)");
		
		List<Establecimiento1DTO> resultados = establecimientoPLRepository.getEstablecimiento1DTO();
		
		for(Establecimiento1DTO establecimiento1DTO: resultados) {
			assertTrue(resultadosEsperados.contains(establecimiento1DTO.getEstablecimiento()));
		}
		
		assertEquals(2, resultados.size());
		
	}
	
}
