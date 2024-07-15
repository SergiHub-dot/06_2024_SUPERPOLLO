package com.sinensia.superpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.integration.model.CategoriaPL;
import com.sinensia.superpollo.integration.model.ProductoPL;

public interface ProductoPLRepository extends JpaRepository<ProductoPL, Long> {

	List<ProductoPL> findByPrecioBetween(double min, double max);
	
	List<ProductoPL> findByFechaAltaBetween(Date desde, Date hasta);

	List<ProductoPL> findByDescatalogadoTrue();
	
	List<ProductoPL> findByCategoria(CategoriaPL categoria);
	
	List<ProductoPL> findByCategoriaId(Long idCategoria);
	
	@Query("UPDATE ProductoPL p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p IN :productos")
	@Modifying
	void variarPrecio(List<ProductoPL> productos, double porcentaje);
	
	@Query("UPDATE ProductoPL p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p.codigo IN :codigosProducto")
	@Modifying
	void variarPrecio(long[] codigosProducto, double porcentaje);

	@Query("SELECT c, COUNT(p) FROM CategoriaPL c LEFT JOIN ProductoPL p ON p.categoria = c GROUP BY c")
	List<Object[]> getEstadisticaNumeroProductoCategoria();
	
	@Query("SELECT c, ROUND(AVG(p.precio),2) FROM CategoriaPL c LEFT JOIN ProductoPL p ON p.categoria = c GROUP BY c")
	List<Object[]> getEstadisticaPrecioMedioProductoCategoria();

	// ************************** CREACION DE DTOS ************************************************
	
	@Query("SELECT UPPER(CONCAT(p.nombre, ' [', p.codigo, ']')), p.precio, p.categoria.nombre FROM ProductoPL p")
	List<Object[]> findProducto1DTOs(); 
	
}
