package com.sinensia.superpollo.presentation.restcontrollers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.superpollo.business.model.Producto;
import com.sinensia.superpollo.business.services.ProductoServices;
import com.sinensia.superpollo.presentation.config.ErrorHttpCustomizado;
import com.sinensia.superpollo.presentation.config.PresentationException;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoServices productoServices;
	
	@GetMapping
	public List<Producto> getProductos(@RequestParam(required = false) Double min, 
			                           @RequestParam(required = false) Double max){
		
		List<Producto> productos = null;
		
		if(min != null && max != null) {
			productos = productoServices.getBetweenPriceRange(min, max);
		} else {
			productos = productoServices.getAll();
		}
		
		return productos;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> getByCodigo(@PathVariable Long codigo) {
		
		Optional<Producto> optional = productoServices.read(codigo);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorHttpCustomizado("No se encuentra el producto " + codigo), HttpStatus.NOT_FOUND);
		} 
	
		return ResponseEntity.of(optional);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Producto producto, UriComponentsBuilder ucb) {
		
		Long codigo = null;
		
		try {
		
			codigo = productoServices.create(producto);
		
		} catch(IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ErrorHttpCustomizado(e.getMessage()));
		}
		
		URI uri = ucb.path("/productos/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
		
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		
		try {
			productoServices.delete(codigo);
		} catch(IllegalStateException e) {
			throw new PresentationException("No se encuentra el producto " + codigo, HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			throw new PresentationException("Ha habido un problema en la petición. ", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Producto producto){
		
		try {
			productoServices.update(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException("No se encuentra el producto " + producto.getCodigo(), HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			throw new PresentationException("Ha habido un problema en la petición. ", HttpStatus.BAD_REQUEST);
		}	
	}
}
