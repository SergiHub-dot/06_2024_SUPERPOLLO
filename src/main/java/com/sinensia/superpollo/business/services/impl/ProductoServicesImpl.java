package com.sinensia.superpollo.business.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;

@Service
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
		
		List<Producto> productos = new ArrayList<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			if(producto.getCategoria().equals(categoria)) {
				productos.add(producto);
			}
		}
		
		return productos;
	}

	@Override
	public int getNumeroTotalProductos() {
		return BASE_DATOS_PRODUCTOS.size();
	}

	@Override
	public void variarPrecio(List<Producto> productos, double porcentaje) {
		
		for(Producto producto: productos) {
			if(!BASE_DATOS_PRODUCTOS.containsKey(producto.getCodigo())) {
				throw new IllegalArgumentException("No existe el producto " + producto.getCodigo());
			}
		}
		
		for(Producto producto: productos) {
			Producto productoGuardado = BASE_DATOS_PRODUCTOS.get(producto.getCodigo());
			double precio = productoGuardado.getPrecio();
			double nuevoPrecio =  precio + (precio * porcentaje / 100);
			productoGuardado.setPrecio(nuevoPrecio);
		}	
	}

	@Override
	public void variarPrecio(long[] codigos, double porcentaje) {
		
		for(Long codigo: codigos) {
			if(!BASE_DATOS_PRODUCTOS.containsKey(codigo)) {
				throw new IllegalArgumentException("No existe el producto " + codigo);
			}
		}
		
		for(Long codigo: codigos) {
			Producto productoGuardado = BASE_DATOS_PRODUCTOS.get(codigo);
			double precio = productoGuardado.getPrecio();
			double nuevoPrecio =  precio + (precio * porcentaje / 100);
			productoGuardado.setPrecio(nuevoPrecio);
		}
	}

	@Override
	public Map<Categoria, Integer> getEstadisticaNumeroProductosPorCategoria() {
		
		Map<Categoria, Integer> resultados = new HashMap<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			
			Categoria categoria = producto.getCategoria();
			
			if(!resultados.containsKey(categoria)) {
				resultados.put(categoria, 1);
			} else {
				resultados.replace(categoria, resultados.get(categoria) + 1);
			}
		}
		
		return resultados;
	}

	@Override
	public Map<Categoria, Double> getEstadisticaPrecioMedioProductosPorCategoria() {
		
		Map<Categoria, Double> resultadosMap = new HashMap<>();
		Map<Categoria, Integer> numeroProductosMap = new HashMap<>();
		
		for(Producto producto: BASE_DATOS_PRODUCTOS.values()) {
			Categoria categoria = producto.getCategoria();
			if(resultadosMap.containsKey(categoria)) {
				double importeAcumulado = resultadosMap.get(categoria);
				int numeroProductos = numeroProductosMap.get(categoria);
				resultadosMap.replace(categoria, importeAcumulado + producto.getPrecio());
				numeroProductosMap.replace(categoria, numeroProductos + 1);
			} else {
				resultadosMap.put(categoria, producto.getPrecio());
				numeroProductosMap.put(categoria, 1);
			}
		}
		
		for(Categoria categoria: resultadosMap.keySet()) {
			double importeAcumulado = resultadosMap.get(categoria);
			int numeroProductos = numeroProductosMap.get(categoria);
			resultadosMap.replace(categoria, importeAcumulado / numeroProductos);
		}
		
		return resultadosMap;
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
		producto1.setDescripcion("Lata de cocacola Zero 33cl");
		producto1.setPrecio(2.5);
		producto1.setDescatalogado(false);
		
		Producto producto2 = new Producto();
		producto2.setCodigo(102L);
		producto2.setCategoria(categoria2);
		producto2.setFechaAlta(fecha2);
		producto2.setNombre("Patas Bravas");
		producto2.setDescripcion("Deliciosas patatas");
		producto2.setPrecio(7.0);
		producto2.setDescatalogado(false);
		
		Producto producto3 = new Producto();
		producto3.setCodigo(103L);
		producto3.setCategoria(categoria2);
		producto3.setFechaAlta(fecha3);
		producto3.setNombre("Patas Bravas Light");
		producto3.setDescripcion("Deliciosas patatas sin salsa");
		producto3.setPrecio(6.0);
		producto3.setDescatalogado(false);
		
		BASE_DATOS_PRODUCTOS.put(producto1.getCodigo(), producto1);
		BASE_DATOS_PRODUCTOS.put(producto2.getCodigo(), producto2);
		BASE_DATOS_PRODUCTOS.put(producto3.getCodigo(), producto3);
		
	}

}
