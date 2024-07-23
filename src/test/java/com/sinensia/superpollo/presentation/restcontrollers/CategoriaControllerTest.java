package com.sinensia.superpollo.presentation.restcontrollers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@WebMvcTest(value=CategoriaController.class)
public class CategoriaControllerTest extends AbstractControllerTest{
	
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
		
		MvcResult mvcResult = mockMvc.perform(get("/categorias")).andExpect(status().isOk()).andReturn();
		
		checkResponseBody(mvcResult, categorias);
		
	}
	
	@Test
	void solicitamos_categoria_existente() throws Exception {
		
		when(categoriaServices.read(10L)).thenReturn(Optional.of(categoria1));
		
		MvcResult mvcResult = mockMvc.perform(get("/categorias/10")).andExpect(status().isOk()).andReturn();
		
		checkResponseBody(mvcResult, categoria1);
		
	}
	
	@Test
	void solicitamos_categoria_NO_existente() throws Exception {
		
		when(categoriaServices.read(10L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/categorias/10")).andExpect(status().isNotFound()).andReturn();
		
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("No se encuentra la categoria 10"));
		
	}
	
	@Test
	void creamos_categoria_ok() throws Exception {
		
		categoria1.setId(null);
		
		when(categoriaServices.create(categoria1)).thenReturn(34456L);
		
		String requestBody = objectMapper.writeValueAsString(categoria1);
		
		mockMvc.perform(post("/categorias").content(requestBody).contentType("application/json"))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location","http://localhost/categorias/34456"));
	}
	
	@Test
	void creamos_categoria_con_id_NO_null() throws Exception{
		
		when(categoriaServices.create(categoria1)).thenThrow(new IllegalStateException("Para crear una categoría el id ha de ser null"));
		
		String requestBody = objectMapper.writeValueAsString(categoria1);
		
		MvcResult mvcResult = mockMvc.perform(post("/categorias").content(requestBody).contentType("application/json"))
				.andExpect(status().isBadRequest()).andReturn();
			
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("Para crear una categoría el id ha de ser null"));
		
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
