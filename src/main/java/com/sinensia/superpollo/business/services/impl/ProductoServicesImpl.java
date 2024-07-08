package com.sinensia.superpollo.business.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.integration.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServicesImpl implements ProductoServices {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getCodigo() != null) {
			throw new IllegalStateException("El código de producto no es null. No se puede crear.");
		}
		
		Producto createdProducto = productoRepository.save(producto);
		
		return createdProducto.getCodigo();
	}

	@Override
	public Optional<Producto> read(Long codigo) {
		return productoRepository.findById(codigo);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		boolean existe = productoRepository.existsById(producto.getCodigo());
		
		if(!existe) {
			throw new IllegalStateException("El producto " + producto.getCodigo() + " no existe. No se puede actualizar.");
		}
		
		productoRepository.save(producto);
		
	}

	@Override
	@Transactional
	public void delete(Long codigo) {
		
		boolean existe = productoRepository.existsById(codigo);
		
		if(!existe) {
			throw new IllegalStateException("No existe el producto con código " + codigo);
		}
		
		productoRepository.deleteById(codigo);	
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return productoRepository.findByPrecioBetween(min, max);
	}

	@Override
	public List<Producto> getBetweenDates(Date desde, Date hasta) {
		return productoRepository.findByFechaAltaBetween(desde, hasta);
	}

	@Override
	public List<Producto> getDescatalogados() {
		return productoRepository.findByDescatalogadoTrue();
	}

	@Override
	public List<Producto> getByCategoria(Categoria categoria) {		
		return productoRepository.findByCategoria(categoria);
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoRepository.count();
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

}
