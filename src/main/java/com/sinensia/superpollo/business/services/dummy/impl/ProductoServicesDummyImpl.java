package com.sinensia.superpollo.business.services.dummy.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.model.dtos.Producto1DTO;
import com.sinensia.superpollo.business.services.ProductoServices;

@Service
@Primary
public class ProductoServicesDummyImpl implements ProductoServices {
	
	private final Map<Long, Producto> BASE_DATOS_PRODUCTOS = new HashMap<>();
	
	public ProductoServicesDummyImpl() {
		initObjects();
	}
	
	@Override
	public Long create(Producto producto) {
		
		if(producto.getCodigo() != null) {
			throw new IllegalStateException("El producto " + producto.getNombre() +" ya tiene código. No se puede crear.");
		}
		Long maximoCodigo = 0L;
		
		for(Long clave: BASE_DATOS_PRODUCTOS.keySet()) {
			if (clave > maximoCodigo) {
				maximoCodigo = clave;
			}
		}
		
		Long nuevoCodigo = maximoCodigo + 1;
		
		producto.setCodigo(nuevoCodigo);
		
		BASE_DATOS_PRODUCTOS.put(producto.getCodigo(), producto);
		
		return nuevoCodigo;
	}
	
	@Override
	public Optional<Producto> read(Long codigo) {
		return Optional.ofNullable(BASE_DATOS_PRODUCTOS.get(codigo));
	}
	
	@Override
	public void update(Producto producto) {
		
		Long codigo = producto.getCodigo();
		
		boolean existe = BASE_DATOS_PRODUCTOS.containsKey(codigo);
		
		if(!existe) {
			throw new IllegalStateException("No existe el producto con código " + codigo);
		}
		
		BASE_DATOS_PRODUCTOS.replace(codigo, producto);
			
	}
	
	@Override
	public void delete(Long codigo) {
				
		boolean existe = BASE_DATOS_PRODUCTOS.containsKey(codigo);
		
		if(!existe) {
			throw new IllegalStateException("No existe el producto con código " + codigo);
		}
		
		BASE_DATOS_PRODUCTOS.remove(codigo);
		
	}
	
	@Override
	public List<Producto> getAll() {
		return new ArrayList<>(BASE_DATOS_PRODUCTOS.values());
	}
	
	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		// TODO
		return null;
	}
	
	@Override
	public List<Producto> getBetweenDates(Date desde, Date hasta) {
		// TODO
		return null;
	}
	
	@Override
	public List<Producto> getDescatalogados() {
		// TODO
		return null;
	}
	
	@Override
	public List<Producto> getByCategoria(Categoria categoria) {
		// TODO
		return null;
	}
	
	@Override
	public int getNumeroTotalProductos() {
		return BASE_DATOS_PRODUCTOS.size();
	}
	
	@Override
	public void variarPrecio(List<Producto> productos, double porcentaje) {
		
		// forEach() !
		
		// TODO
	}
	
	@Override
	public void variarPrecio(long[] codigos, double porcentaje) {
		
		// forEach() !
		
		// TODO
	}
	
	@Override
	public Map<Categoria, Integer> getEstadisticaNumeroProductosPorCategoria() {
		// TODO
		return null;
	}

	@Override
	public Map<Categoria, Double> getEstadisticaPrecioMedioProductosPorCategoria() {
		// TODO
		return null;
	}

	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************	
	
	private void initObjects() {
		
		Categoria categoria1 = new Categoria();
		Categoria categoria2 = new Categoria();
		
		categoria1.setId(101L);
		categoria1.setNombre("REFRESCO");
		
		categoria2.setId(102L);
		categoria2.setNombre("TAPA");
		
		Date fecha1 = null;
		Date fecha2 = null;
		Date fecha3 = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			fecha1 = sdf.parse("24/08/2016");
			fecha2 = sdf.parse("26/08/2016");
			fecha3 = sdf.parse("27/08/2016");
		} catch (ParseException e) {
			
		}
		
		Producto producto1 = new Producto();
		producto1.setCodigo(101L);
		producto1.setCategoria(categoria1);
		producto1.setFechaAlta(fecha1);
		producto1.setNombre("Cocacola Zero");
		producto1.setDescripcion("Lata de cocacola Dummy 33cl");
		producto1.setPrecio(2.5);
		producto1.setDescatalogado(false);
		
		Producto producto2 = new Producto();
		producto2.setCodigo(102L);
		producto2.setCategoria(categoria2);
		producto2.setFechaAlta(fecha2);
		producto2.setNombre("Patas Bravas");
		producto2.setDescripcion("Deliciosas patatas Dummy");
		producto2.setPrecio(7.0);
		producto2.setDescatalogado(false);
		
		Producto producto3 = new Producto();
		producto3.setCodigo(103L);
		producto3.setCategoria(categoria2);
		producto3.setFechaAlta(fecha3);
		producto3.setNombre("Patas Bravas Light");
		producto3.setDescripcion("Deliciosas patatas sin salsa Dummy");
		producto3.setPrecio(6.0);
		producto3.setDescatalogado(false);
		
		BASE_DATOS_PRODUCTOS.put(producto1.getCodigo(), producto1);
		BASE_DATOS_PRODUCTOS.put(producto2.getCodigo(), producto2);
		BASE_DATOS_PRODUCTOS.put(producto3.getCodigo(), producto3);
		
	}

	@Override
	public List<Producto1DTO> getProducto1DTOs() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
