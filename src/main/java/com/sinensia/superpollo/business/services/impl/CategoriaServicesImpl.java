package com.sinensia.superpollo.business.services.impl;

import java.util.ArrayList;
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
		
		if(categoria.getId() != null) {
			throw new IllegalStateException("La categoría " + categoria.getNombre() +" ya tiene id. No se puede crear.");
		}

		Long maximoId = 0L;
		
		for(Long clave: BASE_DATOS_CATEGORIAS.keySet()) {
			if (clave > maximoId) {
				maximoId = clave;
			}
		}
		
		Long nuevoId = maximoId + 1;
		
		categoria.setId(nuevoId);
		
		BASE_DATOS_CATEGORIAS.put(categoria.getId(), categoria);
		
		return nuevoId;
	}

	@Override
	public Optional<Categoria> read(Long id) {
		return Optional.ofNullable(BASE_DATOS_CATEGORIAS.get(id));
	}

	@Override
	public List<Categoria> getAll() {
		
		List<Categoria> categorias = new ArrayList<>();
		
		for(Categoria categoria: BASE_DATOS_CATEGORIAS.values()) {
			categorias.add(categoria);
		}
		
		return categorias;
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
		
		categoria1.setId(101L);
		categoria1.setNombre("TAPAS");
		
		categoria2.setId(102L);
		categoria2.setNombre("REFRESCOS");
		
		categoria3.setId(103L);
		categoria3.setNombre("INFUSIONES");
		
		categoria4.setId(104L);
		categoria4.setNombre("BOLLERIA");
		
		BASE_DATOS_CATEGORIAS.put(categoria1.getId(), categoria1);
		BASE_DATOS_CATEGORIAS.put(categoria2.getId(), categoria2);
		BASE_DATOS_CATEGORIAS.put(categoria3.getId(), categoria3);
		BASE_DATOS_CATEGORIAS.put(categoria4.getId(), categoria4);
	}

}
