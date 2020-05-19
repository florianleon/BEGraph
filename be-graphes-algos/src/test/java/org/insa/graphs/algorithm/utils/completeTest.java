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
public class completeTest {
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
    private Graph HG;
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
        this.HG = readGraph("/Users/florianleon/Desktop/Cartes/haute-garonne.mapgr");
    }
    
	
	@Test
    public void testComplet() {
        try {
			initGraphs();
			System.out.println("\n *********************************************** \n");
	        List<Node> nodesCarre = carre.getNodes();
	        Node origCarre;
	        Node destCarre;
	        List<Node> nodesInsa = insa.getNodes();
	        Node origInsa;
	        Node destInsa;
	        List<Node> nodesReunion = reunion.getNodes();
	        Node origReunion;
	        Node destReunion;
	        List<Node> nodesHG = HG.getNodes();
	        Node origHG;
	        Node destHG;
	        //pour pouvoir prendre un node au hasard la liste (ça marche pas sans…)
	        Random atRandom = new Random();
	        
	        long lStartTimeAStar;
	        long lEndTimeAStar;
	        long outputAStar;
	        long lStartTimeDijkstra;
	        long lEndTimeDijkstra;
	        long outputDijkstra;
	        long lStartTimeBellman;
	        long lEndTimeBellman;
	        long outputBellman;
	        
	        /**
	    	 * On teste des chemins au hasard
	    	 */
	        origCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size()));
	        origInsa = nodesInsa.get(atRandom.nextInt(nodesInsa.size()));
	        origReunion = nodesReunion.get(atRandom.nextInt(nodesReunion.size()));
	        origHG = nodesHG.get(atRandom.nextInt(nodesHG.size()));
	        
	        //c'est rare mais ça peut arriver mais c'est "rare" que orig = dest
	        //alors on utilise un do while
	        do {
	        	destCarre = nodesCarre.get(atRandom.nextInt(nodesCarre.size())-1);
	            destInsa = nodesInsa.get(atRandom.nextInt(nodesInsa.size())-1);
	            destReunion = nodesReunion.get(atRandom.nextInt(nodesReunion.size())-1);
	            destHG = nodesHG.get(atRandom.nextInt(nodesHG.size())-1);
	        } while (origCarre == destCarre || origInsa == destInsa || origReunion == destReunion  || origHG == destHG);
	        
	        for (ArcInspector arcInspector : arcInspectors) {
	        	//carre
	            ShortestPathData dataCarre = new ShortestPathData(carre, origCarre, destCarre, arcInspector);
	            lStartTimeAStar = System.currentTimeMillis();
	            ShortestPathSolution solCarre = new AStarAlgorithm(dataCarre).doRun();
	            lEndTimeAStar = System.currentTimeMillis();
	            outputAStar = lEndTimeAStar - lStartTimeAStar;
	            lStartTimeDijkstra = System.currentTimeMillis();
	            ShortestPathSolution solCarreDijkstra = new DijkstraAlgorithm(dataCarre).doRun();
	            lEndTimeDijkstra = System.currentTimeMillis();
	            outputDijkstra = lEndTimeDijkstra - lStartTimeDijkstra;
	            lStartTimeBellman = System.currentTimeMillis();
	            ShortestPathSolution solCarreBellman = new BellmanFordAlgorithm(dataCarre).doRun();
	            lEndTimeBellman = System.currentTimeMillis();
	            outputBellman = lEndTimeBellman - lStartTimeBellman;
	            if (solCarre == solCarreDijkstra && solCarre == solCarreBellman) {
	            	assertTrue(solCarre.getPath().isValid());
	            }
	            System.out.println("Carre : " + arcInspector + "\n");
	            System.out.println("AStar: " + outputAStar + " ms \n");
	            System.out.println("Dijkstra: " + outputDijkstra + " ms \n");
	            System.out.println("Bellman: " + outputBellman + " ms \n");
	            System.out.println('\n');
	            
	            //insa
	            ShortestPathData dataInsa = new ShortestPathData(insa, origInsa, destInsa, arcInspector);
	            lStartTimeAStar = System.currentTimeMillis();
	            ShortestPathSolution solInsa = new AStarAlgorithm(dataInsa).doRun();
	            lEndTimeAStar = System.currentTimeMillis();
	            outputAStar = lEndTimeAStar - lStartTimeAStar;
	            lStartTimeDijkstra = System.currentTimeMillis();
	            ShortestPathSolution solInsaDijkstra = new DijkstraAlgorithm(dataInsa).doRun();
	            lEndTimeDijkstra = System.currentTimeMillis();
	            outputDijkstra = lEndTimeDijkstra - lStartTimeDijkstra;
	            lStartTimeBellman = System.currentTimeMillis();
	            ShortestPathSolution solInsaBellman = new BellmanFordAlgorithm(dataInsa).doRun();
	            lEndTimeBellman = System.currentTimeMillis();
	            outputBellman = lEndTimeBellman - lStartTimeBellman;
	            
	            if (solInsa == solInsaDijkstra && solInsa == solInsaBellman) {
	            	assertTrue(solInsa.getPath().isValid());
	            }
	            System.out.println("Insa : " + arcInspector + "\n");
	            System.out.println("AStar: " + outputAStar + " ms \n");
	            System.out.println("Dijkstra: " + outputDijkstra + " ms \n");
	            System.out.println("Bellman: " + outputBellman + " ms \n");
	            System.out.println('\n');
	            //reunion
	            
	            ShortestPathData dataReunion = new ShortestPathData(reunion, origReunion, destReunion, arcInspector);
	            lStartTimeAStar = System.currentTimeMillis();
	            ShortestPathSolution solReunion = new AStarAlgorithm(dataReunion).doRun();
	            lEndTimeAStar = System.currentTimeMillis();
	            outputAStar = lEndTimeAStar - lStartTimeAStar;
	            lStartTimeDijkstra = System.currentTimeMillis();
	            ShortestPathSolution solReunionDijkstra = new DijkstraAlgorithm(dataReunion).doRun();
	            lEndTimeDijkstra = System.currentTimeMillis();
	            outputDijkstra = lEndTimeDijkstra - lStartTimeDijkstra;
	            lStartTimeBellman = System.currentTimeMillis();
	            ShortestPathSolution solReunionBellman = new BellmanFordAlgorithm(dataReunion).doRun();
	            lEndTimeBellman = System.currentTimeMillis();
	            outputBellman = lEndTimeBellman - lStartTimeBellman;
	            if (solReunion == solReunionDijkstra && solReunion == solReunionBellman) {
	            	assertTrue(solReunion.getPath().isValid());
	            }
	            System.out.println("Reunion : " + arcInspector + "\n");
	            System.out.println("AStar: " + outputAStar + " ms \n");
	            System.out.println("Dijkstra: " + outputDijkstra + " ms \n");
	            System.out.println("Bellman: " + outputBellman + " ms \n");
	            System.out.println('\n');
	            
	            //haute-Garonne
	            
	            ShortestPathData dataHG = new ShortestPathData(HG, origHG, destHG, arcInspector);
	            lStartTimeAStar = System.currentTimeMillis();
	            ShortestPathSolution solHG = new AStarAlgorithm(dataHG).doRun();
	            lEndTimeAStar = System.currentTimeMillis();
	            outputAStar = lEndTimeAStar - lStartTimeAStar;
	            ShortestPathSolution solHGDijkstra = new DijkstraAlgorithm(dataHG).doRun();
	            lEndTimeDijkstra = System.currentTimeMillis();
	            outputDijkstra = lEndTimeDijkstra - lStartTimeDijkstra;
	            lStartTimeBellman = System.currentTimeMillis();
	            ShortestPathSolution solHGBellman = new BellmanFordAlgorithm(dataHG).doRun();
	            lEndTimeBellman = System.currentTimeMillis();
	            outputBellman = lEndTimeBellman - lStartTimeBellman;
	            if (solHG == solHGDijkstra && solHG == solHGBellman) {
	            	assertTrue(solHG.getPath().isValid());
	            }
	            System.out.println("Haute-Garonne : " + arcInspector + "\n");
	            System.out.println("AStar: " + outputAStar + " ms \n");
	            System.out.println("Dijkstra: " + outputDijkstra + " ms \n");
	            System.out.println("Bellman: " + outputBellman + " ms \n");
	            System.out.println('\n');
	                
	        }

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	

}
