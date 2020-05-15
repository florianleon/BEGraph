package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

//test
public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    
    @Override
    public ShortestPathSolution doRun() {
    	
    	final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        LabelAStar[] labels = new LabelAStar[nbNodes];
        
        double costEstimated;
        
        //on distingue le type de trajet en temps puis en distance
        for(Node node : graph.getNodes()) {
        	
        	labels[node.getId()] = new LabelAStar(node);
        	if(data.getMode() == AbstractInputData.Mode.TIME) {
        		//fastest
        		costEstimated = node.getPoint().distanceTo(data.getDestination().getPoint()) / (double)data.getMaximumSpeed();
        	} else {
        		//shortest
        		costEstimated = node.getPoint().distanceTo(data.getDestination().getPoint());
        	}
        	
        	
        	labels[node.getId()].setEstimatedCost(costEstimated);
        	
        }
    	
        
        return djikstraRun(labels, data, graph);
        
    }

}
