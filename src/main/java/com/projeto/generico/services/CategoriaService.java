package com.projeto.generico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.generico.domain.Categoria;
import com.projeto.generico.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired 
	//Vai ser automaticamente instaciada pelo spring
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {	
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		
	}
}
