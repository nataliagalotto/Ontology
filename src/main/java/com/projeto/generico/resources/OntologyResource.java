package com.projeto.generico.resources;

import com.projeto.generico.config.InitJena;
import net.minidev.json.JSONObject;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/ontology")
public class OntologyResource {

	public static final String SOURCE = "./src/main/resources/data/";

	// Pizza ontology namespace
	public static final String PIZZA_NS = "http://www.co-ode.org/ontologies/pizza/pizza.owl#";

	public static final String COMERCIAL_NS = "http://www.semanticweb.org/alefe/ontologies/2021/4/centro_comercial#";

	private static String ontoFileEP = "EPWS_parte1_v6.owl";

	private static String ontoFileFood = "FoodRDF.owl";

	String prefix = "prefix ccom: <" + COMERCIAL_NS + ">\n" +
			"prefix rdf: <" + RDF.getURI() + ">\n" +
			"prefix rdfs: <" + RDFS.getURI() + ">\n" +
			"prefix owl: <" + OWL.getURI() + ">\n" +
			"prefix gr: <http://purl.org/goodrelations/v1#>\n" +
			"prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n";

	@CrossOrigin
	@GetMapping("/lojas")
	public List<JSONObject> getLojasByProduto(@RequestParam String produto) {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Loja"));

		String queryString = ( prefix +
				"SELECT * { ?Loja ccom:Vende ccom:" + produto +"}"
		);

		return getItems(queryString, label);
	}

	@CrossOrigin
	@GetMapping("/lojasPorNome")
	public List<JSONObject> getLojasPorNome() {

		ArrayList<String> label = new ArrayList<>(
				Arrays.asList("Loja"));

		String queryString = ( prefix +
				"SELECT ?Loja \n" +
				"WHERE{ ?x rdf:type gr:BusinessEntity.\n" +
				"?x gr:legalName ?Loja.}"
		);

		return getItems(queryString, label);
	}

	@CrossOrigin
	@GetMapping("/lojasPorNomeOrderBy")
	public List<JSONObject> getLojasPorNomeOrderBy() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Loja"));

		String queryString = ( prefix +
				"SELECT ?Loja \n" +
				"WHERE{ ?x rdf:type gr:BusinessEntity.\n" +
				"?x gr:legalName ?Loja." +
				"} ORDER BY ?Loja"
		);

		return getItems(queryString, label);
	}

	@CrossOrigin
	@GetMapping("/oferta")
	public List<JSONObject> getOferta() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("offering", "item"));

		String queryString = ( prefix +
				"SELECT ?offering ?item \n" +
				"WHERE { ?offering rdf:type gr:Offering .\n" +
				"?offering gr:includesObject ?object.\n" +
				"?object gr:typeOfGood ?item.}"
		);

		return getItems(queryString, label);
	}
//
//	@CrossOrigin
//	@GetMapping("/lojasPorCategoria")
//	public List<JSONObject> getLojasPorCategoria(@RequestParam String categoria) {
//
//		ArrayList<String> label = new ArrayList<String>(
//				Arrays.asList("entity", "legalname", "category"));
//
//		String queryString = ( prefix +
//				"SELECT ?entity ?legalname ?category\n" +
//				"WHERE { ?entity rdf:type gr:BusinessEntity.\n" +
//				" ?entity gr:legalName ?legalname.\n" +
//				" ?entity gr:category ?category.\n" +
//				"        ?entity gr:category \""+categoria+"\"^^xsd:string. \n" +
//				" }"
//		);
//
//		return getItems(queryString, label);
//	}

	@CrossOrigin
	@GetMapping("/lojasPorAtividade")
	public List<JSONObject> getLojasPorAtividade() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Loja", "Atividade"));

		String queryString = ( prefix +
				"SELECT ?Loja ?Atividade\n" +
				"WHERE { ?x rdf:type gr:BusinessEntity.\n" +
				" ?x gr:legalName ?Loja.\n" +
				" ?x gr:category ?Atividade.\n" +
				" } ORDER BY ?Atividade"
		);

		return getItems(queryString, label);
	}

	@CrossOrigin
	@GetMapping("/ofertaPorLojas")
	public List<JSONObject> getOfertaPorLojas(@RequestParam String loja) {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Loja", "Nome", "offering", "Teste", "Preco", "Qtd", "Produto"));

		String queryString = (prefix +
				"SELECT ?Loja ?Nome ?offering ?Teste ?Preco ?Qtd ?Produto\n" +
				"WHERE{\n" +
				"    ?Loja gr:offers ?offering.\n" +
				"    ?Loja gr:legalName \""+loja+"\"^^xsd:string.\n" +
				"    ?Loja gr:legalName ?Nome.\n" +
				"    \n" +
				"    ?Teste rdf:type gr:UnitPriceSpecification.\n" +
				"    ?offering gr:hasPriceSpecification ?Teste.\n" +
				"    ?Teste gr:hasCurrencyValue ?Preco. \n" +
				"\n" +
				"    ?Quantidade rdf:type gr:TypeAndQuantityNode.\n" +
				"    ?offering gr:includesObject ?Quantidade.\n" +
				"    ?Quantidade gr:amountOfThisGood ?Qtd.\n" +
				"\n" +
				"    ?Prd rdf:type gr:ProductOrService.\n" +
				"    ?Quantidade gr:typeOfGood ?Prd.\n" +
				"    ?Prd gr:name ?Produto.   \n" +
				"}"
		);

		return getItems(queryString, label);
	}

	public List<JSONObject> getItems(String queryString, ArrayList<String> label){
		System.out.println(queryString);
		return InitJena.getItems(queryString, ontoFileEP, label);
	}

	public List<JSONObject> getProdutos(String queryString, ArrayList<String> label){
		System.out.println(queryString);
		return InitJena.getItems(queryString, ontoFileEP, label);
	}
}
