package org.insa.graphs.algorithm.shortestpath;

import java.util.Arrays;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

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
        labels[origin.getId()].setCost(0);
        
        //initialisation du tas
        BinaryHeap<Label> bheap = new BinaryHeap<>();
        
        //on insere origin dans le tas
        bheap.insert(labels[origin.getId()]);
        
        //to be continued…
        
        
        
        
        
        
        
        
        
        
        
        
        boolean found = false;
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        return solution;
    }

}
