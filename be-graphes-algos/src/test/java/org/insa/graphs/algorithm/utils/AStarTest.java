/**
 * 
 */
package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
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
public class AStarTest {
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
        this.carre = readGraph("/Users/florianleon/Desktop/Cartes/carre.mapgr");
        this.insa = readGraph("/Users/florianleon/Desktop/Cartes/insa.mapgr");
        this.reunion = readGraph("/Users/florianleon/Desktop/Cartes/reunion.mapgr");
    }
    
	
	@Test
    public void testRandom() {
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
	        	destCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size())-1);
	            destInsa = nodesInsa.get(atRandom.nextInt(nodesCarre.size())-1);
	            destReunion = nodesReunion.get(atRandom.nextInt(nodesCarre.size())-1);
	        } while (origCarre == destCarre || origInsa == destInsa || origReunion == destReunion);
	        
	        for (ArcInspector arcInspector : arcInspectors) {
	        	//carre
	            ShortestPathData dataCarre = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
	            ShortestPathSolution solCarre = new AStarAlgorithm(dataCarre).doRun();
	            if (solCarre.isFeasible()) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new AStarAlgorithm(dataInsa).doRun();
	            if (solInsa.isFeasible()) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new AStarAlgorithm(dataReunion).doRun();
	            if (solReunion.isFeasible()) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	                
	        }
	        
	        
	        /**
	    	 * On teste un chemin de longueur 0
	    	 */
	        origInsa = nodesInsa.get(atRandom.nextInt(nodesInsa.size()));
            destInsa = origInsa;
            for (ArcInspector arcInspector2 : arcInspectors) {
                ShortestPathData data2 = new ShortestPathData(insa, origInsa, destInsa, arcInspector2);
                ShortestPathSolution sol2 = new AStarAlgorithm(data2).doRun();
                if (sol2.isFeasible()) {
                	assertTrue(sol2.getPath().isValid());
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
	            ShortestPathSolution solCarre = new AStarAlgorithm(dataCarre).doRun();
	            ShortestPathSolution solCarreBellman = new BellmanFordAlgorithm(dataCarre).doRun();
	            if (solCarre == solCarreBellman) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new AStarAlgorithm(dataInsa).doRun();
	            ShortestPathSolution solInsaBellman = new BellmanFordAlgorithm(dataInsa).doRun();
	            if (solInsa == solInsaBellman) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new AStarAlgorithm(dataReunion).doRun();
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
    public void testDijkstra() {
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
	            ShortestPathSolution solCarre = new AStarAlgorithm(dataCarre).doRun();
	            ShortestPathSolution solCarreDijkstra = new DijkstraAlgorithm(dataCarre).doRun();
	            if (solCarre == solCarreDijkstra) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new AStarAlgorithm(dataInsa).doRun();
	            ShortestPathSolution solInsaDijkstra = new DijkstraAlgorithm(dataInsa).doRun();
	            if (solInsa == solInsaDijkstra) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new AStarAlgorithm(dataReunion).doRun();
	            ShortestPathSolution solReunionDijkstra = new DijkstraAlgorithm(dataReunion).doRun();
	            if (solReunion == solReunionDijkstra) {
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
	            ShortestPathSolution solCarre = new AStarAlgorithm(dataCarre).doRun();
	            List<Node> pathNodesCarre = new ArrayList<Node>();
	            for (Arc arc : solCarre.getPath().getArcs()) {
	            	pathNodesCarre.add(arc.getOrigin());
	            }
	            int lastIndexCarre = solCarre.getPath().getArcs().size();
	            pathNodesCarre.add(solCarre.getPath().getArcs().get(lastIndexCarre-1).getDestination());
	            Path solCarrePath;
	            if (arcInspector == arcInspectors[0]) {
	            	solCarrePath = Path.createShortestPathFromNodes(carre, pathNodesCarre);
	            } else {
	            	solCarrePath = Path.createFastestPathFromNodes(carre, pathNodesCarre);
	            }
	            if (solCarre.getPath() == solCarrePath) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            
	            //reunion
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            ShortestPathSolution solReunion = new AStarAlgorithm(dataReunion).doRun();
	            List<Node> pathNodesReunion = new ArrayList<Node>();
	            for (Arc arc : solReunion.getPath().getArcs()) {
	            	pathNodesReunion.add(arc.getOrigin());
	            }
	            int lastIndexReunion = solReunion.getPath().getArcs().size();
	            pathNodesReunion.add(solReunion.getPath().getArcs().get(lastIndexReunion-1).getDestination());
	            Path solReunionPath;
	            if (arcInspector == arcInspectors[0]) {
	            	solReunionPath = Path.createShortestPathFromNodes(reunion, pathNodesReunion);
	            } else {
	            	solReunionPath = Path.createFastestPathFromNodes(reunion, pathNodesReunion);
	            }
	            if (solReunion.getPath() == solReunionPath) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            ShortestPathSolution solInsa = new AStarAlgorithm(dataInsa).doRun();
	            List<Node> pathNodesInsa = new ArrayList<Node>();
	            for (Arc arc : solInsa.getPath().getArcs()) {
	            	pathNodesInsa.add(arc.getOrigin());
	            }
	            int lastIndexInsa = solInsa.getPath().getArcs().size();
	            pathNodesInsa.add(solInsa.getPath().getArcs().get(lastIndexInsa-1).getDestination());
	            Path solInsaPath;
	            if (arcInspector == arcInspectors[0]) {
	            	solInsaPath = Path.createShortestPathFromNodes(insa, pathNodesInsa);
	            } else {
	            	solInsaPath = Path.createFastestPathFromNodes(insa, pathNodesInsa);
	            }
	            if (solInsa.getPath() == solInsaPath) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	                
	        }

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
