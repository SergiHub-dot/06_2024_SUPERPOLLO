package com.sinensia.superpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.integration.repositories.EstablecimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices{

	@Autowired
	private EstablecimientoRepository establecimientoRepository;
	
	@Override
	@Transactional
	public Long create(Establecimiento establecimiento) {
		
		if(establecimiento.getCodigo() != null) {
			throw new IllegalStateException("El establecimiento " + establecimiento.getNombreComercial() + " ya tiene código. No se puede crear.");
		}
		
		Establecimiento createdEstablecimiento = establecimientoRepository.save(establecimiento);
		
		return createdEstablecimiento.getCodigo();
	}

	@Override
	public Optional<Establecimiento> read(Long codigo) {
		return establecimientoRepository.findById(codigo);
	}

	@Override
	public List<Establecimiento> getAll() {
		return establecimientoRepository.findAll();
	}

	@Override
	public List<Establecimiento1DTO> getEstablecimiento1DTOs() {
		return establecimientoRepository.getEstablecimiento1DTO();
	}

}
