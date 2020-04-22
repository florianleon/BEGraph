/**
 * 
 */
package org.insa.graphs.algorithm.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;


/**
 * @author florianleon
 *
 */
public class DjikstraTest {
	/**
     * 
	 * Point technique : vous aurez besoin d’un objet ArcInspector. 
	 * Pour l’obtenir, vous pouvez invoquer la méthode getAllFilters() de ArcInspectorFactory et 
	 * prendre le premier de la liste puis le troisième.
     */    
    private ArcInspector arcFilterLength, arcFilterTime;
    private void ArcInspectorInit() {
    	arcFilterLength = ArcInspectorFactory.getAllFilters().get(0);
    	arcFilterTime = ArcInspectorFactory.getAllFilters().get(2);
    }
    
   /**
    * On va tester l'algo sur 3 cartes : 
    * carré, insa et reunion
    */
    private Graph carre;
    private Graph insa;
    private Graph reunion;
    //je sais pas comment les créer pour l'instant
    
    private Graph readGraph(String path) throws IOException {
    	try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(path))))) {
            return reader.read();
    	}
    }
    
	private void initGraphs() throws IOException {
        carre = readGraph("/Users/florianleon/Desktop/carre.mapgr");
        insa = readGraph("/Users/florianleon/Desktop/insa.mapgr");
        reunion = readGraph("/Users/florianleon/Desktop/reunion.mapgr");
    }
    

    
    //ShortestPathData(Graph graph, Node origin, Node destination, ArcInspector arcInspector)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
