package com.sinensia.superpollo.business.services.dummy.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sinensia.superpollo.business.model.DatosContacto;
import com.sinensia.superpollo.business.model.Direccion;
import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;

@Service
@Primary
public class EstablecimientoDummyServicesImpl implements EstablecimientoServices {

	private final Map<Long, Establecimiento> BASE_DATOS_ESTABLECIMIENTOS = new HashMap<>();
	
	public EstablecimientoDummyServicesImpl() {
		initObjects();
	}
	
	@Override
	public Long create(Establecimiento establecimiento) {
		
		if(establecimiento.getCodigo() != null) {
			throw new IllegalStateException("El establecimiento " + establecimiento.getNombreComercial() +" ya tiene código. No se puede crear.");
		}

		Long maximoCodigo = 0L;
		
		for(Long clave: BASE_DATOS_ESTABLECIMIENTOS.keySet()) {
			if (clave > maximoCodigo) {
				maximoCodigo = clave;
			}
		}
		
		Long nuevoCodigo = maximoCodigo + 1;
		
		establecimiento.setCodigo(nuevoCodigo);
		
		BASE_DATOS_ESTABLECIMIENTOS.put(establecimiento.getCodigo(), establecimiento);
		
		return nuevoCodigo;
	}

	@Override
	public Optional<Establecimiento> read(Long id) {
	
		return Optional.ofNullable(BASE_DATOS_ESTABLECIMIENTOS.get(id));
	}

	@Override
	public List<Establecimiento> getAll() {
		
		List<Establecimiento> establecimientos = new ArrayList<>();
		
		for(Establecimiento establecimiento: BASE_DATOS_ESTABLECIMIENTOS.values()) {
			establecimientos.add(establecimiento);
		}
		
		return establecimientos;
	}

	// **********************************************************
	//
	// Private Methods
	//
	// **********************************************************
	
	private void initObjects() {
		
		Direccion direccion1 = new Direccion();
		Direccion direccion2 = new Direccion();
		
		direccion1.setDireccion("Gran Via de les Corts Catalanes, 234");
		direccion1.setPoblacion("Barcelona");
		direccion1.setCodigoPostal("08020");
		direccion1.setProvincia("Barcelona");
		direccion1.setPais("España");
		
		direccion2.setDireccion("Avda. Mostols, 23");
		direccion2.setPoblacion("Madrid");
		direccion2.setCodigoPostal("89987");
		direccion2.setProvincia("Madrid");
		direccion2.setPais("España");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date fecha1 = null;
		Date fecha2 = null;
		
		try {
			fecha1 = sdf.parse("23/10/2019");
			fecha2 = sdf.parse("14/04/2017");
		} catch(ParseException e) {
			
		}
		
		DatosContacto datosContacto1 = new DatosContacto();
		DatosContacto datosContacto2 = new DatosContacto();
		
		datosContacto1.setEmail("granvia@superpollos.com");
		datosContacto1.setTelefono("93 220 90 90");
		
		datosContacto2.setEmail("vaguada@superpollos.com");
		datosContacto2.setTelefono("91 240 90 32");
		
		Establecimiento establecimiento1 = new Establecimiento();
		Establecimiento establecimiento2 = new Establecimiento();
		
		establecimiento1.setCodigo(101L);
		establecimiento1.setNombreComercial("Gran Via 2");
		establecimiento1.setFechaInauguracion(fecha1);
		establecimiento1.setDireccion(direccion1);
		establecimiento1.setDatosContacto(datosContacto1);
	
		establecimiento2.setCodigo(102L);
		establecimiento2.setNombreComercial("La Vaguada");
		establecimiento2.setFechaInauguracion(fecha2);
		establecimiento2.setDireccion(direccion2);
		establecimiento2.setDatosContacto(datosContacto2);
		
		BASE_DATOS_ESTABLECIMIENTOS.put(establecimiento1.getCodigo(), establecimiento1);
		BASE_DATOS_ESTABLECIMIENTOS.put(establecimiento2.getCodigo(), establecimiento2);
		
	}

}
