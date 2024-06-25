package com.sinensia.superpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Pedido;

public interface PedidoServices {
	
	/**
	 * Devuelve el número que le ha otorgado el sistema al nuevo pedido
	 * 
	 * Si el número del pedido NO es null lanza IllegalStateException
	 *
	 */
	Long create(Pedido pedido);											
	
	Optional<Pedido> read(Long numero);									
	
	/**
	 * Si el codigo del pedido no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long numero);
	
	List<Pedido> getAll();
	
}
