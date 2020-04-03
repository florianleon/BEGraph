/**
 * 
 */
package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

/**
 * @author florianleon
 *
 */
public class Label implements Comparable<Label>{
	//sommet associé à ce label (sommet ou numéro de sommet)
	private Node currentNode;
	//booléen, vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	private boolean marked;
	//valeur courante du plus court chemin depuis l'origine vers le sommet
	private double cost;
	//correspond au sommet précédent sur le chemin correspondant au plus court chemin courant
	/*
	*private Node pere;
	*\//Afin de reconstruire le chemin à la fin de l'algorithme, mieux vaut stocker l'arc plutôt que seulement le père
	*private Arc arc;
	*/
	//ca serait plus logique d'avoir l'arc du pere je pense
	private Arc arcFather;
	
	public Label(Node node) {
		this.currentNode = node;
		//this.pere = null;
		this.cost = Double.POSITIVE_INFINITY;
		//this.arc = null;	
		this.arcFather = null;
		this.marked = false;
	}
	
	/**
     * Get the currentNode of the current label
     * @return the Node of the Label
     */
    public Node getNode() {
        return this.currentNode;
    }
    
    /**
     * Changer le coût du Label
     * @param newCost : nouveau coût
     */
    public void setCost(double newCost) {
        this.cost = newCost;
    }
    
    /**
     * @return le coût du label
     */
    public double getCost() {
        return this.cost;
    }
    
    /**
     * 
     */
    public void setMarked() {
    	this.marked = true;
    }
    
    
    /**
     * @return si le Label à été visité 
     */
    public boolean isMarked() {
    	return this.marked == true;
    }
    
    
    /**
     * Pour fixer l'arc du pere
     * @param arcFather
     */
    public void setArcFather(Arc arcFather) {
        this.arcFather = arcFather;
    }
    
    /**
     * @return Arc : arc du pere
     */
    public Arc getArcFather() {
        return arcFather;
    }

    
    @Override
    public int compareTo(Label label) {
        return Double.compare(this.cost, label.getCost()) ;
    }
    
	
	
}
