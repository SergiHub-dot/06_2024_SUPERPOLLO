package com.sinensia.superpollo.business.services.impl.test;

import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;
import com.sinensia.superpollo.business.services.impl.CategoriaServicesImpl;

public class CategoriaManualTest {

	public static void main(String[] args) {
		
		CategoriaServices categoriaServices = new CategoriaServicesImpl();

		// Probamos metodo1
		
		Categoria nuevaCategoria = new Categoria();
		nuevaCategoria.setId(null);
		nuevaCategoria.setNombre("NUEVA CATEGORIA!");
		
		Long id = categoriaServices.create(nuevaCategoria);
		
		System.out.println("Nuevo id: " + id);
		
		// ******************************************************************
		
		// Probamos metodo2
		
		Optional<Categoria> optional1 = categoriaServices.read(101L);
		
		if(optional1.isPresent()) {
			Categoria categoria = optional1.get();
			System.out.println(categoria); 
		}
		
		Optional<Categoria> optional2 = categoriaServices.read(15L);
		
		if(optional2.isEmpty()) {
			System.out.println("No existe la categoría 15");
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
