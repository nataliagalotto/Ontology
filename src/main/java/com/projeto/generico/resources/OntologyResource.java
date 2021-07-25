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

	public static final String COMERCIAL_NS = "http://www.semanticweb.org/alefe/ontologies/2021/4/centro_comercial#";

	private static String ontoFileEP = "EPWS_parte1_v6.owl";

	String prefix = "prefix ccom: <" + COMERCIAL_NS + ">\n" +
			"prefix rdf: <" + RDF.getURI() + ">\n" +
			"prefix rdfs: <" + RDFS.getURI() + ">\n" +
			"prefix owl: <" + OWL.getURI() + ">\n" +
			"prefix gr: <http://purl.org/goodrelations/v1#>\n" +
			"prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
			"prefix foaf: <http://xmlns.com/foaf/0.1/>\n" +
			"prefix schema: <http://schema.org/>\n";

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

	@CrossOrigin
	@GetMapping("/lojasPorCategoria")
	public List<JSONObject> getLojasPorCategoria(@RequestParam String categoria) {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("entity", "legalname", "category"));

		String queryString = ( prefix +
				"SELECT ?entity ?legalname ?category\n" +
				"WHERE { ?entity rdf:type gr:BusinessEntity.\n" +
				" ?entity gr:legalName ?legalname.\n" +
				" ?entity gr:category ?category.\n" +
				"        ?entity gr:category \""+categoria+"\"^^xsd:string. \n" +
				" }"
		);

		return getItems(queryString, label);
	}

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
				Arrays.asList("Loja", "Nome", "offering", "Teste", "price", "Qtd", "Produto"));
		String queryString = (prefix +
				"SELECT ?Loja ?Nome ?offering ?Teste ?price ?Qtd ?Produto\n" +
				"WHERE{\n" +
				"    ?Loja gr:offers ?offering.\n" +
				"    ?Loja gr:legalName \""+loja+"\"^^xsd:string.\n" +
				"    ?Loja gr:legalName ?Nome.\n" +
				"    \n" +
				"    ?Teste rdf:type gr:UnitPriceSpecification.\n" +
				"    ?offering gr:hasPriceSpecification ?Teste.\n" +
				"    ?Teste gr:hasCurrencyValue ?price. \n" +
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

	@CrossOrigin
	@GetMapping("/usuarioPorAccountName")
	public List<JSONObject> getUsuarioPorAccountName(@RequestParam String accountName) {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Nome", "Surname", "Account"));
		String queryString = (prefix +
				"SELECT ?Nome ?Surname ?Account\n" +
				"WHERE{\n" +
				"?Usuario rdf:type ccom:Cliente.\n" +
				"?Usuario foaf:name ?Nome.\n" +
				"?Usuario foaf:surname ?Surname.\n" +
				"?Usuario foaf:accountName ?Account.\n" +
				"?Usuario foaf:accountName \""+accountName+"\"^^xsd:string.\n" +
				"}"
		);

		return getItems(queryString, label);
	}


	@CrossOrigin
	@GetMapping("/inserirCarrinho")
	public List<JSONObject>  inserirCarrinho() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho"));

		String selectString = (prefix +
				"SELECT ?Carrinho\n" +
				"WHERE{\n" +
				" ?Carrinho rdf:type ccom:Carrinho.\n" +
				"}"
		);

		List<JSONObject> obj  = getItems(selectString, label);
		int size = obj.size() + 1;

		String queryString = (prefix +
				"INSERT DATA{\n" +
				" ccom:carrinho_"+size+" rdf:type ccom:Carrinho. \n" +
				"}"
		);

		execInsert(queryString);
		List<JSONObject> list = new ArrayList<>();
		 list.add(getItems(selectString, label).get(0));
		 return list;
	}

	@CrossOrigin
	@GetMapping("/inserirCarrinho1")
	public List<JSONObject>  inserirCarrinho1() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho"));

		String selectString = (prefix +
				"SELECT ?Carrinho\n" +
				"WHERE{\n" +
				" ?Carrinho rdf:type ccom:Carrinho.\n" +
				"}"
		);

		String queryString = (prefix +
				"INSERT DATA{\n" +
				" ccom:carrinho_1 rdf:type ccom:Carrinho. \n" +
				"}"
		);

		execInsert(queryString);
		List<JSONObject> list = new ArrayList<>();
		list.add(getItems(selectString, label).get(0));
		return list;
	}

	@CrossOrigin
	@GetMapping("/inserirOfertaNoCarrinho")
	public List<JSONObject> inserirOfertaNoCarrinho(@RequestParam String carrinho, String oferta ) {
		String queryString = (prefix +
				"INSERT DATA{\n" +
				"    ccom:"+carrinho+" ccom:contemItem ccom:"+oferta+".\n" +
				"}"
		);

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho", "Oferta", "Valor", "Item", "Produto"));

		String selectString = (prefix +
				"SELECT ?Carrinho ?Oferta ?Valor ?Item ?Produto\n" +
				"WHERE{\n" +
				"  ?Carrinho ccom:contemItem ?Oferta.\n" +
				"  ?Oferta gr:includesObject ?Valor.\n" +
				"  ?Valor gr:typeOfGood ?Item.\n" +
				"  ?Item gr:name ?Produto.\n" +
				"}"
		);

		execInsert(queryString);
		return getItems(selectString, label);
	}

	@CrossOrigin
	@GetMapping("/deletarOfertaNoCarrinho")
	public List<JSONObject> deletarOfertaNoCarrinho(@RequestParam String oferta ) {
		String queryString = (prefix +
				"DELETE WHERE{\n" +
				" ?Oferta ccom:contemItem ccom:"+oferta+".\n" +
				"}"
		);

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho", "Oferta", "Valor", "Item", "Produto"));

		String selectString = (prefix +
				"SELECT ?Carrinho ?Oferta ?Valor ?Item ?Produto\n" +
				"WHERE{\n" +
				"  ?Carrinho ccom:contemItem ?Oferta.\n" +
				"  ?Oferta gr:includesObject ?Valor.\n" +
				"  ?Valor gr:typeOfGood ?Item.\n" +
				"  ?Item gr:name ?Produto.\n" +
				"}"
		);

		execDelete(queryString);
		return getItems(selectString, label);
	}

	@CrossOrigin
	@GetMapping("/todosOsCarrinhosCompleto")
	public List<JSONObject> todosOsCarrinhosCompleto() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho", "Oferta", "Valor", "Item", "Produto"));

		String selectString = (prefix +
				"SELECT ?Carrinho ?Oferta ?Valor ?Item ?Produto\n" +
				"WHERE{\n" +
				"  ?Carrinho ccom:contemItem ?Oferta.\n" +
				"  ?Oferta gr:includesObject ?Valor.\n" +
				"  ?Valor gr:typeOfGood ?Item.\n" +
				"  ?Item gr:name ?Produto.\n" +
				"}"
		);

		return getItems(selectString, label);
	}

	@CrossOrigin
	@GetMapping("/todosOsCarrinhos")
	public List<JSONObject> todosOsCarrinhos() {

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho"));

		String selectString = (prefix +
				"SELECT ?Carrinho \n" +
				"WHERE{\n" +
				"  ?Carrinho rdf:type ccom:Carrinho.\n" +
				"}"
		);

		return getItems(selectString, label);
	}

	@CrossOrigin
	@GetMapping("/deletarTodosOsCarrinhos")
	public List<JSONObject> deletarTodosOsCarrinhos() {
		String queryString = (prefix +
				"DELETE WHERE{\n" +
				"?Carrinho rdf:type ccom:Carrinho.\n" +
				"}"
		);

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Carrinho", "Oferta", "Valor", "Item", "Produto"));

		String selectString = (prefix +
				"SELECT ?Carrinho \n" +
				"WHERE{ \n" +
				" ?Carrinho rdf:type ccom:Carrinho.\n" +
				"}"
		);

		execDelete(queryString);
		return getItems(selectString, label);
	}

	@CrossOrigin
	@GetMapping("/inserirObjetoCompra")
	public List<JSONObject> inserirObjetoCompra(@RequestParam String compra, String cliente, String carrinho ) {
		String queryString = (prefix +
				"INSERT DATA{\n" +
				" ccom:"+compra+" rdf:type ccom:Compras.\n" +
				" ccom:"+compra+" ccom:temComprador ccom:"+cliente+".\n" +
				" ccom:"+compra+" ccom:temItem ccom:"+carrinho+".\n" +
				"}"
		);

		ArrayList<String> label = new ArrayList<String>(
				Arrays.asList("Compras"));

		String selectString = (prefix +
				"SELECT ?Compras\n" +
				"WHERE{\n" +
				"  ?Compras rdf:type ccom:Compras.\n" +
				"}"
		);

		execInsert(queryString);
		return getItems(selectString, label);
	}

	public List<JSONObject> getItems(String queryString, ArrayList<String> label) {
		System.out.println(queryString);
		return InitJena.getItems(queryString, ontoFileEP, label);
	}

	public Boolean execInsert(String queryString) {
		System.out.println(queryString);
		return InitJena.execUpdate(queryString, ontoFileEP);
	}

	public Boolean execDelete(String queryString) {
		System.out.println(queryString);
		return InitJena.execUpdate(queryString, ontoFileEP);
	}
}
