package com.sinensia.superpollo.business.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.EstadoPedido;
import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.integration.model.EstadoPedidoPL;
import com.sinensia.superpollo.integration.model.PedidoPL;
import com.sinensia.superpollo.integration.repositories.PedidoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{

	private final SimpleDateFormat SDF_1 = new SimpleDateFormat("dd/MM/yyyy");
	private final SimpleDateFormat SDF_2 = new SimpleDateFormat("HH:mm");
	
	@Autowired
	private PedidoPLRepository pedidoPLRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	@Transactional
	public Long create(Pedido pedido) {
		
		if(pedido.getNumero() != null) {
			throw new IllegalStateException("Para crear un pedido el número ha de ser null");
		}
		
		pedido.setEstado(EstadoPedido.NUEVO);
	
		return pedidoPLRepository.save(mapper.map(pedido, PedidoPL.class)).getNumero();
	}

	@Override
	public Optional<Pedido> read(Long numero) {
		
		Optional<PedidoPL> optionalPL = pedidoPLRepository.findById(numero);
		
		Pedido pedido = null;
		
		if(optionalPL.isPresent()) {
			pedido = mapper.map(optionalPL.get(), Pedido.class);
		}
		
		return Optional.ofNullable(pedido);
	}
	
	@Override
	@Transactional
	public void updateEstado(Long numero, EstadoPedido estado) {
		
		Optional<PedidoPL> optional = pedidoPLRepository.findById(numero);
		
		if(optional.isEmpty()) {
			throw new IllegalStateException("No existe el pedido " + numero);
		}
		
		EstadoPedidoPL estadoPedidoPLActual = optional.get().getEstado();
		EstadoPedidoPL estadoPedidoPLNuevo = EstadoPedidoPL.valueOf(estado.toString());

		boolean condicion1 = estadoPedidoPLNuevo.equals(EstadoPedidoPL.NUEVO);
		boolean condicion2 = estadoPedidoPLNuevo.equals(EstadoPedidoPL.EN_PROCESO) && !estadoPedidoPLActual.equals(EstadoPedidoPL.NUEVO);
		boolean condicion3 = estadoPedidoPLNuevo.equals(EstadoPedidoPL.PENDIENTE_ENTREGA) && !estadoPedidoPLActual.equals(EstadoPedidoPL.EN_PROCESO);
		boolean condicion4 = estadoPedidoPLNuevo.equals(EstadoPedidoPL.SERVIDO) && !estadoPedidoPLActual.equals(EstadoPedidoPL.PENDIENTE_ENTREGA);
		boolean condicion5 = estadoPedidoPLNuevo.equals(EstadoPedidoPL.CANCELADO) && estadoPedidoPLActual.equals(EstadoPedidoPL.CANCELADO)|| estadoPedidoPLActual.equals(EstadoPedidoPL.SERVIDO);
		
		if(condicion1 || condicion2 || condicion3 || condicion4 || condicion5) {
			throw new IllegalStateException("No se puede pasar un pedido de " + estadoPedidoPLActual + " a " + estadoPedidoPLNuevo);
		}
		
		optional.get().setEstado(estadoPedidoPLNuevo);

	}

	@Override
	@Transactional
	public void delete(Long numero) {
		
		boolean existe = pedidoPLRepository.existsById(numero);
		
		if(!existe) {
			throw new IllegalStateException("No existe el pedido número " + numero);
		}
		
		pedidoPLRepository.deleteById(numero);	
	}

	@Override
	public List<Pedido> getAll() {
		
		List<Pedido> pedidos = new ArrayList<>();
		
		for(PedidoPL pedidoPL: pedidoPLRepository.findAll()) {
			pedidos.add(mapper.map(pedidoPL, Pedido.class));
		}
		
		return pedidos ;
	}

	@Override
	public List<Pedido1DTO> getPedido1DTOs() {
	
		List<Object[]> resultados = pedidoPLRepository.getPedido1DTO();
		List<Pedido1DTO> pedidos1DTO = new ArrayList<>();
		
		for(Object[] objects: resultados) {
		
			Long numero = (Long) objects[0];
			Date fechaHora = (Date) objects[1];
			String nombreEstablecimiento = (String) objects[2];
			String estado = (String) objects[3];
			
			String fecha = SDF_1.format(fechaHora);
			String hora = SDF_2.format(fechaHora);
		
			Pedido1DTO pedido1DTO = new Pedido1DTO(numero, fecha, hora, nombreEstablecimiento, estado);
			pedidos1DTO.add(pedido1DTO);
		}
		
		return pedidos1DTO;
	}

	@Override
	public List<Pedido2DTO> getPedido2DTOs() {
		
		List<Object[]> resultados = pedidoPLRepository.getPedido2DTO();
		
		List<Pedido2DTO> pedidos2DTO = new ArrayList<>();
		
		for(Object[] objects: resultados) {
			
			Long numero = (Long) objects[0];
			Date fechaHora = (Date) objects[1];
			String estado = ((String) objects[2]).toString();
			int numeroLineas = (Integer) objects[3];
			Double importeTotal = (Double) objects[4];
			
			Pedido2DTO pedido2DTO = new Pedido2DTO(numero, fechaHora, estado, numeroLineas, importeTotal);
			pedidos2DTO.add(pedido2DTO);
		}
		
		return pedidos2DTO;
	}

	@Override
	public List<Pedido3DTO> getPedido3DTOs() {
		return pedidoPLRepository.getPedido3DTO();
	}
}
