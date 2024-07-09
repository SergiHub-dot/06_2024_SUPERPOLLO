package com.sinensia.superpollo.business.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.model.dtos.Producto1DTO;

public interface ProductoServices {

	/**
	 * Devuelve el código que le ha otorgado el sistema al nuevo producto
	 * 
	 * Si el código del producto NO es null lanza IllegalStateException
	 *
	 */
	Long create(Producto producto);											
	
	Optional<Producto> read(Long codigo);									
	
	/**
	 * Si el codigo del producto no existe lanza IllegalStateException
	 * 
	 */
	void update(Producto producto);											
	
	/**
	 * Si el codigo del producto no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long codigo);
	
	List<Producto> getAll();
	
	
	/**
	 * Incluye los extemos
	 * 
	 */
	List<Producto> getBetweenPriceRange(double min, double max);
	
	/**
	 * Incluye los extemos
	 * 
	 */
	List<Producto> getBetweenDates(Date desde, Date hasta);
	
	List<Producto> getDescatalogados();
	
	List<Producto> getByCategoria(Categoria categoria);
	
	int getNumeroTotalProductos();
	
	/**
	 * Incrementa un porcentaje el precio de los productos que se pasan
	 * 
	 * Por ejemplo:
	 * 
	 * si porcentaje = 2.0 ---> Los incrementa un 2.0 %
	 * 
	 */
	void variarPrecio(List<Producto> productos, double porcentaje);
	
	/**
	 * Incrementa un porcentaje el precio de los productos que se pasan (por codigo)
	 * 
	 * Por ejemplo:
	 * 
	 * si porcentaje = 2.0 ---> Los incrementa un 2.0 %
	 * 
	 */
	void variarPrecio(long[] codigos, double porcentaje);
	
	/**
	 * Nos devuelve un mapa con la informacion del numero de productos que hay para cada categoria
	 * 
	 * Categoria   Numero de Productos
	 * =========   ===================
	 * TAPA                         12
	 * REFRESCOS                     7
	 * LICORES                       5
	 * ...
	 */
	Map<Categoria, Integer> getEstadisticaNumeroProductosPorCategoria();
	
	/**
	 * Nos devuelve un mapa con la informacion del precio medio de los productos de cada categoria
	 * 
	 * Categoria   Precio medio
	 * =========   ============
	 * TAPA                 7.9
	 * REFRESCOS            2.8
	 * LICORES             12.3
	 * ...
	 */
	Map<Categoria, Double> getEstadisticaPrecioMedioProductosPorCategoria();
	
	List<Producto1DTO> getProducto1DTOs();
}
