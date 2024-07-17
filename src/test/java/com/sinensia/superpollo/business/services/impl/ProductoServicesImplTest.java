package com.sinensia.superpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.model.dtos.Producto1DTO;
import com.sinensia.superpollo.integration.model.CategoriaPL;
import com.sinensia.superpollo.integration.model.ProductoPL;
import com.sinensia.superpollo.integration.repositories.ProductoPLRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServicesImplTest {

	@Mock
	private ProductoPLRepository productoPLRepository;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@InjectMocks
	private ProductoServicesImpl productoServicesImpl;
	
	private ProductoPL productoPL1;
	private ProductoPL productoPL2;
	private Producto producto1;
	private Producto producto2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void create_producto_ok() {
		
		producto1.setCodigo(null);
		
		when(mapper.map(producto1, ProductoPL.class)).thenReturn(productoPL1);
		when(productoPLRepository.save(productoPL1)).thenReturn(productoPL2);
		
		Long codigo = productoServicesImpl.create(producto1);
		
		assertEquals(200L, codigo);
		
	}
	
	@Test
	void create_producto_con_id_NO_null() {
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			productoServicesImpl.create(producto1);
		});
		
		assertEquals("Para crear un producto el código ha de ser null", exception.getMessage());
	}
	
	@Test
	void read_existente() {
	
		when(productoPLRepository.findById(100L)).thenReturn(Optional.of(productoPL1));
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		
		Optional<Producto> optional = productoServicesImpl.read(100L);
		
		assertTrue(optional.isPresent());
		assertEquals(100L, optional.get().getCodigo());	
	}
	
	@Test
	void read_no_existente() {
		
		when(productoPLRepository.findById(587L)).thenReturn(Optional.empty());
		
		Optional<Producto> optional = productoServicesImpl.read(587L);
		
		assertTrue(optional.isEmpty());
	}

	@Test
	void update_ok() {
		
		when(productoPLRepository.existsById(100L)).thenReturn(true);
		when(mapper.map(producto1, ProductoPL.class)).thenReturn(productoPL1);
		
		productoServicesImpl.update(producto1);
		
		verify(productoPLRepository, times(1)).save(productoPL1);
	}
	
	@Test
	void update_NO_existente() {
		
		when(productoPLRepository.existsById(100L)).thenReturn(false);
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			productoServicesImpl.update(producto1);
		});
		
		assertEquals("El producto 100 no existe. No se puede actualizar.", exception.getMessage());
	}
	
	@Test
	void delete_ok() {
		
		when(productoPLRepository.existsById(200L)).thenReturn(true);
		
		productoServicesImpl.delete(200L);
		
		verify(productoPLRepository, times(1)).deleteById(200L);
		
	}
	
	@Test
	void delete_NO_existente() {
		
		when(productoPLRepository.existsById(200L)).thenReturn(false);
		
		Exception exception = assertThrows(IllegalStateException.class, ()->{
			productoServicesImpl.delete(200L);
		});
		
		assertEquals("No existe el producto con código 200", exception.getMessage());
	
	}
	
	@Test
	void getAllTest() {
		
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		when(productoPLRepository.findAll()).thenReturn(productosPL);
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		when(mapper.map(productoPL2, Producto.class)).thenReturn(producto2);
		
		List<Producto> productos = productoServicesImpl.getAll();
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(Arrays.asList(producto1, producto2)));
	}
	
	@Test
	void getBetweenPriceRangeTest() {
		
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		when(productoPLRepository.findByPrecioBetween(10.0, 100.0)).thenReturn(productosPL);
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		when(mapper.map(productoPL2, Producto.class)).thenReturn(producto2);
		
		List<Producto> productos = productoServicesImpl.getBetweenPriceRange(10.0, 100.0);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(Arrays.asList(producto1, producto2)));
	}
	
	@Test
	void getBetweenDatesTest() {
		
		Date desde = new Date(1000000000L);
		Date hasta = new Date(2000000000L);
		
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		when(productoPLRepository.findByFechaAltaBetween(desde, hasta)).thenReturn(productosPL);
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		when(mapper.map(productoPL2, Producto.class)).thenReturn(producto2);
		
		List<Producto> productos = productoServicesImpl.getBetweenDates(desde, hasta);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(Arrays.asList(producto1, producto2)));
		
	}
	
	@Test
	void getDescatalogadosTest() {
		
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		when(productoPLRepository.findByDescatalogadoTrue()).thenReturn(productosPL);
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		when(mapper.map(productoPL2, Producto.class)).thenReturn(producto2);
		
		List<Producto> productos = productoServicesImpl.getDescatalogados();
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(Arrays.asList(producto1, producto2)));
		
	}
	
	@Test
	void getByCategoriaTest() {
		
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		Categoria categoria = new Categoria();
		categoria.setId(1L);
		
		CategoriaPL categoriaPL = new CategoriaPL();
		categoriaPL.setId(1L);
		
		when(productoPLRepository.findByCategoria(categoriaPL)).thenReturn(productosPL);
		when(mapper.map(categoria, CategoriaPL.class)).thenReturn(categoriaPL);
		when(mapper.map(productoPL1, Producto.class)).thenReturn(producto1);
		when(mapper.map(productoPL2, Producto.class)).thenReturn(producto2);
		
		List<Producto> productos = productoServicesImpl.getByCategoria(categoria);
		
		assertEquals(2, productos.size());
		assertTrue(productos.containsAll(Arrays.asList(producto1, producto2)));
	}
	
	@Test
	void getNumeroTotalProductosTest() {
		
		when(productoPLRepository.count()).thenReturn(277L);
		
		int numeroTotalProductos = productoServicesImpl.getNumeroTotalProductos();
		
		assertEquals(277, numeroTotalProductos);
	}
	
	@Test
	void variarPrecio1Test() {
		
		List<Producto> productos = Arrays.asList(producto1, producto2);
		List<ProductoPL> productosPL = Arrays.asList(productoPL1, productoPL2);
		
		when(mapper.map(producto1, ProductoPL.class)).thenReturn(productoPL1);
		when(mapper.map(producto2, ProductoPL.class)).thenReturn(productoPL2);
		
		productoServicesImpl.variarPrecio(productos, 5.0);
		
		verify(productoPLRepository, times(1)).variarPrecio(productosPL, 5.0);
	}
	
	@Test
	void variarPrecio2Test() {
		
		long[] codigos = {100L, 200L, 300L};
		
		productoServicesImpl.variarPrecio(codigos, 5.0);
		
		verify(productoPLRepository, times(1)).variarPrecio(codigos, 5.0);
	}
	
	@Test
	void getEstadisticaNumeroProductosPorCategoriaTest() {
		
		CategoriaPL categoria1PL = new CategoriaPL();
		CategoriaPL categoria2PL = new CategoriaPL();
		
		categoria1PL.setId(1L);
		categoria2PL.setId(2L);
		
		Categoria categoria1 = new Categoria();
		Categoria categoria2 = new Categoria();
		
		categoria1.setId(1L);
		categoria2.setId(2L);
		
		Object[] fila1 = {categoria1PL, 5L};
		Object[] fila2 = {categoria2PL, 7L};
		
		List<Object[]> tablaResultados = Arrays.asList(fila1, fila2);
		
		when(productoPLRepository.getEstadisticaNumeroProductoCategoria()).thenReturn(tablaResultados);
		when(mapper.map(categoria1PL, Categoria.class)).thenReturn(categoria1);
		when(mapper.map(categoria2PL, Categoria.class)).thenReturn(categoria2);
		
		Map<Categoria, Integer> estadistica = productoServicesImpl.getEstadisticaNumeroProductosPorCategoria();
		
		assertEquals(2, estadistica.size());
		assertEquals(5, estadistica.get(categoria1));
		assertEquals(7, estadistica.get(categoria2));
		
	}
	
	@Test
	void getEstadisticaPrecioMedioProductosPorCategoriaTest() {
		
		CategoriaPL categoria1PL = new CategoriaPL();
		CategoriaPL categoria2PL = new CategoriaPL();
		
		categoria1PL.setId(1L);
		categoria2PL.setId(2L);
		
		Categoria categoria1 = new Categoria();
		Categoria categoria2 = new Categoria();
		
		categoria1.setId(1L);
		categoria2.setId(2L);
		
		Object[] fila1 = {categoria1PL, 5.6};
		Object[] fila2 = {categoria2PL, 10.8};
		
		List<Object[]> tablaResultados = Arrays.asList(fila1, fila2);
		
		when(productoPLRepository.getEstadisticaPrecioMedioProductoCategoria()).thenReturn(tablaResultados);
		when(mapper.map(categoria1PL, Categoria.class)).thenReturn(categoria1);
		when(mapper.map(categoria2PL, Categoria.class)).thenReturn(categoria2);
		
		Map<Categoria, Double> estadistica = productoServicesImpl.getEstadisticaPrecioMedioProductosPorCategoria();
		
		assertEquals(2, estadistica.size());
		assertEquals(5.6, estadistica.get(categoria1));
		assertEquals(10.8, estadistica.get(categoria2));
	}
	
	@Test
	void getProducto1DTOsTest() {
		
		Object[] fila1 = {"Producto_1", 5.6, "Categoria_A"};
		Object[] fila2 = {"Producto_2", 10.8, "Categoria_B"};
	
		List<Object[]> tablaResultados = Arrays.asList(fila1, fila2);
		
		when(productoPLRepository.findProducto1DTOs()).thenReturn(tablaResultados);
		
		List<Producto1DTO> productos1DTO = productoServicesImpl.getProducto1DTOs();
		
		Set<String> resultadosAsString = new HashSet<>();
		
		for(Producto1DTO producto1DTO: productos1DTO) {
			resultadosAsString.add(producto1DTO.getNombre() + "_" + producto1DTO.getPrecio() + "_" + producto1DTO.getCategoria());
		}
		
		assertEquals(2, productos1DTO.size());
		assertTrue(resultadosAsString.contains("Producto_1_5.6_Categoria_A"));
		assertTrue(resultadosAsString.contains("Producto_2_10.8_Categoria_B"));
		
	}
	
	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		productoPL1 = new ProductoPL();
		productoPL2 = new ProductoPL();
		
		productoPL1.setCodigo(100L);
		productoPL2.setCodigo(200L);
		
		producto1 = new Producto();
		producto2 = new Producto();
		
		producto1.setCodigo(100L);
		producto2.setCodigo(200L);
		
	}
	
}
