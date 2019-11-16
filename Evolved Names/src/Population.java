import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Assignment #2, Evolved Names
 * TCSS 371
 * Instructor: Paulo Barreto
 * 
 * In this class we...
 * 
 * @author Roman Kucheryavyy, Rostislav Martsenyak
 * @version 4/26/19
 */

public class Population {

	Genome mostFit;
	static int counter = 0;
	List<Genome> myGenomePopulation; //lets us use sublist to split tree
	int numGenomes;
	
	Population(Integer numGenomes, Double mutationRate) {
			
			
			myGenomePopulation = new ArrayList<Genome>();	
			this.numGenomes = numGenomes;  
			
			for (int i = 0; i < numGenomes; i++) {
				
				Genome newGenome = new Genome(mutationRate);
				myGenomePopulation.add(i, newGenome);
			}
			
			mostFit = myGenomePopulation.get(0);
	}
	/**
	 * Method for the daily cycle.
	 */
	void day() {
		
		for (int i = 0; i < myGenomePopulation.size(); i ++) {
			
			if (mostFit.fitness() > myGenomePopulation.get(i).fitness()) {
				
				mostFit = myGenomePopulation.get(i);
				
			} 
				
		}
		Collections.sort(myGenomePopulation);
		
		myGenomePopulation = myGenomePopulation.subList(0, 50);
		Random rand = new Random();
		
		while (myGenomePopulation.size() != 100) {
			
			if(rand.nextInt(100) % 2 == 0) {
				Genome tempGenome = new Genome(myGenomePopulation.get(rand.nextInt(myGenomePopulation.size())));
				tempGenome.mutate();
				
				myGenomePopulation.add(new Genome(tempGenome));
				
			} 
			else {
				
				Genome tempGenome = new Genome(myGenomePopulation.get(rand.nextInt(myGenomePopulation.size())));
				Genome tempGenome2 = new Genome(myGenomePopulation.get(rand.nextInt(myGenomePopulation.size())));
				tempGenome.crossover(tempGenome2);
				tempGenome.mutate();
				myGenomePopulation.add(new Genome(tempGenome));
				
			}
		}
	
	}
}
