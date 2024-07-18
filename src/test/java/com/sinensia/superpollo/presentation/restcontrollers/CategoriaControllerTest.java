package com.sinensia.superpollo.presentation.restcontrollers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@WebMvcTest(value=CategoriaController.class)
public class CategoriaControllerTest {

	@Autowired
	private MockMvc miniPostman;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private CategoriaServices categoriaServices;
	
	private Categoria categoria1;
	private Categoria categoria2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void solicitamos_todas_las_categorias() throws Exception {
		
		List<Categoria> categorias = Arrays.asList(categoria1, categoria2);
		
		when(categoriaServices.getAll()).thenReturn(categorias);
		
		MvcResult mvcResult = miniPostman.perform(get("/categorias")).andExpect(status().isOk()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String categoriasAsJSON = objectMapper.writeValueAsString(categorias);
		
		assertEquals(categoriasAsJSON,strBodyRespuesta);
		
	}
	
	@Test
	void solicitamos_categoria_existente() throws Exception {
		
		when(categoriaServices.read(10L)).thenReturn(Optional.of(categoria1));
		
		MvcResult mvcResult = miniPostman.perform(get("/categorias/10")).andExpect(status().isOk()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String categoriasAsJSON = objectMapper.writeValueAsString(categoria1);
		
		assertEquals(categoriasAsJSON,strBodyRespuesta);
		
	}
	
	@Test
	void solicitamos_categoria_NO_existente() throws Exception {
		
		when(categoriaServices.read(10L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = miniPostman.perform(get("/categorias/10")).andExpect(status().isNotFound()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String categoriasAsJSON = objectMapper.writeValueAsString(new ErrorHttpCustomizado("No se encuentra la categoria 10"));
		
		assertEquals(categoriasAsJSON,strBodyRespuesta);
		
	}
	
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		categoria1 = new Categoria();
		categoria2 = new Categoria();
		
		categoria1.setId(10L);
		categoria2.setId(20L);
	}
}
