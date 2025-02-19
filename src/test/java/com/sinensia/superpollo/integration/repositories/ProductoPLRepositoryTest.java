package com.sinensia.superpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.superpollo.integration.model.CategoriaPL;
import com.sinensia.superpollo.integration.model.ProductoPL;

@DataJpaTest
@Sql(scripts={"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class ProductoPLRepositoryTest {

	@Autowired
	private ProductoPLRepository productoPLRepository;
	
	@Test
	void findByPrecioBetweenTest() {
		
		ProductoPL producto1 = new ProductoPL();
		ProductoPL producto2 = new ProductoPL();
		ProductoPL producto3 = new ProductoPL();
		ProductoPL producto4 = new ProductoPL();
		ProductoPL producto5 = new ProductoPL();
		
		producto1.setCodigo(127L);
		producto2.setCodigo(139L);
		producto3.setCodigo(159L);
		producto4.setCodigo(160L);
		producto5.setCodigo(138L);
		
		List<ProductoPL> productosEsperados = Arrays.asList(producto1, producto2, producto3, producto4, producto5);
		
		List<ProductoPL> productos = productoPLRepository.findByPrecioBetween(1.9, 2.3);
		
		assertEquals(5, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void findByFechaAltaBetweenTest() {
		
		ProductoPL producto1 = new ProductoPL();
		ProductoPL producto2 = new ProductoPL();
		ProductoPL producto3 = new ProductoPL();
		ProductoPL producto4 = new ProductoPL();
		ProductoPL producto5 = new ProductoPL();
		ProductoPL producto6 = new ProductoPL();

		producto1.setCodigo(162L);
		producto2.setCodigo(161L);
		producto3.setCodigo(113L);
		producto4.setCodigo(112L);
		producto5.setCodigo(163L);
		producto6.setCodigo(114L);

		List<ProductoPL> productosEsperados = Arrays.asList(producto1, producto2, producto3, producto4, producto5, producto6);
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		Date hasta = null;
		Date desde = null;
		
		try {
			desde = formato.parse("2017-10-12");
			hasta = formato.parse("2017-10-14");

		} catch (Exception e) {

		}

		List<ProductoPL> productos = productoPLRepository.findByFechaAltaBetween(desde, hasta);

		assertEquals(6, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void findByDescatalogadoTrueTest() {

		ProductoPL producto1 = new ProductoPL();
		
		producto1.setCodigo(136L);
		
		List<ProductoPL> productos = productoPLRepository.findByDescatalogadoTrue();
		
		assertEquals(1, productos.size());
		assertTrue(productos.contains(producto1));
	}
	
	@Test
	void findByCategoriaTest() {
		
		CategoriaPL categoria1 = new CategoriaPL();
		categoria1.setId(3L);
		
		ProductoPL producto1 = new ProductoPL();
		ProductoPL producto2 = new ProductoPL();
		
		producto1.setCodigo(102L);
		producto2.setCodigo(103L);
		
		List<ProductoPL> productosEsperados = Arrays.asList(producto1, producto2);
		
		List<ProductoPL> productos = productoPLRepository.findByCategoria(categoria1);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void findByCategoriaIdTest() {
		
		ProductoPL producto1 = new ProductoPL();
		ProductoPL producto2 = new ProductoPL();
		
		producto1.setCodigo(102L);
		producto2.setCodigo(103L);
		
		List<ProductoPL> productosEsperados = Arrays.asList(producto1, producto2);
		
		List<ProductoPL> productos = productoPLRepository.findByCategoriaId(3L);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void variarPrecio1Test() {
		
		ProductoPL producto1 = new ProductoPL();
		ProductoPL producto2 = new ProductoPL();
		
		producto1.setCodigo(100L);
		producto2.setCodigo(103L);
		
		List<ProductoPL> productos = Arrays.asList(producto1, producto2);
		
		productoPLRepository.variarPrecio(productos, 50.0);

		producto1 = null;
		producto2 = null;
		
		producto1 = productoPLRepository.findById(100L).get();
		producto2 = productoPLRepository.findById(103L).get();
		ProductoPL producto3 = productoPLRepository.findById(102L).get();
		
		assertEquals(9.0, producto1.getPrecio());
		assertEquals(4.5, producto2.getPrecio());
		assertEquals(3.0, producto3.getPrecio());
		
	}
	
	@Test
	void variarPrecio2Test() {
		
		long[] codigos = {100L, 103L};

		productoPLRepository.variarPrecio(codigos, 50.0);

		ProductoPL producto1 = productoPLRepository.findById(100L).get();
		ProductoPL producto2 = productoPLRepository.findById(103L).get();
		ProductoPL producto3 = productoPLRepository.findById(102L).get();
		
		assertEquals(9.0, producto1.getPrecio());
		assertEquals(4.5, producto2.getPrecio());
		assertEquals(3.0, producto3.getPrecio());
		
	}
	
	@Test
	void getEstadisticaNumeroProductoCategoriaTest() {
		
		Map<Long, Long> resultadosEsperados = new HashMap<>();
		
		resultadosEsperados.put(1L, 6L);
		resultadosEsperados.put(2L, 11L);
		resultadosEsperados.put(3L, 2L);
		resultadosEsperados.put(4L, 6L);
		resultadosEsperados.put(5L, 12L);
		resultadosEsperados.put(6L, 0L);
		resultadosEsperados.put(7L, 3L);
		resultadosEsperados.put(8L, 5L);
		resultadosEsperados.put(9L, 11L);
		resultadosEsperados.put(10L, 7L);
		resultadosEsperados.put(11L, 7L);
		resultadosEsperados.put(12L, 0L);
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaNumeroProductoCategoria();
		
		for(Object[] objects: resultados) {
			Long idCategoria = ((CategoriaPL)objects[0]).getId();
			Long cantidad = (Long) objects[1];
			assertEquals(resultadosEsperados.get(idCategoria), cantidad);
		}
		
		assertEquals(12, resultados.size());
	}
	
	@Test
	void getEstadisticaPrecioMedioProductoCategoriaTest() {
		
		Map<Long, Double> resultadosEsperados = new HashMap<>();
		
		resultadosEsperados.put(1L, 4.43);
		resultadosEsperados.put(2L, 1.72);
		resultadosEsperados.put(3L, 3.0);
		resultadosEsperados.put(4L, 5.07);
		resultadosEsperados.put(5L, 12.68);
		resultadosEsperados.put(6L, null);
		resultadosEsperados.put(7L, 1.83);
		resultadosEsperados.put(8L, 2.5);
		resultadosEsperados.put(9L, 7.42);
		resultadosEsperados.put(10L, 1.97);
		resultadosEsperados.put(11L, 2.67);
		resultadosEsperados.put(12L, null);
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaPrecioMedioProductoCategoria();
		
		for(Object[] objects: resultados) {
			Long idCategoria = ((CategoriaPL)objects[0]).getId();
			Double precioMedio = (Double) objects[1];
			assertEquals(resultadosEsperados.get(idCategoria), precioMedio);
		}
		
		assertEquals(12, resultados.size());
	}
	
	// TODO Testear todos los métodos de repositorios que nos devuelven DTOs ( son 5 ) Que no falte ni uno!!!!!
	// OJO!!!!!! SE TIENEN QUE DISTRIBUIR ENLOS DIFERENTES REPOSITORIOS!!!!!
	
}
