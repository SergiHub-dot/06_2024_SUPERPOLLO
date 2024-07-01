package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.integration.repositories.CategoriaRepository;

@RestController
@RequestMapping("/pruebas")
public class CategoriaPruebasController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("/categorias")
	public List<Categoria> getAll(){
		return categoriaRepository.findAll();
	}
}
