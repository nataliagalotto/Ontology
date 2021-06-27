package com.projeto.generico.resources;

import com.projeto.generico.domain.Pedido;
import com.projeto.generico.jenawork.InitJena;
import com.projeto.generico.services.PedidoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ontology")
public class OntologyResource {

	@CrossOrigin
	@GetMapping("/describePolitician")
	public List<JSONObject> describePolitician(@RequestParam String politician_name) {

		String queryString =
				"PREFIX pol: <http://www.semanticweb.org/viva/ontologies/2019/6/politicians#>" +
						"SELECT ?Y ?Z WHERE { pol:" + politician_name + " ?Y ?Z .}";
		List<JSONObject> resultSet = InitJena.describePoliticians(queryString);
		System.out.println(queryString);
		return resultSet;
	}
}
