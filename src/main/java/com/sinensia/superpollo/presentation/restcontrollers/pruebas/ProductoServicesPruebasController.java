package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Categoria;
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
	
	@GetMapping("/2")
	public List<Producto> prueba2() throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date desde = sdf.parse("20/10/2017");
		Date hasta = sdf.parse("25/10/2017");
		
	
		return productoServices.getBetweenDates(desde, hasta);
	}
	
	@GetMapping("/3")
	public List<Producto> prueba3(){
		
		return productoServices.getDescatalogados();
	}
	
	@GetMapping("/4")
	public List<Producto> prueba4(){
		
		Categoria categoria = new Categoria();
		categoria.setId(1L);
		
		return productoServices.getByCategoria(categoria);
	}
}
