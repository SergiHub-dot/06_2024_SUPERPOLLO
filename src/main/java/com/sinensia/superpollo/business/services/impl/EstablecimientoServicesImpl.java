package com.sinensia.superpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices{

	@Override
	public Long create(Establecimiento establecimiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Establecimiento> read(Long codigo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Establecimiento> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
