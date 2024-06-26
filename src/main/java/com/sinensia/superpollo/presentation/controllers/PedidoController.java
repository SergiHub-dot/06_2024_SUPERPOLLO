package com.sinensia.superpollo.presentation.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Pedido;
import com.sinensia.superpollo.business.services.PedidoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoServices pedidoServices;
	
	@GetMapping
	public List<Pedido> getPedidos(){
		return pedidoServices.getAll();
	}
	
	@GetMapping("/{numero}")
	public ResponseEntity<?> getByNumero(@PathVariable Long numero) {
		
		Optional<Pedido> optional = pedidoServices.read(numero);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorHttpCustomizado("No se encuentra el pedido " + numero), HttpStatus.NOT_FOUND);
		} 
	
		return ResponseEntity.of(optional);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pedido pedido, UriComponentsBuilder ucb) {
		
		Long numero = null;
		
		try {
		
			numero = pedidoServices.create(pedido);
		
		} catch(IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorHttpCustomizado(e.getMessage()));
		}
		
		URI uri = ucb.path("/pedidos/{numero}").build(numero);
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{numero}")
	public ResponseEntity<?> delete(@PathVariable Long numero) {
		
		try {
			pedidoServices.delete(numero);
		} catch(Exception e) {
			return new ResponseEntity<>(new ErrorHttpCustomizado("No se encuentra el pedido " + numero), HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
