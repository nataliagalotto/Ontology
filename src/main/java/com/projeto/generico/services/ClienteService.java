package com.projeto.generico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.generico.domain.Cliente;
import com.projeto.generico.repositories.ClienteRepository;
import com.projeto.generico.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired 
	//Vai ser automaticamente instaciada pelo spring
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {	
		
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não entrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
