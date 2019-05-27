package com.projeto.generico.resources;

import com.projeto.generico.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.generico.domain.Categoria;
import com.projeto.generico.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable final Integer id) {

        Categoria categoria = service.find(id);

        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
        categoria = service.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable final Integer id) {
        categoria = service.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> categoriaList = service.findAll();
        List<CategoriaDTO> categoriaDTOList = categoriaList.stream()
                .map(obj -> new CategoriaDTO(obj))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriaDTOList);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
                @RequestParam(value ="page" , defaultValue = "0") Integer page,
                @RequestParam(value ="linesPerPage" , defaultValue = "24") Integer linesPerPage,
                @RequestParam(value ="orderBy" , defaultValue = "nome") String orderBy,
                @RequestParam(value ="direction" , defaultValue = "ASC") String direction) {

        Page<Categoria> categoriaPage = service.findPage( page,  linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriaDTOPage = categoriaPage.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(categoriaDTOPage);
    }


}
