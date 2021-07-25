package com.projeto.generico.config;

import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.*;
import org.apache.jena.util.FileManager;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InitJena {

    private static RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
            .destination("http://localhost:3030/centro_comercial");

    public static ResultSet execQueryFuseki(String queryString, String ontoFile) {
        // In this variation, a connection is built each time.
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(queryString);
            return qExec.execSelect() ;
        }
    }

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

    public static Boolean execInsert2(String queryString, String ontoFile) {
        try {
            OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            ontoModel.read(ontoFile,"RDF/XML");

            UpdateRequest update  = UpdateFactory.create(queryString);
            UpdateProcessor processor = UpdateExecutionFactory.create(update, GraphStoreFactory.create(ontoModel));
            processor.execute();

            ontoModel.write(new FileOutputStream(ontoFile), "RDF/XML");
        } catch (Exception e) {
            System.err.println("ERROR" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return true;
    }

    public static Boolean execInsert(String queryString, String ontoFile) {
        // In this variation, a connection is built each time.
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {
            UpdateRequest request = UpdateFactory.create();

            request.add(queryString);
            conn.update(request);
        } catch (Exception e) {
            System.err.println("ERROR" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        return true;
    }

    public static Boolean execDelete(String queryString, String ontoFile) {
        // In this variation, a connection is built each time.
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {
            UpdateRequest request = UpdateFactory.create();

            request.add(queryString);
            conn.update(request);
        }
        return true;
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
        return list;
    }
}
