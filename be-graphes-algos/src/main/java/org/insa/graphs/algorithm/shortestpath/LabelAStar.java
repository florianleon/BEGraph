/**
 * 
 */
package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

/**
 * @author florianleon
 *
 */
public class LabelAStar extends Label {
	
	private double estimatedCost;

    public LabelAStar(Node currentNode) {
        super(currentNode);
        this.estimatedCost = Double.POSITIVE_INFINITY;
    }
    
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
    
    public double getEstimatedCost() {
        return estimatedCost;
    }
    
    
    public double getTotalCost() {
        return this.getCost() + this.getEstimatedCost();
    }
    
    
    public int compareTo(LabelAStar other) {
        return Double.compare(this.getTotalCost(), other.getTotalCost());
    }
    
    

	
}
