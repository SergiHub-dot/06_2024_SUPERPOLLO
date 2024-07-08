package com.sinensia.superpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByPrecioBetween(double min, double max);
	
	List<Producto> findByFechaAltaBetween(Date desde, Date hasta);

	List<Producto> findByDescatalogadoTrue();
	
	List<Producto> findByCategoria(Categoria categoria);
	
	List<Producto> findByCategoriaId(Long idCategoria);
	
}
