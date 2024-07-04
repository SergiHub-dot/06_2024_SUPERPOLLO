package com.sinensia.superpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.superpollo.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
