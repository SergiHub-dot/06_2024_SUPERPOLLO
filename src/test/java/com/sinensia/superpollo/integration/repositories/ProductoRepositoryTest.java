package com.sinensia.superpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.superpollo.business.model.Categoria;
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
	void findByFechaAltaBetweenTest() {
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		Producto producto3 = new Producto();
		Producto producto4 = new Producto();
		Producto producto5 = new Producto();
		Producto producto6 = new Producto();

		producto1.setCodigo(162L);
		producto2.setCodigo(161L);
		producto3.setCodigo(113L);
		producto4.setCodigo(112L);
		producto5.setCodigo(163L);
		producto6.setCodigo(114L);

		List<Producto> productosEsperados = Arrays.asList(producto1, producto2, producto3, producto4, producto5, producto6);
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		Date hasta = null;
		Date desde = null;
		
		try {
			desde = formato.parse("2017-10-12");
			hasta = formato.parse("2017-10-14");

		} catch (Exception e) {

		}

		List<Producto> productos = productoRepository.findByFechaAltaBetween(desde, hasta);

		assertEquals(6, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void findByDescatalogadoTrueTest() {

		Producto producto1 = new Producto();
		
		producto1.setCodigo(136L);
		
		List<Producto> productos = productoRepository.findByDescatalogadoTrue();
		
		assertEquals(1, productos.size());
		assertTrue(productos.contains(producto1));
	}
	
	@Test
	void findByCategoriaTest() {
		
		Categoria categoria1 = new Categoria();
		categoria1.setId(3L);
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		
		producto1.setCodigo(102L);
		producto2.setCodigo(103L);
		
		List<Producto> productosEsperados = Arrays.asList(producto1, producto2);
		
		List<Producto> productos = productoRepository.findByCategoria(categoria1);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void findByCategoriaIdTest() {
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		
		producto1.setCodigo(102L);
		producto2.setCodigo(103L);
		
		List<Producto> productosEsperados = Arrays.asList(producto1, producto2);
		
		List<Producto> productos = productoRepository.findByCategoriaId(3L);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(productosEsperados));
	}
	
	@Test
	void variarPrecioTest() {
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		
		producto1.setCodigo(100L);
		producto2.setCodigo(103L);
		
		List<Producto> productos = Arrays.asList(producto1, producto2);
		
		productoRepository.variarPrecio(productos, 50.0);

		producto1 = null;
		producto2 = null;
		
		producto1 = productoRepository.findById(100L).get();
		producto2 = productoRepository.findById(103L).get();
		Producto producto3 = productoRepository.findById(102L).get();
		
		assertEquals(9.0, producto1.getPrecio());
		assertEquals(4.5, producto2.getPrecio());
		assertEquals(3.0, producto3.getPrecio());
		
	}
	
}
