import java.util.ArrayList;
import java.util.Random;

/**
 * Assignment #2, Evolved Names
 * TCSS 371
 * Instructor: Paulo Barreto
 * 4/26/19
 * 
 * In this class we structure and create the necessary actions that happen when a genome mutates, as well as implementing the calculating fitness algorithm.
 * 
 * @author Roman Kucheryavyy, Rostislav Martsenyak
 * @version 
 */

public class Genome implements Comparable<Genome> {
	
	static Character[]  chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','S','X','Y','Z',' ','-','\''};
	static String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";
	private StringBuilder currentStringBuilder = new StringBuilder("A");
	double mutationRate;
	Population population;
	
	public Genome (double mutationRate){
		
		this.mutationRate = mutationRate;
		
	}
	
	Genome(Genome gene){
		
		this.mutationRate = gene.mutationRate;
		this.currentStringBuilder = new StringBuilder(gene.currentStringBuilder);
		
		
	}
	/**
	 * Additional method to add a random character if needed once called in the mutate method.
	 */
	void addRandomCharacter() {
		
		Random randomNumber = new Random();
		int characterIndex = randomNumber.nextInt(29);
		
		int location = randomNumber.nextInt(currentStringBuilder.length() + 1);

		if (location < currentStringBuilder.length()) {
			
			currentStringBuilder.insert(location, chars[characterIndex]);
			
		} else {
			
			currentStringBuilder.insert(currentStringBuilder.length(), chars[characterIndex]);
		
			
		}
	}
	/**
	 * Additional method to remove a random character if called in our mutate method.
	 */
	void removeRandomCharacter() {

		Random randomNumber = new Random();
		
		if(currentStringBuilder.length() > 1) {
			int location = randomNumber.nextInt(currentStringBuilder.length());
			

			currentStringBuilder.deleteCharAt(location);
		}
		
	}
	/**
	 * Additional method to replace a random character if called in our mutate method.
	 * @param location
	 */
	void replaceRandomCharacter(int location) {
		
		Random randomNumber = new Random();
		
		int characterIndex = randomNumber.nextInt(chars.length);
		
		if(currentStringBuilder.length() != 0) {
			
			currentStringBuilder.setCharAt(location, chars[characterIndex]);
		}
	}
	
//	In this method we mutate genomes, be either adding random characters, swapping, or removing in correspondence to the mutationRate percent.
	void mutate() {
		
		Random randomNumber = new Random();
		
		if(mutationRate >= randomNumber.nextDouble() ) {
			
			addRandomCharacter();
			
		} 
		if (mutationRate >= randomNumber.nextDouble()) {
			
			removeRandomCharacter();
			
		} 
		
		for(int i = 0; i < currentStringBuilder.length(); i++) {
			
			if (mutationRate >= randomNumber.nextDouble()) {
				
				replaceRandomCharacter(i);
			}
		}
		
		
	}
	
	//Crossover one genome with another using the guidelines mentioned in the rubric.	 
	void crossover(Genome other) {
		
		Random randomNumber = new Random();
		int condition;
		StringBuilder longestStringBuilder = new StringBuilder();
		int min = Math.abs(currentStringBuilder.length() - other.currentStringBuilder.length());
		
		if (this.currentStringBuilder.length() > other.currentStringBuilder.length()) {
			
			condition = this.currentStringBuilder.length();
			longestStringBuilder = this.currentStringBuilder;
			
		} else {
			
			condition = other.currentStringBuilder.length();
			longestStringBuilder = other.currentStringBuilder;
			
		}
		
		for (int i = 0; i < condition - min; i ++) {
			if (randomNumber.nextInt() % 2 == 0) {
				
			} else {
				
				this.currentStringBuilder.setCharAt(i, other.currentStringBuilder.charAt(i));
			}
		}
		
		if(longestStringBuilder.length() != currentStringBuilder.length()) {
			
			this.currentStringBuilder.append(longestStringBuilder.charAt(condition - min));
			
		}
	}
	
