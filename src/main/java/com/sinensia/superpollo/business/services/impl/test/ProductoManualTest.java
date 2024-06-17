package com.sinensia.superpollo.business.services.impl.test;

import java.util.List;
import java.util.Map;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.business.services.impl.ProductoServicesImpl;

public class ProductoManualTest {

	public static void main(String[] args) {
		
		ProductoServices productoServices = new ProductoServicesImpl();
		
		List<Producto> productos = productoServices.getAll();
		
		for(Producto producto: productos) {
			System.out.println(producto);
		}
		
		System.out.println("\n**********************************************\n");
		
		Map<Categoria, Integer> numeroProductosPorCategoria = productoServices.getEstadisticaNumeroProductosPorCategoria();
		
		for(Categoria categoria: numeroProductosPorCategoria.keySet()) {
			System.out.println(categoria.getNombre() + ": " + numeroProductosPorCategoria.get(categoria));
		}

	}

}
