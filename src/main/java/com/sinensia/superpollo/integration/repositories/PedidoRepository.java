package com.sinensia.superpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query("SELECT p.numero, p.fechaHora, p.establecimiento.nombreComercial, p.estado FROM Pedido p")
	List<Object[]> getPedido1DTO();
		
	@Query("   SELECT p.numero,                        "
		 + "          p.fechaHora,                     "
		 + "          p.estado,                        "
		 + "          SIZE(p.lineasDetalle),           "
		 + "          SUM(ld.cantidad * ld.precio)     "
		 + "     FROM Pedido p JOIN p.lineasDetalle ld "
		 + " GROUP BY p.numero")
	List<Object[]> getPedido2DTO();
	
	
	/*
	 * private Long numero;
	private String establecimiento;
	private String estado;
	private String comentario; // Mostramos solo los 20 primeros caracteres y añadimos...   Ejemplo: "No entragra los vaso..."
	private String trimestre; 
	 */
	
	@Query("SELECT new com.sinensia.superpollo.business.model.dtos.Pedido3DTO(p.numero, "
		 + "                                                                  p.establecimiento.nombreComercial, "
		 + "                                                                  SUBSTRING(p.estado, 0), "
		 + "                                                                  p.observaciones, "
		 + "                                                                  'd') "
		 + "  FROM Pedido p")
	List<Pedido3DTO> getPedido3DTO();
}
