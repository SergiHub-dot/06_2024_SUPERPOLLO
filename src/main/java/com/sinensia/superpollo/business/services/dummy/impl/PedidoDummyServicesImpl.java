package com.sinensia.superpollo.business.services.dummy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.services.PedidoServices;

@Service
@Primary
public class PedidoDummyServicesImpl implements PedidoServices {

	@Override
	public Long create(Pedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Pedido> read(Long numero) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Long numero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pedido> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
