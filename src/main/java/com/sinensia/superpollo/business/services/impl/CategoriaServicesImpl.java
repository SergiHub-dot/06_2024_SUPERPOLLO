package com.sinensia.superpollo.business.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;

public class CategoriaServicesImpl implements CategoriaServices {

	private final Map<Long, Categoria> BASE_DATOS_CATEGORIAS = new HashMap<>();
	
	public CategoriaServicesImpl() {
		initObjects();
	}
	
	@Override
	public Long create(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Categoria> read(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Categoria> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		Categoria categoria1 = new Categoria();
		Categoria categoria2 = new Categoria();
		Categoria categoria3 = new Categoria();
		Categoria categoria4 = new Categoria();
		
		categoria1.setId(100L);
		categoria1.setNombre("TAPAS");
		
		categoria2.setId(101L);
		categoria2.setNombre("REFRESCOS");
		
		categoria3.setId(102L);
		categoria3.setNombre("INFUSIONES");
		
		categoria4.setId(103L);
		categoria4.setNombre("BOLLERIA");
		
		BASE_DATOS_CATEGORIAS.put(categoria1.getId(), categoria1);
		BASE_DATOS_CATEGORIAS.put(categoria2.getId(), categoria2);
		BASE_DATOS_CATEGORIAS.put(categoria3.getId(), categoria3);
		BASE_DATOS_CATEGORIAS.put(categoria4.getId(), categoria4);
	}

}
