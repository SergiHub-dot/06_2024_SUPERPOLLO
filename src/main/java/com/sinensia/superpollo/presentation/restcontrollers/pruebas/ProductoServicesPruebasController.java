package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.dtos.Producto1DTO;
import com.sinensia.superpollo.business.services.ProductoServices;

@RestController
@RequestMapping("/pruebas")
public class ProductoServicesPruebasController {

	@Autowired
	private ProductoServices productoServices;
	
	@GetMapping("/productodto1")
	public List<Producto1DTO> getProducto1DTOs(){
		return productoServices.getProducto1DTOs();
	}
}
