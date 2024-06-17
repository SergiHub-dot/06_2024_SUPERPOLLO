package com.sinensia.superpollo.business.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;

public class ProductoServicesImpl implements ProductoServices {
	
	private final Map<Long, Producto> BASE_DATOS_PRODUCTOS = new HashMap<>();
	
	public ProductoServicesImpl() {
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

		List<Producto> productos = new ArrayList<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			if(producto.getPrecio() >= min && producto.getPrecio() <= max) {
				productos.add(producto);
			}
		}
		
		return productos;
	}

	@Override
	public List<Producto> getBetweenDates(Date desde, Date hasta) {

		List<Producto> productos = new ArrayList<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			if(producto.getFechaAlta().compareTo(desde) >= 0 && producto.getFechaAlta().compareTo(hasta) <= 0) {
				productos.add(producto);
			}
		}
		
		return productos;
	}

	@Override
	public List<Producto> getDescatalogados() {
	
		List<Producto> productos = new ArrayList<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			if(producto.isDescatalogado()) {
				productos.add(producto);
			}
		}
		
		return productos;
	}

	@Override
	public List<Producto> getByCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroTotalProductos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void variarPrecio(List<Producto> productos, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void variarPrecio(long[] codigos, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Categoria, Integer> getEstadisticaNumeroProductosPorCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Categoria, Double> getEstadisticaPrecioMedioProductosPorCategoria() {
		// TODO Auto-generated method stub
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			fecha1 = sdf.parse("24/08/2016");
			fecha2 = sdf.parse("26/08/2016");
		} catch (ParseException e) {
			
		}
		
		Producto producto1 = new Producto();
		producto1.setCategoria(categoria1);
		producto1.setFechaAlta(fecha1);
		producto1.setNombre("Cocacola Zero");
		producto1.setDescripcion("Lata de cocacola Zero 33cl");
		producto1.setPrecio(2.5);
		producto1.setDescatalogado(false);
		
		Producto producto2 = new Producto();
		producto2.setCategoria(categoria2);
		producto2.setFechaAlta(fecha2);
		producto2.setNombre("Patas Bravas");
		producto2.setDescripcion("Deliciosas patatas");
		producto2.setPrecio(7.0);
		producto2.setDescatalogado(false);
		
		BASE_DATOS_PRODUCTOS.put(producto1.getCodigo(), producto1);
		BASE_DATOS_PRODUCTOS.put(producto2.getCodigo(), producto2);
		
	}

}
