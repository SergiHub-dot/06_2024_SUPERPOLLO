package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.integration.repositories.EstablecimientoRepository;

@RestController
@RequestMapping("/pruebas")
public class EstablecimientoPruebasController {

	@Autowired
	private EstablecimientoRepository establecimientoRepository;
	
	@GetMapping("/establecimientos")
	public List<Establecimiento> getAll(){
		return establecimientoRepository.findAll();
	}
}
