package com.sinensia.superpollo.presentation.restcontrollers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@WebMvcTest(value=ProductoController.class)
public class ProductoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductoServices productoServices;
	
	private Producto producto1;
	private Producto producto2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void solicitamos_todos_los_productos() throws Exception {
		
		List<Producto> productos = Arrays.asList(producto1, producto2);
		
		when(productoServices.getAll()).thenReturn(productos);
		
		MvcResult mvcResult = mockMvc.perform(get("/productos")).andExpect(status().isOk()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String productoAsJSON = objectMapper.writeValueAsString(productos);
		
		assertEquals(productoAsJSON,strBodyRespuesta);
		
	}
	
    @Test
    public void solicitamos_productos_con_parametros() throws Exception {
	       
    	List<Producto> productos = Arrays.asList(producto1, producto2);

	    when(productoServices.getBetweenPriceRange(5.0, 25.0)).thenReturn(productos);

        MvcResult mvcResult = mockMvc.perform(get("/productos")
        										.param("min", "5.0")
        										.param("max", "25.0"))
	                                     .andExpect(status().isOk())
	                                     .andReturn();

        String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        String productoAsJSON = objectMapper.writeValueAsString(productos);

        assertEquals(productoAsJSON, strBodyRespuesta);
        
    }
		
	@Test
	void solicitamos_producto_existente() throws Exception {
		
		when(productoServices.read(10L)).thenReturn(Optional.of(producto1));
		
		MvcResult mvcResult = mockMvc.perform(get("/productos/10")).andExpect(status().isOk()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		String productoAsJSON = objectMapper.writeValueAsString(producto1);
		
		assertEquals(productoAsJSON, strBodyRespuesta);
		
	}
	
	@Test
	void solicitamos_producto_NO_existente() throws Exception {
		
		when(productoServices.read(10L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/productos/10")).andExpect(status().isNotFound()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		String productoAsJSON = objectMapper.writeValueAsString(new ErrorHttpCustomizado("No se encuentra el producto 10"));
		
		assertEquals(productoAsJSON, strBodyRespuesta);
		
	}
	
	@Test
	void creamos_producto_ok() throws Exception {
		
		producto1.setCodigo(null);
		
		when(productoServices.create(producto1)).thenReturn(34456L);
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		mockMvc.perform(post("/productos").content(requestBody).contentType("application/json"))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location","http://localhost/productos/34456"));
			
	}
	
	@Test
	void creamos_producto_con_codigo_NO_null() throws Exception{
		
		when(productoServices.create(producto1)).thenThrow(new IllegalStateException("Para crear un producto el codigo ha de ser null"));
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		MvcResult mvcResult = mockMvc.perform(post("/productos").content(requestBody).contentType("application/json"))
				.andExpect(status().isBadRequest()).andReturn();
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String mensajeError = objectMapper.writeValueAsString(new ErrorHttpCustomizado("Para crear un producto el codigo ha de ser null"));
		
		assertEquals(mensajeError, strBodyRespuesta);
	}
	
    @Test
    void eliminamos_producto_existente() throws Exception {
       
        mockMvc.perform(delete("/productos/10"))
               .andExpect(status().isNoContent());
        
        verify(productoServices, times(1)).delete(producto1.getCodigo());
    }
	
    @Test
    void eliminamos_producto_NO_existente() throws Exception{
    	
    	doThrow(new IllegalStateException()).when(productoServices).delete(10L);
    	
		MvcResult mvcResult = mockMvc.perform(delete("/productos/10")).andExpect(status().isNotFound()).andReturn();
    	
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		String productoAsJSON = objectMapper.writeValueAsString(new ErrorHttpCustomizado("No se encuentra el producto 10"));
		
		assertEquals(productoAsJSON, strBodyRespuesta);
    }
    
	@Test
	void actualizamos_producto_ok() throws Exception {
		
		producto1.setCodigo(null);
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		mockMvc.perform(put("/productos/10").content(requestBody).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		producto1.setCodigo(10L);
		
		verify(productoServices, times(1)).update(producto1);
		
	}
	
	@Test
	void actualizamos_producto_NO_exitente() throws Exception {
		
		doThrow(new IllegalStateException()).when(productoServices).update(producto1);
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		MvcResult mvcResult = mockMvc.perform(put("/productos/10").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		
		verify(productoServices, times(1)).update(producto1);
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String mensajeError = objectMapper.writeValueAsString(new ErrorHttpCustomizado("No se encuentra el producto 10"));
		
		assertEquals(mensajeError, strBodyRespuesta);
		
	}
	
	@Test
	void actualizamos_producto_erroneo() throws Exception{
		
		doThrow(new ArithmeticException("XXXX")).when(productoServices).update(producto1);
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		MvcResult mvcResult = mockMvc.perform(put("/productos/10").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		
		verify(productoServices, times(1)).update(producto1);
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String mensajeError = objectMapper.writeValueAsString(new ErrorHttpCustomizado("Ha habido un problema en la petición."));
		
		assertEquals(mensajeError, strBodyRespuesta);
		
	}

	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
		
	private void initObjects() {
			
		producto1 = new Producto();
		producto2 = new Producto();
			
		producto1.setCodigo(10L);
		producto2.setCodigo(20L);
		
		producto1.setPrecio(10);
		producto2.setPrecio(25);

	}
	
}
