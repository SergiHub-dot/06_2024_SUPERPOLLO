package com.sinensia.superpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;

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
	
	List<Pedido1DTO> getPedido1DTOs();
	List<Pedido2DTO> getPedido2DTOs();
	List<Pedido3DTO> getPedido3DTOs();
	
}
