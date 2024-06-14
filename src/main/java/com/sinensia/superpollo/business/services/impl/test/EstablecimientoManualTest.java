package com.sinensia.superpollo.business.services.impl.test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sinensia.superpollo.business.model.DatosContacto;
import com.sinensia.superpollo.business.model.Direccion;
import com.sinensia.superpollo.business.model.Establecimiento;
import com.sinensia.superpollo.business.services.EstablecimientoServices;
import com.sinensia.superpollo.business.services.impl.EstablecimientoServicesImpl;

public class EstablecimientoManualTest {

	public static void main(String[] args) {
		
		EstablecimientoServices establecimientoServices = new EstablecimientoServicesImpl();

		// Probamos metodo1
		
		Establecimiento establecimiento = new Establecimiento();
		Direccion direccion = new Direccion();
		DatosContacto datosContacto = new DatosContacto();
		
		direccion.setDireccion("c/ Pruebas, 23");
		direccion.setPoblacion("Barcelona");
		direccion.setCodigoPostal("08003");
		direccion.setProvincia("Barcelona");
		direccion.setPais("España");
		
		datosContacto.setEmail("pruebas@superpollo.es");
		datosContacto.setTelefono("93 2209098");

		establecimiento.setNombreComercial("ESTABLECIMIENTO PRUEBAS");
		establecimiento.setDatosContacto(datosContacto);
		establecimiento.setDireccion(direccion);
		establecimiento.setFechaInauguracion(new Date(10000200000L));
		
		Long codigo = establecimientoServices.create(establecimiento);
		
		System.out.println(codigo);
	
		// ******************************************************************
		
		// Probamos metodo2
		
		Optional<Establecimiento> optional1 = establecimientoServices.read(102L);
		
		System.out.println(optional1.get());
		
		Optional<Establecimiento> optional2 = establecimientoServices.read(555L);
		
		System.out.println(optional2.isEmpty());
		
		
		// ******************************************************************
		
		// Probamos metodo3
		
		List<Establecimiento> establecimientos = establecimientoServices.getAll();
		
		
		for(Establecimiento est: establecimientos) {
			System.out.println(est);
		}	
	
		
	}

}
