package com.projeto.generico.services;

import java.util.List;
import java.util.Optional;

import com.projeto.generico.dto.ClienteDTO;
import com.projeto.generico.services.exception.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projeto.generico.domain.Cliente;
import com.projeto.generico.repositories.ClienteRepository;
import com.projeto.generico.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired 
	//Vai ser automaticamente instaciada pelo spring
	private ClienteRepository repo;

	public Cliente find(final Integer id) {
		
		Optional<Cliente> cliente = repo.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não entrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	public Cliente insert(final Cliente cliente){
		cliente.setId(null);
		return repo.save(cliente);
	}

	public Cliente update(final Cliente cliente){
		Cliente newCliente = find(cliente.getId());
		updateData(newCliente,cliente);
		return repo.save(newCliente);
	}


	public void delete(final Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e){
			throw  new DataIntegrityException("Não é possível excluir uma cliente porque há entidades relacionadas");
		}

	}

	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Page<Cliente> findPage(final Integer page, final Integer linesPerPage, final String orderBy, final String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}


	public Cliente fromDTO(final ClienteDTO clienteDTO){
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null,null);
	}

	private void updateData(final Cliente newCliente, final Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
}
