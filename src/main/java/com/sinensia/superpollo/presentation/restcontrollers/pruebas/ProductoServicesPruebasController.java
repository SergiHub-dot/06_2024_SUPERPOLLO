package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.ProductoServices;

@RestController
@RequestMapping("/pruebas/services")
public class ProductoServicesPruebasController {

	@Autowired
	private ProductoServices productoServices;

	@GetMapping("/1")
	public Map<Categoria, Integer> getEstadisticaNumeroProductosCategoria() {
		return productoServices.getEstadisticaNumeroProductosPorCategoria();
	}
}
