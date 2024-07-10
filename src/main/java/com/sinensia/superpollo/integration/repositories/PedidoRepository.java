package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query("SELECT p.numero, p.fechaHora, p.establecimiento.nombreComercial, p.estado FROM Pedido p")
	List<Object[]> getPedido1DTO();
		
	@Query("   SELECT p.numero, "
		 + "          p.fechaHora, "
		 + "          p.estado, "
		 + "          SIZE(p.lineasDetalle), "
		 + "          SUM(ld.cantidad * ld.precio) "
		 + "     FROM Pedido p JOIN p.lineasDetalle ld "
		 + " GROUP BY p.numero")
	List<Object[]> getPedido2DTO();
	
}
