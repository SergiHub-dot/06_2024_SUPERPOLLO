package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query("SELECT p FROM Producto AS p WHERE p.precio >= :min AND p.precio <= :max")
	List<Producto> getBetweenPrecios(double min, double max);
	
	

	
}
