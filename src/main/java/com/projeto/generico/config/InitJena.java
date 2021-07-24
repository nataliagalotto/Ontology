package com.projeto.generico.config;

import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import java.io.InputStream;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.List;

public class InitJena {

    private static QueryExecution qe;

    public static ResultSet execQuery(String queryString, String ontoFile) {

        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open(ontoFile);
            try {
                ontoModel.read(in, null);

                Query query = QueryFactory.create(queryString);

                //Execute the query and obtain results
                qe = QueryExecutionFactory.create(query, ontoModel);
                ResultSet results = qe.execSelect();

                // Output query results
                //ResultSetFormatter.out(System.out, results, query);

                return results;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static List<JSONObject> getItems(String queryString, String ontoFile, String label) {
        ResultSet resultSet = execQuery(queryString, ontoFile);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            obj.put("item",solution.get(label).toString().split("#")[1]);
            list.add(obj);
        }

        // Important ‑ free up resources used running the query
        //qe.close();
        return list;
    }

    public static List<JSONObject> getItems(String queryString, String ontoFile, ArrayList<String> listLabel) {
        ResultSet resultSet = execQuery(queryString, ontoFile);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            for ( String label : listLabel) {
                obj.put(label, solution.get(label).toString()
                        .replace("^^<http://www.w3.org/2001/XMLSchema#string>","")
                        .replace("http://www.semanticweb.org/alefe/ontologies/2021/4/centro_comercial#","")
                        .replace("^^http://www.w3.org/2001/XMLSchema#float",""));
            }
            list.add(obj);
        }

        // Important ‑ free up resources used running the query
        //qe.close();
        return list;
    }

    public static List<JSONObject> getProducts(String queryString, String ontoFile, ArrayList<String> listLabel) {
        ResultSet resultSet = execQuery(queryString, ontoFile);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            for ( String label : listLabel) {
                obj.put(label, solution.get(label).toString()
                        .replace("^^<http://www.w3.org/2001/XMLSchema#string>","")
                        .replace("http://www.semanticweb.org/alefe/ontologies/2021/4/centro_comercial#","")
                        .replace("^^http://www.w3.org/2001/XMLSchema#float",""));
            }
            obj.put("img","ulmart_img.jpg");
            list.add(obj);
        }

        // Important ‑ free up resources used running the query
        //qe.close();
        return list;
    }

    public static String getProducts(String produto) {
        switch (produto){
            case "Uaumart" :
                   return "com/projeto/generico/ulmart_img.jpg";
            default:

        }

    }
}
