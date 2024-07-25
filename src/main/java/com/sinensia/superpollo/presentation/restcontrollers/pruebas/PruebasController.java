package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;

@RestController
@RequestMapping("/pruebas")
public class PruebasController {

	@Autowired
	@Qualifier("productoServicesDummyImpl")
	private ProductoServices productoServices;
	
	
	@GetMapping("/all")
	public List<Producto> getAll(){
		return productoServices.getAll();
	}
	
	@GetMapping("/descatalogados")
	public List<Producto> getDescatalogados(){
		return productoServices.getDescatalogados();
	}
	
	@GetMapping("/cambiarprecios")
	public String cambiarPrecios(){
		
		Producto p1 = new Producto();
		Producto p2 = new Producto();
		
		p1.setCodigo(102L);
		p2.setCodigo(103L);
		
		List<Producto> productos = Arrays.asList(p1, p2);
		
		productoServices.variarPrecio(productos, 50.0);
		
		return "ok";
	}
}
