package com.projeto.generico.services;

import java.util.List;
import java.util.Optional;

import com.projeto.generico.dto.CategoriaDTO;
import com.projeto.generico.services.exception.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projeto.generico.domain.Categoria;
import com.projeto.generico.repositories.CategoriaRepository;
import com.projeto.generico.services.exception.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

	@Autowired
	// Vai ser automaticamente instaciada pelo spring
	private CategoriaRepository repo;

	public Categoria find(final Integer id) {

		Optional<Categoria> categoria = repo.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não entrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

    @Transactional
	public Categoria insert(final Categoria categoria){
        categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(final Categoria  categoria){
		Categoria newCategoria = find(categoria.getId());
		updateData(newCategoria,categoria);
		return repo.save(newCategoria);
	}


	public void delete(final Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e){
			throw  new DataIntegrityException("Não é possível excluir uma categoria que possuí produtos");
		}

	}

	public List<Categoria> findAll(){
		return repo.findAll();
	}

	public Page<Categoria> findPage(final Integer page, final Integer linesPerPage, final String orderBy, final String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(final CategoriaDTO categoriaDTO){
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

	private void updateData(final Categoria newCategoria, final Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}

}
