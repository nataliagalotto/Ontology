package com.projeto.generico.services;

import java.util.List;
import java.util.Optional;

import com.projeto.generico.domain.Cidade;
import com.projeto.generico.domain.Endereco;
import com.projeto.generico.domain.enums.TipoCliente;
import com.projeto.generico.dto.ClienteDTO;
import com.projeto.generico.dto.ClienteNewDTO;
import com.projeto.generico.repositories.EnderecoRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	
	@Autowired 
	//Vai ser automaticamente instaciada pelo spring
	private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

	public Cliente find(final Integer id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não entrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente){
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(final Cliente cliente){
		Cliente newCliente = find(cliente.getId());
		updateData(newCliente,cliente);
		return clienteRepository.save(newCliente);
	}


	public void delete(final Integer id){
		find(id);
		try{
			clienteRepository.deleteById(id);
		}catch (DataIntegrityViolationException e){
			throw  new DataIntegrityException("Não é possível excluir uma cliente porque há entidades relacionadas");
		}

	}

	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(final Integer page, final Integer linesPerPage, final String orderBy, final String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}


	public Cliente fromDTO(final ClienteDTO clienteDTO){
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null,null);
	}

    public Cliente fromDTO(final ClienteNewDTO clienteNewDTO){
	    Cliente cliente =  new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOutCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()));
	    Cidade cidade = new Cidade(clienteNewDTO.getCidade(), null, null);
	    Endereco endereco = new Endereco(null, clienteNewDTO.getLogdradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        if (clienteNewDTO.getTelefone3() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        return cliente;
	}

	private void updateData(final Cliente newCliente, final Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
}
