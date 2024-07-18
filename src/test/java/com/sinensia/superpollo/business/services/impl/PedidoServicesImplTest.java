package com.sinensia.superpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;
import com.sinensia.superpollo.integration.model.PedidoPL;
import com.sinensia.superpollo.integration.repositories.PedidoPLRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServicesImplTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Mock
	private PedidoPLRepository pedidoPLRepository;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@InjectMocks
	private PedidoServicesImpl pedidoServicesImpl;
	
	private PedidoPL pedidoPL1;
	private PedidoPL pedidoPL2;
	private Pedido pedido1;
	private Pedido pedido2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void create_pedido_ok() {
		
		pedido1.setNumero(null);
		
		when(mapper.map(pedido1, PedidoPL.class)).thenReturn(pedidoPL1);
		when(pedidoPLRepository.save(pedidoPL1)).thenReturn(pedidoPL2);
		
		Long numero = pedidoServicesImpl.create(pedido1);
		
		assertEquals(200L, numero);
		
	}
	
	@Test
	void create_pedido_con_id_NO_null() {
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			pedidoServicesImpl.create(pedido1);
		});
		
		assertEquals("Para crear un pedido el número ha de ser null", exception.getMessage());
	}
	
	@Test
	void read_existente() {
	
		when(pedidoPLRepository.findById(100L)).thenReturn(Optional.of(pedidoPL1));
		when(mapper.map(pedidoPL1, Pedido.class)).thenReturn(pedido1);
		
		Optional<Pedido> optional = pedidoServicesImpl.read(100L);
		
		assertTrue(optional.isPresent());
		assertEquals(100L, optional.get().getNumero());	
	}
	
	@Test
	void read_no_existente() {
		
		when(pedidoPLRepository.findById(587L)).thenReturn(Optional.empty());
		
		Optional<Pedido> optional = pedidoServicesImpl.read(587L);
		
		assertTrue(optional.isEmpty());
	}
	
	@Test
	void delete_ok() {
		
		when(pedidoPLRepository.existsById(200L)).thenReturn(true);
		
		pedidoServicesImpl.delete(200L);
		
		verify(pedidoPLRepository, times(1)).deleteById(200L);
		
	}
	
	@Test
	void delete_NO_existente() {
		
		when(pedidoPLRepository.existsById(200L)).thenReturn(false);
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			pedidoServicesImpl.delete(200L);
		});
		
		assertEquals("No existe el pedido número 200", exception.getMessage());
	
	}
	
	@Test
	void getAllTest() {
		
		List<PedidoPL> pedidosPL = Arrays.asList(pedidoPL1, pedidoPL2);
	
		when(pedidoPLRepository.findAll()).thenReturn(pedidosPL);
		when(mapper.map(pedidoPL1, Pedido.class)).thenReturn(pedido1);
		when(mapper.map(pedidoPL2, Pedido.class)).thenReturn(pedido2);
		
		List<Pedido> pedidos = pedidoServicesImpl.getAll();
		
		assertEquals(2, pedidos.size());
		assertTrue(pedidos.containsAll(Arrays.asList(pedido1, pedido2)));
	}
	
	@Test
	void getPedido1DTOsTest() throws Exception {
			
		Date fecha1 = sdf.parse("25/10/2023 11:34");
		Date fecha2 = sdf.parse("26/10/2023 17:20");
		
		Object[] fila1 = {1L, fecha1, "Nombre_A", "NUEVO"};
		Object[] fila2 = {2L, fecha2, "Nombre_B", "CANCELADO"};
		
		List<Object[]> tablaResultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getPedido1DTO()).thenReturn(tablaResultados);
		
		List<Pedido1DTO> pedido1DTOs = pedidoServicesImpl.getPedido1DTOs();
		
		Set<String> resultadosAsString = new HashSet<>();
		
		for(Pedido1DTO pedido1DTO: pedido1DTOs) {
			resultadosAsString.add(pedido1DTO.getNumero() 
					+ "_" + pedido1DTO.getFecha()
					+ "_" + pedido1DTO.getHora()
					+ "_" + pedido1DTO.getNombreEstablecimiento() 
					+ "_" + pedido1DTO.getEstado());
		}
	
		assertEquals(2, pedido1DTOs.size());
		assertTrue(resultadosAsString.contains("1_25/10/2023_11:34_Nombre_A_NUEVO"));
		assertTrue(resultadosAsString.contains("2_26/10/2023_17:20_Nombre_B_CANCELADO"));
	}
	
	@Test
	void getPedido2DTOsTest() throws Exception {
		
		Date fecha1 = sdf.parse("25/10/2023 11:34");
		Date fecha2 = sdf.parse("26/10/2023 17:20");
		
		Object[] fila1 = {1L, fecha1, "NUEVO", 2, 14.5};
		Object[] fila2 = {2L, fecha2, "CANCELADO", 1, 4.0};
		
		List<Object[]> tablaResultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getPedido2DTO()).thenReturn(tablaResultados);
		
		List<Pedido2DTO> pedido2DTOs = pedidoServicesImpl.getPedido2DTOs();
		
		Set<String> resultadosAsString = new HashSet<>();
		
		for(Pedido2DTO pedido2DTO: pedido2DTOs) {
			resultadosAsString.add(pedido2DTO.getNumero() 
					+ "_" + pedido2DTO.getFechaHora() 
					+ "_" + pedido2DTO.getEstado()
					+ "_" + pedido2DTO.getNumeroLineas()
					+ "_" + pedido2DTO.getImporteTotal());
		}
		
		assertEquals(2, pedido2DTOs.size());
		assertTrue(resultadosAsString.contains("1_Wed Oct 25 11:34:00 CEST 2023_NUEVO_2_14.5"));
		assertTrue(resultadosAsString.contains("2_Thu Oct 26 17:20:00 CEST 2023_CANCELADO_1_4.0"));		
	}
	
	@Test
	void getPedido3DTOsTest() {
		
		Pedido3DTO pedido3DTO1 = new Pedido3DTO(1L, "Establecimiento_A", "CANCELADO", "Comentario1", "1T");
		Pedido3DTO pedido3DTO2 = new Pedido3DTO(2L, "Establecimiento_B", "NUEVO", "Comentario2", "3T");
		
		when(pedidoPLRepository.getPedido3DTO()).thenReturn(Arrays.asList(pedido3DTO1, pedido3DTO2));
		
		List<Pedido3DTO> pedido3DTOs = pedidoServicesImpl.getPedido3DTOs();
		
		Set<String> resultadosAsString = new HashSet<>();
		
		for(Pedido3DTO pedido3DTO: pedido3DTOs) {
			resultadosAsString.add(pedido3DTO.getNumero() 
					+ "_" + pedido3DTO.getEstablecimiento() 
					+ "_" + pedido3DTO.getEstado()
					+ "_" + pedido3DTO.getComentario()
					+ "_" + pedido3DTO.getTrimestre());
		}
		
		assertEquals(2, pedido3DTOs.size());
		assertTrue(resultadosAsString.contains("1_Establecimiento_A_CANCELADO_Comentario1_1T"));
		assertTrue(resultadosAsString.contains("2_Establecimiento_B_NUEVO_Comentario2_3T"));		

	}
	
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
		
	private void initObjects() {
		
		pedidoPL1 = new PedidoPL();
		pedidoPL2 = new PedidoPL();
		
		pedidoPL1.setNumero(100L);
		pedidoPL2.setNumero(200L);
		
		pedido1 = new Pedido();
		pedido2 = new Pedido();
		
		pedido1.setNumero(100L);
		pedido2.setNumero(200L);
	}
	
}
