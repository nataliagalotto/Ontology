package com.projeto.generico.resources;

import com.projeto.generico.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.generico.domain.Cliente;
import com.projeto.generico.services.ClienteService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {

			Cliente obj = service.find(id);

			return ResponseEntity.ok().body(obj);
	}

}
