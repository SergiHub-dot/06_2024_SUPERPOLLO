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

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@WebMvcTest(value=EstablecimientoController.class)
public class EstablecimientoControllerTest extends AbstractControllerTest{

	@MockBean
	private EstablecimientoServices establecimientoServices;
	
	private Establecimiento establecimiento1;
	private Establecimiento establecimiento2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void solicitamos_todos_los_establecimientos() throws Exception {
		
		List<Establecimiento> establecimientos = Arrays.asList(establecimiento1, establecimiento2);
		
		when(establecimientoServices.getAll()).thenReturn(establecimientos);
		
		MvcResult mvcResult = mockMvc.perform(get("/establecimientos")).andExpect(status().isOk()).andReturn();
				
		checkResponseBody(mvcResult, establecimientos);
		
	}
	
	@Test
	void solicitamos_establecimiento_existente() throws Exception {
		
		when(establecimientoServices.read(10L)).thenReturn(Optional.of(establecimiento1));
		
		MvcResult mvcResult = mockMvc.perform(get("/establecimientos/10")).andExpect(status().isOk()).andReturn();
		
		checkResponseBody(mvcResult, establecimiento1);
		
	}
	
	@Test
	void solicitamos_establecimiento_NO_existente() throws Exception {
		
		when(establecimientoServices.read(10L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/establecimientos/10")).andExpect(status().isNotFound()).andReturn();
				
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("No se encuentra el establecimiento 10"));
		
	}
	
	@Test
	void creamos_establecimientos_con_id_NO_null() throws Exception {
		
		when(establecimientoServices.create(establecimiento1)).thenThrow(new IllegalStateException("Para crear un establecimiento el codigo ha de ser null"));
		
		String requestBody = objectMapper.writeValueAsString(establecimiento1);
		
		MvcResult mvcResult = mockMvc.perform(post("/establecimientos").content(requestBody).contentType("application/json"))
				 	.andExpect(status().isBadRequest()).andReturn();
		
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("Para crear un establecimiento el codigo ha de ser null"));

	}
	
	@Test
	void creamos_establecimiento_ok() throws Exception {
		
		establecimiento1.setCodigo(null);
		
		when(establecimientoServices.create(establecimiento1)).thenReturn(34456L);
		
		String requestBody = objectMapper.writeValueAsString(establecimiento1);
		
		mockMvc.perform(post("/establecimientos").content(requestBody).contentType("application/json"))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location","http://localhost/establecimientos/34456"));
	}
	
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		establecimiento1 = new Establecimiento();
		establecimiento2 = new Establecimiento();
		
		establecimiento1.setCodigo(10L);
		establecimiento2.setCodigo(20L);
	}
}
