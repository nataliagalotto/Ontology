package com.projeto.generico.resources;

import com.projeto.generico.jenawork.InitJena;
import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ontology")
public class OntologyResource {

	public static final String SOURCE = "./src/main/resources/data/";

	// Pizza ontology namespace
	public static final String PIZZA_NS = "http://www.co-ode.org/ontologies/pizza/pizza.owl#";

	public static final String COMERCIAL_NS = "http://www.semanticweb.org/alefe/ontologies/2021/4/centro_comercial#";

	private static String ontoFileEP = "EPWS_parte1.owl";

	private static String ontoFileFood = "FoodRDF.owl";

	@CrossOrigin
	@GetMapping("/getProvinces")
	public List<JSONObject> getProvinces() {

		String prefix = "prefix X: <" + PIZZA_NS + ">\n" +
				"prefix rdfs: <" + RDFS.getURI() + ">\n" +
				"prefix owl: <" + OWL.getURI() + ">\n";

		String queryString = ( prefix +
						"select ?X where {?X a owl:Class ; " +
						"                            rdfs:subClassOf ?restriction.\n" +
						"                     ?restriction owl:onProperty X:hasTopping ;" +
						"                            owl:someValuesFrom X:PeperoniSausageTopping" +
						"}" );
		List<JSONObject> resultSet = InitJena.getItems(queryString, ontoFileFood );
		System.out.println(queryString);
		return resultSet;
	}

	@CrossOrigin
	@GetMapping("/loja")
	public List<JSONObject> getloja() {

		String prefix = "prefix X: <" + COMERCIAL_NS + ">\n" +
				"prefix rdfs: <" + RDFS.getURI() + ">\n" +
				"prefix owl: <" + OWL.getURI() + ">\n";

		String queryString = ( prefix +
				"select ?X where {?X a owl:Thing ; " +
				"                            rdfs:instances ?loja.\n" +
				"}"
		);
		List<JSONObject> resultSet = InitJena.getItems(queryString, ontoFileEP);
		System.out.println(queryString);
		return resultSet;
	}
}
