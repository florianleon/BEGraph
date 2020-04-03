package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        // Initialize array of distances.
        double[] distances = new double[nbNodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[data.getOrigin().getId()] = 0;

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());

        // Initialize array of predecessors.
        Arc[] predecessorArcs = new Arc[nbNodes];
        
        // Actual algorithm, we will assume the graph does not contain negative
        // cycle...
        Label[] labels = new Label[nbNodes];
        List<Node> nodes = graph.getNodes();
        //l'initialisation du cours est faite dans la classe label
        for (int i = 0; i < nbNodes; i++) {
            labels[i] = new Label(nodes.get(i));
        }
        //pour commencer l'algo il faut mettre le cout de notre 
        //sommet de depart à 0
        final Node origin = data.getOrigin();
        final Node destination = data.getDestination();
        final Label originLabel = labels[data.getOrigin().getId()];
        final Label destinationLabel = labels[data.getDestination().getId()];
        labels[origin.getId()].setCost(0);
        
        //initialisation du tas
        BinaryHeap<Label> bheap = new BinaryHeap<>();
        
        //on insere origin dans le tas
        bheap.insert(labels[origin.getId()]);
        
        //to be continued…
        int compteur = 0;
        Label x = null;
        
        while (!labels[destination.getId()].isMarked() && !bheap.isEmpty()) {
        	compteur++;
        	x = bheap.deleteMin();
        	x.setMarked();
        	notifyNodeMarked(x.getNode());
        	
        	List<Arc> successorsList = x.getNode().getSuccessors();
        	
        	for(Arc succ : successorsList) {
        		
        		Label y = labels[succ.getDestination().getId()];
        		notifyNodeReached(succ.getDestination());
        		
        		if((!y.isMarked()) && data.isAllowed(succ)) {
        			
        			if(y.getCost() > x.getCost() + data.getCost(succ)) {
        				
        				//double yCost = x.getCost() + data.getCost(succ);
        				
        				double formerCost;
        		    	double newCost;
        		    	Arc arcFather;
        		    	arcFather = succ;
        		    	formerCost = y.getCost();
        		    	newCost = x.getCost() + data.getCost(arcFather);
        		    	
        		    	if(newCost < formerCost) {
        		    		/*
        		    		 * Si l'element existe dans le tas on l'enleve
        		    		 * s'il existe pas on catch l'erreur et on ne fait rien et du coup on insere dans le tas
        		    		 */
        		    		try {
        		    			bheap.remove(y);
        		    		} catch(ElementNotFoundException e) {
        		    			
        		    		}
        		    		y.setCost(x.getCost() + arcFather.getLength());
        		    		y.setArcFather(arcFather);;
        		    		bheap.insert(y);
        		    	}
        				
        			}	
        		}
        	}
        }
        
        if(destinationLabel.isMarked() == false) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {
        	ArrayList<Arc> arcs = new ArrayList<Arc>();
        	while(!x.equals(originLabel)) {
        		arcs.add(x.getArcFather());
        		x = labels[x.getArcFather().getOrigin().getId()];
        	}
            Collections.reverse(arcs);
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            
        }
        
        
     
        
        return solution;
    }

    
    
    
}
