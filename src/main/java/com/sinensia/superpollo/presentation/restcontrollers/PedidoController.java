package com.sinensia.superpollo.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.presentation.config.PresentationException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private PedidoServices pedidoServices;
	
	public PedidoController(PedidoServices pedidoServices) {
		this.pedidoServices = pedidoServices;
	}
	
	@GetMapping
	public List<Pedido> getPedidos(){
		return pedidoServices.getAll();
	}
	
	@GetMapping("/{numero}")
	public Pedido getByNumero(@PathVariable Long numero) {
		
		Optional<Pedido> optional = pedidoServices.read(numero);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No se encuentra el pedido " + numero, HttpStatus.NOT_FOUND);
		} 
	
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pedido pedido, UriComponentsBuilder ucb) {
		Long numero = pedidoServices.create(pedido);
		return ResponseEntity.created(ucb.path("/pedidos/{numero}").build(numero)).build();
	}
	
	@DeleteMapping("/{numero}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long numero) {
		
		try {
			pedidoServices.delete(numero);
		} catch(Exception e) {
			throw new PresentationException("No se encuentra el pedido " + numero, HttpStatus.NOT_FOUND);
		}
	}
	
}
