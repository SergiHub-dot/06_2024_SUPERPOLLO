package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;

@RestController
@RequestMapping("/pruebas")
public class ProductoServicesPruebasController {

	@Autowired
	private ProductoServices productoServices;
	
	@GetMapping("/1")
	public List<Producto> prueba1(){
		
		double min = 4.5;
		double max = 5.0;
		
		return productoServices.getBetweenPriceRange(min, max);
	}
	
	// TODO getBetweenDates
	
	// TODO getDescatalogados
	
	// TODO getByCategoria      Truco -> Poderis instanciar una categoria y setearle un ID existente. 
	
	
}
