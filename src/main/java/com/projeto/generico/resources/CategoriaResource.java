package com.projeto.generico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.generico.domain.Categoria;
import com.projeto.generico.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable final Integer id) {

		Categoria categoria = service.buscar(id);

		return ResponseEntity.ok().body(categoria);
	}

	@PostMapping()
	public ResponseEntity<?> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
