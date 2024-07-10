package com.sinensia.superpollo.business.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.EstadoPedido;
import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.integration.repositories.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{

	private final SimpleDateFormat SDF_1 = new SimpleDateFormat("dd/MM/yyyy");
	private final SimpleDateFormat SDF_2 = new SimpleDateFormat("HH:mm");
	
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
	
		List<Object[]> resultados = pedidoRepository.getPedido1DTO();
		List<Pedido1DTO> pedidos1DTO = new ArrayList<>();
		
		for(Object[] objects: resultados) {
		
			Long numero = (Long) objects[0];
			Date fechaHora = (Date) objects[1];
			String nombreEstablecimiento = (String) objects[2];
			EstadoPedido estado = (EstadoPedido) objects[3];
			
			String fecha = SDF_1.format(fechaHora);
			String hora = SDF_2.format(fechaHora);
			String strEstado = estado.toString();
			
			Pedido1DTO pedido1DTO = new Pedido1DTO(numero, fecha, hora, nombreEstablecimiento, strEstado);
			pedidos1DTO.add(pedido1DTO);
		}
		
		return pedidos1DTO;
	}

	@Override
	public List<Pedido2DTO> getPedido2DTOs() {
		
		List<Object[]> resultados = pedidoRepository.getPedido2DTO();
		
		List<Pedido2DTO> pedidos2DTO = new ArrayList<>();
		
		for(Object[] objects: resultados) {
			
			Long numero = (Long) objects[0];
			Date fechaHora = (Date) objects[1];
			String estado = ((EstadoPedido) objects[2]).toString();
			int numeroLineas = (Integer) objects[3];
			Double importeTotal = (Double) objects[4];
			
			Pedido2DTO pedido2DTO = new Pedido2DTO(numero, fechaHora, estado, numeroLineas, importeTotal);
			pedidos2DTO.add(pedido2DTO);
		}
		
		return pedidos2DTO;
	}

	@Override
	public List<Pedido3DTO> getPedido3DTOs() {
		// TODO Auto-generated method stub
		return null;
	}

}
