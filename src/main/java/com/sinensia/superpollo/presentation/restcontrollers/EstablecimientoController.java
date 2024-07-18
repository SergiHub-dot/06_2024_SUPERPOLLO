package com.sinensia.superpollo.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.presentation.config.PresentationException;

@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

	private EstablecimientoServices establecimientoServices;
	
	public EstablecimientoController(EstablecimientoServices establecimientoServices) {
		this.establecimientoServices = establecimientoServices;
	}
	
	@GetMapping
	public List<Establecimiento> getEstablecimientos(){		
		return establecimientoServices.getAll();
	}
	
	@GetMapping("/{codigo}")
	public Establecimiento getByCodigo(@PathVariable Long codigo) {
		
		Optional<Establecimiento> optional = establecimientoServices.read(codigo);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No se encuentra el establecimiento " + codigo, HttpStatus.NOT_FOUND);
		} 
	
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Establecimiento establecimiento, UriComponentsBuilder ucb) {
		Long codigo = establecimientoServices.create(establecimiento);
		return ResponseEntity.created(ucb.path("/establecimientos/{codigo}").build(codigo)).build();
	}

}
