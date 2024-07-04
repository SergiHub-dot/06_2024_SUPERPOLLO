package com.sinensia.superpollo.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="ESTABLECIMIENTOS")
public class Establecimiento implements Serializable {

	@Id
	@GeneratedValue(generator="ESTABLECIMIENTO_SEQ")
	private Long codigo;
	
	private String nombreComercial;
	
	@Embedded
	private Direccion direccion;
	
	@Embedded
	private DatosContacto datosContacto;
	
	@Temporal(TemporalType.DATE)
	private Date fechaInauguracion;
	
	public Establecimiento() {
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public DatosContacto getDatosContacto() {
		return datosContacto;
	}

	public void setDatosContacto(DatosContacto datosContacto) {
		this.datosContacto = datosContacto;
	}

	public Date getFechaInauguracion() {
		return fechaInauguracion;
	}

	public void setFechaInauguracion(Date fechaInauguracion) {
		this.fechaInauguracion = fechaInauguracion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Establecimiento other = (Establecimiento) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Establecimiento [codigo=" + codigo + ", nombreComercial=" + nombreComercial + ", direccion=" + direccion
				+ ", datosContacto=" + datosContacto + ", fechaInauguracion=" + fechaInauguracion + "]";
	}
	
}
