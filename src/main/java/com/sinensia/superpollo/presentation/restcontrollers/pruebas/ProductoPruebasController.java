package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.integration.repositories.ProductoRepository;

@RestController
@RequestMapping("/pruebas")
public class ProductoPruebasController {

	@Autowired
	private ProductoRepository productoRepository;

	@GetMapping("/1")
	public List<Object[]> dameAlgo() {
		return productoRepository.getEstadisticaNumeroProductoCategoria();
	}
}
