package com.sinensia.superpollo.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.model.dtos.Establecimiento1DTO;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.integration.model.EstablecimientoPL;
import com.sinensia.superpollo.integration.repositories.EstablecimientoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices{

	@Autowired
	private EstablecimientoPLRepository establecimientoPLRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	@Transactional
	public Long create(Establecimiento establecimiento) {
		
		if(establecimiento.getCodigo() != null) {
			throw new IllegalStateException("El establecimiento " + establecimiento.getNombreComercial() + " ya tiene código. No se puede crear.");
		}
		
	
		return establecimientoPLRepository.save(mapper.map(establecimiento, EstablecimientoPL.class)).getCodigo();
	}

	@Override
	public Optional<Establecimiento> read(Long codigo) {
		
		Optional<EstablecimientoPL> optionalPL = establecimientoPLRepository.findById(codigo);
		
		Establecimiento establecimiento = null;
		
		if(optionalPL.isPresent()) {
			establecimiento = mapper.map(optionalPL.get(), Establecimiento.class);
		}
		
		return Optional.ofNullable(establecimiento);
	}

	@Override
	public List<Establecimiento> getAll() {
		
		List<Establecimiento> establecimientos = new ArrayList<>();
		
		for(EstablecimientoPL establecimientoPL: establecimientoPLRepository.findAll()) {
			establecimientos.add(mapper.map(establecimientoPL, Establecimiento.class));
		}
		
		return establecimientos;
	}

	@Override
	public List<Establecimiento1DTO> getEstablecimiento1DTOs() {
		return establecimientoPLRepository.getEstablecimiento1DTO();
	}

}
