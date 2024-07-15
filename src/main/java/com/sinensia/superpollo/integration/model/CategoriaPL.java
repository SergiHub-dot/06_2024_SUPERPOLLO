package com.sinensia.superpollo.integration.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="CATEGORIAS")
public class CategoriaPL implements Serializable{

	@Id
	@GeneratedValue(generator = "CATEGORIA_SEQ")
	private Long id;
	
	@Column(name="NAME")
	private String nombre;
	
	public CategoriaPL() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaPL other = (CategoriaPL) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CategoriaPL [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
