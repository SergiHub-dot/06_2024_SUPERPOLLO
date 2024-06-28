package com.sinensia.superpollo.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;
import com.sinensia.superpollo.presentation.config.PresentationException;

@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

	@Autowired
	private EstablecimientoServices establecimientoServices;
	
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
	
	// **********************************************************
	//
	// Exception Handler
	//
	// **********************************************************
	
	@ExceptionHandler(PresentationException.class)
	public ResponseEntity<?> handlePresentationExceptions(PresentationException e){
		ErrorHttpCustomizado errorHttpCustomizado = new ErrorHttpCustomizado(e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(errorHttpCustomizado);
	}
	
	@ExceptionHandler({IllegalStateException.class, HttpMessageNotReadableException.class})
	public ResponseEntity<?> handleBadRequestExceptions(Exception e){
		return ResponseEntity.badRequest().body(new ErrorHttpCustomizado(e.getMessage()));
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e){
		return ResponseEntity.internalServerError().body(new ErrorHttpCustomizado("ALGO HA IDO MAL"));
	}
}
