package com.sinensia.superpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.superpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
