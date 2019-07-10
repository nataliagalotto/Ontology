package com.projeto.generico.resources;

import com.projeto.generico.dto.ClienteNewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.generico.domain.Cliente;
import com.projeto.generico.dto.ClienteDTO;
import com.projeto.generico.services.ClienteService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable final Integer id) {

        Cliente cliente = clienteService.find(id);

        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody final ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.fromDTO(clienteNewDTO);
        cliente = clienteService.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@Valid @RequestBody final ClienteDTO clienteDTO, @PathVariable final Integer id) {
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.update(cliente);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clienteList = clienteService.findAll();
        List<ClienteDTO> clienteDTOList = clienteList.stream()
                .map(obj -> new ClienteDTO(obj))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDTOList);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") final Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {

        Page<Cliente> clientePage = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clienteDTOPage = clientePage.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(clienteDTOPage);
    }
}
