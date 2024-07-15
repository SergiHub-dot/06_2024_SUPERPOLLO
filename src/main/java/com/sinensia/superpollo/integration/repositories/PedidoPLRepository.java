package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;
import com.sinensia.superpollo.integration.model.PedidoPL;

public interface PedidoPLRepository extends JpaRepository<PedidoPL, Long>{
	
	@Query("SELECT p.numero, p.fechaHora, p.establecimiento.nombreComercial, p.estado FROM PedidoPL p")
	List<Object[]> getPedido1DTO();
		
	@Query("   SELECT p.numero,                        "
		 + "          p.fechaHora,                     "
		 + "          p.estado,                        "
		 + "          SIZE(p.lineasDetalle),           "
		 + "          SUM(ld.cantidad * ld.precio)     "
		 + "     FROM PedidoPL p JOIN p.lineasDetalle ld "
		 + " GROUP BY p.numero")
	List<Object[]> getPedido2DTO();
		
	@Query("SELECT new com.sinensia.superpollo.business.model.dtos.Pedido3DTO(p.numero, "
		 + "                                                                  p.establecimiento.nombreComercial, "
		 + "                                                                  SUBSTRING(p.estado, 0), "
		 + "                                                                  p.observaciones, "
		 + "                                                                  'd') "
		 + "  FROM PedidoPL p")
	List<Pedido3DTO> getPedido3DTO();
}