//	Additional method to get a maximum integer, if we ever wanted to test this using the non-extra credit formula for the Fitness method.
	Integer max(int firstInt, int secondInt) {
		
		int L;
		
		if(firstInt < secondInt) {
			
			L = secondInt;
			
		} else {
			
			L = firstInt;
			
		}
		
		return L;
		
	}
	// original algorithm to calculate fitness
	Integer fitness() {
		
		int n;
		int m;
		n = currentStringBuilder.length();
		m = target.length();
		int L = max(n,m);
		int f = Math.abs(m - n);
		int tempf = f;
		for(int i = 0; i < L - tempf ; i++) {
			
			if (currentStringBuilder.charAt(i) != target.charAt(i) ) {
				
				f ++;
				
			} else {
				//do nothing
			}
		}

		return f + tempf;

	}
//	 This method tests the fitness of our currentString to how close it is relative to our desired target strive, the lower the value the better
//   0 is the best case, huge numbers are bad.
//	 We implemented this method using the algorithm/for loop and layout provided in the project guidelines using Wagner-Fischer algorithm, Levenshtein edit distance.
	Integer extraCreditFitness() {
		
		int n;
		int m;
		n = currentStringBuilder.length();
		m = target.length();
		int myFitness;
		int myMin;
		
		int[][] D = new int[n+1][m+1];
		
		for(int i = 0; i < n+1; i++) {
			
			D[i][0] = i;
		}
		
		
		for(int i = 0; i < m+1; i++) {
			
			D[0][i] = i;
			
		}
			
		for(int i = 1; i < n + 1; i++) { //was not working properly from i < n but when added +1 it got fixed
			
			for(int j = 1; j < m + 1; j++) {
				
				if (currentStringBuilder.charAt(i - 1) == target.charAt(j - 1)) {
					D[i][j] = D[i - 1][j - 1];
					//System.out.println("MY charAt row index is:   " + (i));
					//System.out.println("MY charAt column index is:   " + (j));
					//System.out.println("MY charAt if string:    " + D[i][j]);
					
				} else {
					
					int temp1 = D[i-1][j] + 1;
					int temp2 = D[i][j-1] + 1;
					int temp3 = D[i-1][j-1] + 1;
					
					if(temp1 < temp2 && temp1 < temp3) {
						
						D[i][j] = temp1;
						//System.out.println("1*****" + D[i][j]);
						
					} else if ((temp2 < temp1) && (temp2 < temp3)) {
						
						D[i][j] = temp2;
						//System.out.println("2*****" + D[i][j]);
						
					} else {
						
						D[i][j] = temp3;
						//System.out.println("3*****" + D[i][j]);
					}
					
					//D[i][j] = myMin;
					//System.out.println("4******" + myMin);
					//myFitness = D[n][m] + (Math.abs(n - m) + 1) / 2;
					//System.out.println("FITNESS" + myFitness);
				}
			}
			
		}	
		//This is just a for loop to help print out the matrix visually so we could follow along
		for(int i = 0; i < n+1; i++) {
			
			for(int j = 0; j < m+1; j++) {
				
				int myInt = D[i][j];
				String str = Integer.toString(myInt);
				if(str.length() == 1) {
					str = str + " " ;
				}
				
				System.out.print(str);    //prints out the matrix we created
			}
			
			System.out.println();			//indents the spacing
		}
		
//		System.out.println("MY n is:   " + n);
//		System.out.println("MY m is:   " + m);
//		System.out.println("MY D[n][m] is:   " + D[n][m - 1]);
		myFitness = (D[n][m] + ((Math.abs(n - m) + 1) / 2));
//		System.out.println("FITNESS    " + myFitness);
		
		//System.out.println(myFitness);
		return myFitness;
		
	}
	/**
	 * Returns the method result into a east to read format as specified in the guidelines.
	 */
	public String toString() {
		
		return "(\"" + currentStringBuilder.toString() + "\"" + ", " + fitness() + ")";
		
	}

	@Override
	public int compareTo(Genome theOther) {
		int result = this.fitness() - theOther.fitness();
		return result;
	}
}
