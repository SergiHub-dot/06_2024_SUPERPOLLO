package com.sinensia.superpollo.presentation.restcontrollers.pruebas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.superpollo.business.model.dtos.Pedido1DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido2DTO;
import com.sinensia.superpollo.business.model.dtos.Pedido3DTO;
import com.sinensia.superpollo.business.model.dtos.Producto1DTO;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.integration.repositories.PedidoPLRepository;

@RestController
@RequestMapping("/pruebas")
public class PruebasController {

	@Autowired
	private ProductoServices productoServices;
	
	@Autowired
	private PedidoServices pedidoServices;
	
	@Autowired
	private PedidoPLRepository pedidoRepository;
	
	// http://localhost:8080/pruebas/services/pedidodto3
	
	@GetMapping("/services/pedidodto3")
	public List<Pedido3DTO> getServicesPedido3DTOs(){
		return pedidoServices.getPedido3DTOs();
	}
	
	@GetMapping("/services/productodto1")
	public List<Producto1DTO> getRepositoryProducto1DTOs(){
		return productoServices.getProducto1DTOs();
	}
	
	@GetMapping("/services/pedido1dto")
	public List<Pedido1DTO> getPedido1DTO() {
		return pedidoServices.getPedido1DTOs();	
	}
	
	@GetMapping("/services/pedido2dto")
	public List<Pedido2DTO> getPedido2DTO() {
		return pedidoServices.getPedido2DTOs();	
	}
	
	@GetMapping("/repository/pedido1dto")
	public List<Object[]> getRepositoryPedido1DTO() {
		return pedidoRepository.getPedido1DTO();
	}
	
	@GetMapping("/repository/pedido2dto")
	public List<Object[]> getRepositoryPedido2DTO() {
		return pedidoRepository.getPedido2DTO();
	}
}
