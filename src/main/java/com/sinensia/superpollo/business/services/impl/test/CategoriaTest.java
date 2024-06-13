package com.sinensia.superpollo.business.services.impl.test;

import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.business.services.impl.CategoriaServicesImpl;

public class CategoriaTest {

	public static void main(String[] args) {
		
		CategoriaServices categoriaServices = new CategoriaServicesImpl();

		// Probamos metodo1
		
		Categoria nuevaCategoria = new Categoria();
		nuevaCategoria.setId(null);
		nuevaCategoria.setNombre("NUEVA CATGORIA!");
		
		Long id = categoriaServices.create(nuevaCategoria);
		
		System.out.println("Nuevo id: " + id);
		
		// ******************************************************************
		
		// Probamos metodo2
		
		Optional<Categoria> optional = categoriaServices.read(100L);
		
		if(optional.isPresent()) {
			System.out.println(optional.get()); 
		}
			
		// ******************************************************************
		
		// Probamos metodo3
		
		List<Categoria> categorias = categoriaServices.getAll();
		
		if(categorias != null) {
			for(Categoria categoria: categorias) {
				System.out.println(categoria);
			}	
		}
		
	}

}
