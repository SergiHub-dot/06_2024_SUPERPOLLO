package com.sinensia.superpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByPrecioBetween(double min, double max);
	
	List<Producto> findByFechaAltaBetween(Date desde, Date hasta);

	List<Producto> findByDescatalogadoTrue();
	
	List<Producto> findByCategoria(Categoria categoria);
	
	List<Producto> findByCategoriaId(Long idCategoria);
	
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p IN :productos")
	@Modifying
	void variarPrecio(List<Producto> productos, double porcentaje);
	
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p.codigo IN :codigosProducto")
	@Modifying
	void variarPrecio(long[] codigosProducto, double porcentaje);

	@Query("SELECT c, COUNT(p) FROM Categoria c LEFT JOIN Producto p ON p.categoria = c GROUP BY c")
	List<Object[]> getEstadisticaNumeroProductoCategoria();
	
	@Query("SELECT c, ROUND(AVG(p.precio),2) FROM Categoria c LEFT JOIN Producto p ON p.categoria = c GROUP BY c")
	List<Object[]> getEstadisticaPrecioMedioProductoCategoria();

	// ************************** CREACION DE DTOS ************************************************
	
	@Query("SELECT UPPER(CONCAT(p.nombre, ' [', p.codigo, ']')), p.precio, p.categoria.nombre FROM Producto p")
	List<Object[]> findProducto1DTOs(); 
	
}
