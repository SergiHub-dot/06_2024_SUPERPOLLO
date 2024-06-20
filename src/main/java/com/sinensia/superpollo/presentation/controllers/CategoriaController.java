package com.sinensia.superpollo.presentation.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;

@RestController
public class CategoriaController {

	@Autowired
	private CategoriaServices categoriaServices;
	
	@GetMapping("/categorias")
	public List<Categoria> getAll(){
		
		List<Categoria> categorias = categoriaServices.getAll();
		
		return categorias;
	}
	
	// GET http://localhost:8080/categorias/23
	
	@GetMapping("/categorias/{id}")
	public Categoria getById(@PathVariable Long id) {
		
		Optional<Categoria> optional = categoriaServices.read(id);
		
		Categoria categoria = null;
		
		if(optional.isPresent()) {
			categoria = optional.get();
		}
		
		return categoria;
	}
	
	@PostMapping("/categorias")
	public Long create(@RequestBody Categoria categoria) {
		
		Long id = categoriaServices.create(categoria);
		
		return id;
	}
}
