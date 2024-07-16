package com.sinensia.superpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;
import com.sinensia.superpollo.integration.model.EstablecimientoPL;
import com.sinensia.superpollo.integration.repositories.EstablecimientoPLRepository;

@ExtendWith(MockitoExtension.class)
public class EstablecimientoServicesImplTest {

	@Mock
	private EstablecimientoPLRepository establecimientoPLRepository;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@InjectMocks
	private EstablecimientoServicesImpl establecimientoServicesImpl;
	
	@Test
	void create_establecimiento_ok() {
		
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setCodigo(null);
		
		EstablecimientoPL establecimientoPL = new EstablecimientoPL();
		EstablecimientoPL createdEstablecimientoPL = new EstablecimientoPL();
		
		createdEstablecimientoPL.setCodigo(1000L);
		
		when(mapper.map(establecimiento, EstablecimientoPL.class)).thenReturn(establecimientoPL);
		when(establecimientoPLRepository.save(establecimientoPL)).thenReturn(createdEstablecimientoPL);
		
		Long codigo = establecimientoServicesImpl.create(establecimiento);
		
		assertEquals(1000L, codigo);
		
	}
	
	@Test
	void create_establecimiento_con_codigo_NO_null() {
		
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setCodigo(50L);
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			establecimientoServicesImpl.create(establecimiento);
		});
		
		assertEquals("Para crear un establecimiento el código ha de ser null", exception.getMessage());
	}
	
	@Test
	void read_no_existente() {
		
		when(establecimientoPLRepository.findById(587L)).thenReturn(Optional.empty());
		
		Optional<Establecimiento> optional = establecimientoServicesImpl.read(587L);
		
		assertTrue(optional.isEmpty());
	}
	
	@Test
	void read_existente() {
		
		EstablecimientoPL establecimientoPL = new EstablecimientoPL();
		establecimientoPL.setCodigo(587L);
		
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setCodigo(587L);
		
		when(establecimientoPLRepository.findById(587L)).thenReturn(Optional.of(establecimientoPL));
		when(mapper.map(establecimientoPL, Establecimiento.class)).thenReturn(establecimiento);
		
		Optional<Establecimiento> optional = establecimientoServicesImpl.read(587L);
		
		assertTrue(optional.isPresent());
		assertEquals(587L, optional.get().getCodigo());
	}
	
	@Test
	void getAll() {
		
		EstablecimientoPL establecimientoPL1 = new EstablecimientoPL();
		EstablecimientoPL establecimientoPL2 = new EstablecimientoPL();
		
		establecimientoPL1.setCodigo(100L);
		establecimientoPL2.setCodigo(200L);
		
		Establecimiento establecimiento1 = new Establecimiento();
		Establecimiento establecimiento2 = new Establecimiento();
		
		establecimiento1.setCodigo(100L);
		establecimiento2.setCodigo(200L);
		
		List<EstablecimientoPL> establecimientosPL = Arrays.asList(establecimientoPL1, establecimientoPL2);
		
		when(establecimientoPLRepository.findAll()).thenReturn(establecimientosPL);
		when(mapper.map(establecimientoPL1, Establecimiento.class)).thenReturn(establecimiento1);
		when(mapper.map(establecimientoPL2, Establecimiento.class)).thenReturn(establecimiento2);
		
		List<Establecimiento> establecimientos = establecimientoServicesImpl.getAll();
		
		assertEquals(2, establecimientos.size());
		assertTrue(establecimientos.contains(establecimiento1));
		assertTrue(establecimientos.contains(establecimiento2));
		
	}
	
	@Test
	void getEstablecimiento1DTOs() {
		
		Establecimiento1DTO establecimiento1DTO1 = new Establecimiento1DTO("La Vaguada (Madrid)");
		Establecimiento1DTO establecimiento1DTO2 = new Establecimiento1DTO("Gran Via (Barcelona)");
		
		List<Establecimiento1DTO> establecimientos1DTO = Arrays.asList(establecimiento1DTO1, establecimiento1DTO2);
		when(establecimientoPLRepository.getEstablecimiento1DTO()).thenReturn(establecimientos1DTO);
		
		List<Establecimiento1DTO> resultado = establecimientoServicesImpl.getEstablecimiento1DTOs();
		
		assertEquals(establecimientos1DTO, resultado);
	}
	
}
