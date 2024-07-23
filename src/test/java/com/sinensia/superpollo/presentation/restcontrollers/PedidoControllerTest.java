package com.sinensia.superpollo.presentation.restcontrollers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@WebMvcTest(value=PedidoController.class)
public class PedidoControllerTest extends AbstractControllerTest{
	
	@MockBean
	private PedidoServices pedidoServices;
	
	private Pedido pedido1;
	private Pedido pedido2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void solicitamos_todos_los_pedidos() throws Exception {
		
		List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
		
		when(pedidoServices.getAll()).thenReturn(pedidos);
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos")).andExpect(status().isOk()).andReturn();
			
		checkResponseBody(mvcResult, pedidos);
		
	}
		
	@Test
	void solicitamos_pedido_existente() throws Exception {
		
		when(pedidoServices.read(10L)).thenReturn(Optional.of(pedido1));
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos/10")).andExpect(status().isOk()).andReturn();
				
		checkResponseBody(mvcResult, pedido1);
		
	}
	
	@Test
	void solicitamos_pedido_NO_existente() throws Exception {
		
		when(pedidoServices.read(10L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos/10")).andExpect(status().isNotFound()).andReturn();
		
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("No se encuentra el pedido 10"));
		
	}
	
	@Test
	void creamos_pedido_ok() throws Exception {
		
		pedido1.setNumero(null);
		
		when(pedidoServices.create(pedido1)).thenReturn(34456L);
		
		String requestBody = objectMapper.writeValueAsString(pedido1);
		
		mockMvc.perform(post("/pedidos").content(requestBody).contentType("application/json"))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location","http://localhost/pedidos/34456"));
			
	}
	
	@Test
	void creamos_pedido_con_numero_NO_null() throws Exception{
		
		when(pedidoServices.create(pedido1)).thenThrow(new IllegalStateException("Para crear un pedido el numero ha de ser null"));
		
		String requestBody = objectMapper.writeValueAsString(pedido1);
		
		MvcResult mvcResult = mockMvc.perform(post("/pedidos").content(requestBody).contentType("application/json"))
				.andExpect(status().isBadRequest()).andReturn();
		
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("Para crear un pedido el numero ha de ser null"));
	}
	
    @Test
    void eliminamos_pedido_existente() throws Exception {
       
        mockMvc.perform(delete("/pedidos/10"))
               .andExpect(status().isNoContent());
        
        verify(pedidoServices, times(1)).delete(pedido1.getNumero());
    }
	
    @Test
    void eliminamos_pedido_NO_existente() throws Exception{
    	
    	doThrow(new IllegalStateException()).when(pedidoServices).delete(10L);
    	
		MvcResult mvcResult = mockMvc.perform(delete("/pedidos/10")).andExpect(status().isNotFound()).andReturn();
		
		checkResponseBody(mvcResult, new ErrorHttpCustomizado("No se encuentra el pedido 10"));
    }
    
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
		
	private void initObjects() {
			
		pedido1 = new Pedido();
		pedido2 = new Pedido();
			
		pedido1.setNumero(10L);
		pedido2.setNumero(20L);
		
	}
}
