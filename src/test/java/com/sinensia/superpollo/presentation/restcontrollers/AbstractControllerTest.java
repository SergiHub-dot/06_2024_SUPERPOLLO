package com.sinensia.superpollo.presentation.restcontrollers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected void checkResponseBody(MvcResult mvcResult, Object object) throws Exception{
		
		String strBodyRespuesta = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		String objetoAsJSON = objectMapper.writeValueAsString(object);
		
		assertEquals(objetoAsJSON, strBodyRespuesta);	
	}
	
}
