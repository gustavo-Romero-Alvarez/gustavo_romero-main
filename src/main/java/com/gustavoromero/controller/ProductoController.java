package com.gustavoromero.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gustavoromero.exception.ModeloNotFoundException;
import com.gustavoromero.model.Producto;
import com.gustavoromero.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	private IProductoService service;
	
	@GetMapping
	public ResponseEntity<List<Producto>> listar() {
		List<Producto> lista = service.listar();
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> listarPorId(@PathVariable("id") Integer id) {
		Producto obj = service.leerPorId(id);
		if (obj.getId_Producto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
		}
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Producto producto) {
		Producto obj = service.registrar(producto);
		//producto/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(producto.getId_Producto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto producto) {
		Producto obj = service.modificar(producto);
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Producto obj = service.leerPorId(id);
		if (obj.getId_Producto()== null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	

}
