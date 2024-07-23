package com.sinensia.superpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.integration.model.CategoriaPL;
import com.sinensia.superpollo.integration.repositories.CategoriaPLRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServicesImplTest {

	@Mock
	private CategoriaPLRepository categoriaPLRepository;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@InjectMocks
	private CategoriaServicesImpl categoriaServicesImpl;
	
	private CategoriaPL categoriaPL1;
	private CategoriaPL categoriaPL2;
	private Categoria categoria1;
	private Categoria categoria2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void create_categoria_ok() {
		
		categoria1.setId(null);
		
		when(mapper.map(categoria1, CategoriaPL.class)).thenReturn(categoriaPL1);
		when(categoriaPLRepository.save(categoriaPL1)).thenReturn(categoriaPL2);
		
		Long id = categoriaServicesImpl.create(categoria1);
		
		assertEquals(200L, id);
		
	}
	
	@Test
	void create_categoria_con_id_NO_null() {
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			categoriaServicesImpl.create(categoria1);
		});
		
		assertEquals("Para creor una categoría el id ha de ser null", exception.getMessage());
	}
	
	@Test
	void read_existente() {
	
		when(categoriaPLRepository.findById(100L)).thenReturn(Optional.of(categoriaPL1));
		when(mapper.map(categoriaPL1, Categoria.class)).thenReturn(categoria1);
		
		Optional<Categoria> optional = categoriaServicesImpl.read(100L);
		
		assertTrue(optional.isPresent());
		assertEquals(100L, optional.get().getId());
		
	}
	
	@Test
	void read_no_existente() {
		
		when(categoriaPLRepository.findById(587L)).thenReturn(Optional.empty());
		
		Optional<Categoria> optional = categoriaServicesImpl.read(587L);
		
		assertTrue(optional.isEmpty());
	}
	
	@Test
	void getAll() {
		
		List<CategoriaPL> categoriasPL = Arrays.asList(categoriaPL1, categoriaPL2);
	
		when(categoriaPLRepository.findAll()).thenReturn(categoriasPL);
		when(mapper.map(categoriaPL1, Categoria.class)).thenReturn(categoria1);
		when(mapper.map(categoriaPL2, Categoria.class)).thenReturn(categoria2);
		
		List<Categoria> categorias = categoriaServicesImpl.getAll();
		
		assertEquals(2, categorias.size());
		assertTrue(categorias.contains(categoria1));
		assertTrue(categorias.contains(categoria2));
		
	}
	
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		categoriaPL1 = new CategoriaPL();
		categoriaPL2 = new CategoriaPL();
		
		categoriaPL1.setId(100L);
		categoriaPL2.setId(200L);
		
		categoria1 = new Categoria();
		categoria2 = new Categoria();
		
		categoria1.setId(100L);
		categoria2.setId(200L);
	}
	
}
