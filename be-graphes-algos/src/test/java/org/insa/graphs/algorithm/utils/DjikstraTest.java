/**
 * 
 */
package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;


/**
 * @author florianleon
 *
 */
@SuppressWarnings("unused")
public class DjikstraTest {
	/**
     * 
	 * Point technique : vous aurez besoin d’un objet ArcInspector. 
	 * Pour l’obtenir, vous pouvez invoquer la méthode getAllFilters() de ArcInspectorFactory et 
	 * prendre le premier de la liste puis le troisième.
     */    

    private ArcInspector[] arcInspectors = {
            ArcInspectorFactory.getAllFilters().get(0), //length
            ArcInspectorFactory.getAllFilters().get(2), //time
    };
    
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
        this.carre = readGraph("/Users/florianleon/Desktop/carre.mapgr");
        this.insa = readGraph("/Users/florianleon/Desktop/insa.mapgr");
        this.reunion = readGraph("/Users/florianleon/Desktop/reunion.mapgr");
    }
    

    
    //ShortestPathData(Graph graph, Node origin, Node destination, ArcInspector arcInspector)
	
	
	@Test
    public void test() {
        try {
			initGraphs();
		
	        List<Node> nodesCarre = carre.getNodes();
	        Node origCarre;
	        Node destCarre;
	        List<Node> nodesInsa = insa.getNodes();
	        Node origInsa;
	        Node destInsa;
	        List<Node> nodesReunion = reunion.getNodes();
	        Node origReunion;
	        Node destReunion;
	        //pour pouvoir prendre un node au hasard la liste (ça marche pas sans…)
	        Random atRandom = new Random();
	        
	        /**
	    	 * On teste des chemins au hasard
	    	 */
	        origCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	        origInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	        origReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        
	        //c'est rare mais ça peut arriver mais c'est "rare" que orig = dest
	        //alors on utilise un do while
	        do {
	        	destCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	            destInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	            destReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        } while (origCarre == destCarre || origInsa == destInsa || origReunion == destReunion);
	        
	        for (ArcInspector arcInspector : arcInspectors) {
	        	//carre
	            ShortestPathData dataCarre = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
	            ShortestPathSolution solCarre = new DijkstraAlgorithm(dataCarre).doRun();
	            //on pourrait peut etre le comparer à Bellmann)Ford aussi je pense
	            if (solCarre.isFeasible()) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new DijkstraAlgorithm(dataInsa).doRun();
	            //on pourrait peut etre le comparer à Bellmann)Ford aussi je pense
	            if (solInsa.isFeasible()) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new DijkstraAlgorithm(dataReunion).doRun();
	            //on pourrait peut etre le comparer à Bellmann)Ford aussi je pense
	            if (solReunion.isFeasible()) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	                
	        }
	        
	        
	        /**
	    	 * On teste un chemin de longueur 0
	    	 */
	        origCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
            destCarre = origCarre;
            for (ArcInspector arcInspector : arcInspectors) {
                ShortestPathData data = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
                ShortestPathSolution sol = new DijkstraAlgorithm(data).doRun();
                if (sol.isFeasible()) {
                	assertTrue(sol.getPath().isValid());
                }
            
            /**
	    	 * On teste un chemin inexistant
	    	 */
	        origCarre = null;
            destCarre = origCarre;
            for (ArcInspector arcInspector2 : arcInspectors) {
                ShortestPathData data2 = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
                ShortestPathSolution sol2 = new DijkstraAlgorithm(data).doRun();
                if (sol2.isFeasible()) {
                	assertTrue(sol2.getPath().isValid());
                }
            }
           }
  
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
  
        
    }
	
	
	
	@Test
    public void testBellman() {
        try {
			initGraphs();
		
	        List<Node> nodesCarre = carre.getNodes();
	        Node origCarre;
	        Node destCarre;
	        List<Node> nodesInsa = insa.getNodes();
	        Node origInsa;
	        Node destInsa;
	        List<Node> nodesReunion = reunion.getNodes();
	        Node origReunion;
	        Node destReunion;
	        //pour pouvoir prendre un node au hasard la liste (ça marche pas sans…)
	        Random atRandom = new Random();
	        
	        /**
	    	 * On teste des chemins au hasard
	    	 */
	        origCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	        origInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	        origReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        
	        //c'est rare mais ça peut arriver mais c'est "rare" que orig = dest
	        //alors on utilise un do while
	        do {
	        	destCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	            destInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	            destReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        } while (origCarre == destCarre || origInsa == destInsa || origReunion == destReunion);
	        
	        for (ArcInspector arcInspector : arcInspectors) {
	        	//carre
	            ShortestPathData dataCarre = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
	            ShortestPathSolution solCarre = new DijkstraAlgorithm(dataCarre).doRun();
	            ShortestPathSolution solCarreBellman = new BellmanFordAlgorithm(dataCarre).doRun();
	            if (solCarre == solCarreBellman) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new DijkstraAlgorithm(dataInsa).doRun();
	            ShortestPathSolution solInsaBellman = new BellmanFordAlgorithm(dataInsa).doRun();
	            if (solInsa == solInsaBellman) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new DijkstraAlgorithm(dataReunion).doRun();
	            ShortestPathSolution solReunionBellman = new BellmanFordAlgorithm(dataReunion).doRun();
	            if (solReunion == solReunionBellman) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	                
	        }

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	@Test
    public void testPath() {
        try {
			initGraphs();
		
	        List<Node> nodesCarre = carre.getNodes();
	        Node origCarre;
	        Node destCarre;
	        List<Node> nodesInsa = insa.getNodes();
	        Node origInsa;
	        Node destInsa;
	        List<Node> nodesReunion = reunion.getNodes();
	        Node origReunion;
	        Node destReunion;
	        //pour pouvoir prendre un node au hasard la liste (ça marche pas sans…)
	        Random atRandom = new Random();
	        
	        /**
	    	 * On teste des chemins au hasard
	    	 */
	        origCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	        origInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	        origReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        
	        //c'est rare mais ça peut arriver mais c'est "rare" que orig = dest
	        //alors on utilise un do while
	        do {
	        	destCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	            destInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size()));
	            destReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size()));
	        } while (origCarre == destCarre || origInsa == destInsa || origReunion == destReunion);
	        
	        for (ArcInspector arcInspector : arcInspectors) {
	        	//carre
	            ShortestPathData dataCarre = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
	            ShortestPathSolution solCarre = new DijkstraAlgorithm(dataCarre).doRun();
	            Path solCarrePath = Path.createShortestPathFromNodes(carre, nodesCarre);
	            if (solCarre.getPath() == solCarrePath) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new DijkstraAlgorithm(dataInsa).doRun();
	            Path solInsaPath = Path.createShortestPathFromNodes(insa, nodesInsa);
	            if (solInsa.getPath() == solInsaPath) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new DijkstraAlgorithm(dataReunion).doRun();
	            Path solReunionPath = Path.createShortestPathFromNodes(reunion, nodesInsa);
	            if (solReunion.getPath() == solReunionPath) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	                
	        }

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
