package com.sinensia.superpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.superpollo.business.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
