package com.sinensia.superpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.EstadoPedido;
import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.integration.repositories.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	@Transactional
	public Long create(Pedido pedido) {
		
		if(pedido.getNumero() != null) {
			throw new IllegalStateException("El número de pedido no es null. No se puede crear.");
		}
		
		pedido.setEstado(EstadoPedido.NUEVO);
		
		Pedido createdPedido = pedidoRepository.save(pedido);
		
		return createdPedido.getNumero();
	}

	@Override
	public Optional<Pedido> read(Long numero) {
		return pedidoRepository.findById(numero);
	}

	@Override
	@Transactional
	public void delete(Long numero) {
		
		boolean existe = pedidoRepository.existsById(numero);
		
		if(!existe) {
			throw new IllegalStateException("No existe el pedido número " + numero);
		}
		
		pedidoRepository.deleteById(numero);	
	}

	@Override
	public List<Pedido> getAll() {
		return pedidoRepository.findAll();
	}

	@Override
	public List<Pedido1DTO> getPedido1DTOs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido2DTO> getPedido2DTOs() {
		// TODO Auto-generated method stub
		return null;
	}

}
