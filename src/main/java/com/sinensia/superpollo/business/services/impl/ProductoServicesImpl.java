package com.sinensia.superpollo.business.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.model.dtos.Producto1DTO;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.integration.model.ProductoPL;
import com.sinensia.superpollo.integration.repositories.ProductoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServicesImpl implements ProductoServices {

	@Autowired
	private ProductoPLRepository productoPLRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getCodigo() != null) {
			throw new IllegalStateException("El código de producto no es null. No se puede crear.");
		}
		
		ProductoPL productoPL = mapper.map(producto, ProductoPL.class);
		
		return productoPLRepository.save(productoPL).getCodigo();
	}

	@Override
	public Optional<Producto> read(Long codigo) {
		
		Optional<ProductoPL> optionalPL = productoPLRepository.findById(codigo);
		
		Producto producto = null;
		
		if(optionalPL.isPresent()) {
			producto = mapper.map(optionalPL.get(), Producto.class);
		}
	
		return Optional.ofNullable(producto);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		boolean existe = productoPLRepository.existsById(producto.getCodigo());
		
		if(!existe) {
			throw new IllegalStateException("El producto " + producto.getCodigo() + " no existe. No se puede actualizar.");
		}
		
		productoPLRepository.save(mapper.map(producto, ProductoPL.class));
		
	}

	@Override
	@Transactional
	public void delete(Long codigo) {
		
		boolean existe = productoPLRepository.existsById(codigo);
		
		if(!existe) {
			throw new IllegalStateException("No existe el producto con código " + codigo);
		}
		
		productoPLRepository.deleteById(codigo);	
	}

	@Override
	public List<Producto> getAll() {
		
		return productoPLRepository.findAll().stream()
				.map(x -> mapper.map(x, Producto.class))
				.toList();
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return productoPLRepository.findByPrecioBetween(min, max);
	}

	@Override
	public List<Producto> getBetweenDates(Date desde, Date hasta) {
		return productoPLRepository.findByFechaAltaBetween(desde, hasta);
	}

	@Override
	public List<Producto> getDescatalogados() {
		return productoPLRepository.findByDescatalogadoTrue();
	}

	@Override
	public List<Producto> getByCategoria(Categoria categoria) {		
		return productoPLRepository.findByCategoria(categoria);
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoPLRepository.count();
	}

	@Override
	@Transactional
	public void variarPrecio(List<Producto> productos, double porcentaje) {
		
		List<ProductoPL> productosPL = new ArrayList<>();
		
		for(Producto producto: productos) {
			productosPL.add(mapper.map(producto, ProductoPL.class));
		}
		
		productoPLRepository.variarPrecio(productosPL, porcentaje);
	}

	@Override
	@Transactional
	public void variarPrecio(long[] codigos, double porcentaje) {
		productoPLRepository.variarPrecio(codigos, porcentaje);
	}

	@Override
	public Map<Categoria, Integer> getEstadisticaNumeroProductosPorCategoria() {
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaNumeroProductoCategoria();
		
		Map<Categoria, Integer> estadistica = new HashMap<>();
		
		for(Object[] objects: resultados) {
			Categoria categoria = (Categoria) objects[0];
			Integer cantidad = ((Long) objects[1]).intValue();
			estadistica.put(categoria, cantidad);
		}
		
		return estadistica;
	}

	@Override
	public Map<Categoria, Double> getEstadisticaPrecioMedioProductosPorCategoria() {
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaPrecioMedioProductoCategoria();
		
		Map<Categoria, Double> estadistica = new HashMap<>();
		
		for(Object[] objects: resultados) {
			Categoria categoria = (Categoria) objects[0];
			Double precioMedio = (Double) objects[1];
			estadistica.put(categoria, precioMedio);
		}
		
		return estadistica;
	}

	@Override
	public List<Producto1DTO> getProducto1DTOs() {
		
		List<Object[]> resultados = productoPLRepository.findProducto1DTOs();
		
		List<Producto1DTO> productos1DTO = new ArrayList<>();
		
		for(Object[] objects: resultados) {
			String nombre = (String) objects[0];
			Double precio = (Double) objects[1];
			String nombreCategoria = (String) objects[2];
			productos1DTO.add(new Producto1DTO(nombre, precio, nombreCategoria));
		}
			
		return productos1DTO;
	}

}
