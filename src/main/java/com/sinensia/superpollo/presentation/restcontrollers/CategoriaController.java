package com.sinensia.superpollo.presentation.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	@Qualifier("categoriaServicesImpl")
	private CategoriaServices categoriaServices;
	
	@GetMapping
	public List<Categoria> getCategorias(){
		return categoriaServices.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		
		Optional<Categoria> optional = categoriaServices.read(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorHttpCustomizado("No se encuentra la categoria " + id), HttpStatus.NOT_FOUND);
		} 
	
		return ResponseEntity.of(optional);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Categoria categoria, UriComponentsBuilder ucb) {
		
		Long id = null;
		
		try {
		
			id = categoriaServices.create(categoria);
		
		} catch(IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorHttpCustomizado(e.getMessage()));
		}
		
		URI uri = ucb.path("/categorias/{id}").build(id);
		
		return ResponseEntity.created(uri).build();
	}
}
