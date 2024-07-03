package com.sinensia.superpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.integration.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServicesImpl implements CategoriaServices{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	@Transactional
	public Long create(Categoria categoria) {
		
		if(categoria.getId() != null) {
			throw new IllegalStateException("La categoría " + categoria.getNombre() +" ya tiene id. No se puede crear.");
		}
		
		return null;
	}

	@Override
	public Optional<Categoria> read(Long id) {
		return categoriaRepository.findById(id);
	}

	@Override
	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}

}
