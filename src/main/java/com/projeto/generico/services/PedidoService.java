package com.projeto.generico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.generico.domain.Pedido;
import com.projeto.generico.repositories.PedidoRepository;
import com.projeto.generico.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired 
	//Vai ser automaticamente instaciada pelo spring
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não entrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
