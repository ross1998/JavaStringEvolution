import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Assignment #2, Evolved Names
 * TCSS 371
 * Instructor: Paulo Barreto
 * 
 * 
 * In this class we initiate the simulation by calling the population of 100 genomes and mutation rate of some sort, and calling day untill
 * our most fit genome has a fitness of 0, then we print out the necessary outputs to the console including generation, time.
 * 
 * @author Roman Kucheryavyy, Rostislav Martsenyak
 * @version 4/26/19
 */

public class Main {
	
	private static Genome testingGenome;
	private int myFitness;
	private static int myGenerations;
	private static int runningTime;

	public static void main(String[] args) {
		
		final long startingTime = System.nanoTime();
		
		myGenerations = 0;
		runningTime = 0;
		
		Population myPopulation = new Population(100, 0.05); //DEFAULT VALUES mentioned in the slides 100 population and 5% mutation rate.
		
		while (myPopulation.mostFit.fitness() != 0) {
			
			
			
			myPopulation.day();
			myGenerations++;
		    System.out.println(myPopulation.mostFit.toString());
			//System.out.println("(" + myPopulation.myGenomePopulation.get(0) + ", " + myPopulation.mostFit.fitness() + ")");
			
		}
		
		System.out.println("Generations: " + myGenerations);
		//Calculating the time, learned this technique from stack overflow comments
		final long nanoTime = System.nanoTime() - startingTime;
		long milliseconds = TimeUnit.MILLISECONDS.convert(nanoTime, TimeUnit.NANOSECONDS);
		System.out.println("Running time: " + milliseconds + " milliseconds");
		//testGenome();
		//testPopulation();
	


	}
	
	static void testGenome(){
		
		Genome myGenome = new Genome(.05);
		Genome testGenome = new Genome(.05);
		System.out.println("Original: " + myGenome);
		myGenome.addRandomCharacter();
		myGenome.addRandomCharacter();
		System.out.println("Adding: " + myGenome);
		myGenome.removeRandomCharacter();
		System.out.println("Removing: "+ myGenome);
		myGenome.replaceRandomCharacter(1);
		System.out.println("Replacing: " + myGenome);
		myGenome.mutate();
		System.out.println("Mutation: " + myGenome);
		myGenome.crossover(testGenome);
		System.out.println(myGenome);
	}
	
	static void testPopulation() {
		
		Population testPopulation = new Population(100, 0.5);
		System.out.println("MostFit: " + testPopulation.mostFit);
		testPopulation.day();
		testPopulation.day();
		testPopulation.day();
		System.out.println("MostFit: " + testPopulation.mostFit);

	}

}
