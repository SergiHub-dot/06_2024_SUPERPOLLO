package com.sinensia.superpollo.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.integration.model.CategoriaPL;
import com.sinensia.superpollo.integration.repositories.CategoriaPLRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServicesImpl implements CategoriaServices{
	
	private CategoriaPLRepository categoriaPLRepository;
	private DozerBeanMapper mapper;
	
	public CategoriaServicesImpl(CategoriaPLRepository categoriaPLRepository, DozerBeanMapper mapper) {
		this.categoriaPLRepository = categoriaPLRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Categoria categoria) {
		
		if(categoria.getId() != null) {
			throw new IllegalStateException("Para crear una categoría el id ha de ser null");
		}
		
		CategoriaPL categoriaPL = mapper.map(categoria, CategoriaPL.class);
	
		return categoriaPLRepository.save(categoriaPL).getId();
	}

	@Override
	public Optional<Categoria> read(Long id) {
		
		Optional<CategoriaPL> optionalPL = categoriaPLRepository.findById(id);
		
		Categoria categoria = null;
		
		if(optionalPL.isPresent()) {
			categoria = mapper.map(optionalPL.get(), Categoria.class);
		}
	
		return Optional.ofNullable(categoria);
	}

	@Override
	public List<Categoria> getAll() {
		
		List<CategoriaPL> categoriasPL = categoriaPLRepository.findAll();
		
		List<Categoria> categorias = new ArrayList<>();
		
		for(CategoriaPL categoriaPL: categoriasPL) {
			categorias.add(mapper.map(categoriaPL, Categoria.class));
		}
		
		return categorias;
	}

}
