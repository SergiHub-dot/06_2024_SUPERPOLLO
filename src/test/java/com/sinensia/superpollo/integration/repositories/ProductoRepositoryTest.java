package com.sinensia.superpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.superpollo.business.model.Producto;

@DataJpaTest
@Sql(scripts={"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class ProductoRepositoryTest {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Test
	void findByPrecioBetweenTest() {
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		Producto producto3 = new Producto();
		Producto producto4 = new Producto();
		Producto producto5 = new Producto();
		
		producto1.setCodigo(127L);
		producto2.setCodigo(139L);
		producto3.setCodigo(159L);
		producto4.setCodigo(160L);
		producto5.setCodigo(138L);
		
		List<Producto> productosEsperados = Arrays.asList(producto1, producto2, producto3, producto4, producto5);
		
		List<Producto> productos = productoRepository.findByPrecioBetween(1.9, 2.3);
		
		assertEquals(5, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	@Disabled
	void findByFechaAltaBetweenTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void findByDescatalogadoTrueTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void findByCategoriaTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void findByCategoriaIdTest() {
		fail("Not implemented yet!");
	}
	
}
