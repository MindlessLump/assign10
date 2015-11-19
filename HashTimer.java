package assign10;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class HashTimer {
	
	//Sample size controls
	private static int startSize = 10000;
	private static int endSize = 200000;
	private static int stepSize = 10000;
	private static int timesToLoop = 100;
	
	private static long startTime, midpointTime, stopTime;					//Timer variables
	private static DecimalFormat formatter = new DecimalFormat("0000E0");	//Time string formatter
	
	public static void main(String[] args) {
		System.out.println("Experiment 1:");
		System.out.println("--------------------QuadProbeHashTable add() with a bad hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Bad();
		System.out.println("--------------------QuadProbeHashTable add() with a mediocre hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Mediocre();
		System.out.println("--------------------QuadProbeHashTable add() with a good hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Good();
		
		System.out.println("\nExperiment 2:");
		System.out.println("--------------------QuadProbeHashTable add() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2QuadProbeAdd();
		System.out.println("--------------------QuadProbeHashTable contains() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2QuadProbeContains();
		System.out.println("--------------------ChainingHashTable add() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2ChainingAdd();
		System.out.println("--------------------ChainingHashTable contains() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2ChainingContains();
		
		System.out.println("\nExperiment 3:");
		System.out.println("--------------------BadHashFunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment3Bad();
		System.out.println("--------------------MediocreHashFunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment3Mediocre();
		System.out.println("--------------------GoodHashFunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment3Good();
	}
	
	/**
	 * Our bad hashfunctor returns the ASCII integer value of the first character.
	 */
	public static void doExperiment1Bad() {
		int n = 0;
		int collisions = 0;
		int insertions = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			QuadProbeHashTable table = new QuadProbeHashTable(n, new BadHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.add(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
			collisions += table.collisions;
			insertions += table.insertions;
		}
		System.out.println("Insertions: " + insertions + "\nCollisions: " + collisions);
	}
	
	/**
	 * Our mediocre hashfunctor returns the sum of the ASCII values of the characters.
	 */
	public static void doExperiment1Mediocre() {
		int n = 0;
		int collisions = 0, insertions = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			QuadProbeHashTable table = new QuadProbeHashTable(n, new MediocreHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.add(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
			collisions += table.collisions;
			insertions += table.insertions;
		}
		System.out.println("Insertions: " + insertions + "\nCollisions: " + collisions);
	}
	
	/**
	 * Our good hashfunctor is a wrapper for Java's hashcode() method.
	 */
	public static void doExperiment1Good() {
		int n = 0;
		int collisions = 0, insertions = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			QuadProbeHashTable table = new QuadProbeHashTable(n, new GoodHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.add(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
			collisions += table.collisions;
			insertions += table.insertions;
		}
		System.out.println("Insertions: " + insertions + "\nCollisions: " + collisions);
	}
	
	/**
	 * We use our best hashfunctor to test the quad probe methods.
	 */
	public static void doExperiment2QuadProbeAdd() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			QuadProbeHashTable table = new QuadProbeHashTable(n, new GoodHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.add(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	/**
	 * We use our best hashfunctor to test the quad probe methods.
	 */
	public static void doExperiment2QuadProbeContains() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			QuadProbeHashTable table = new QuadProbeHashTable(n, new GoodHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.contains(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	/**
	 * We use our best hashfunctor to test the chaining probe methods.
	 */
	public static void doExperiment2ChainingAdd() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			ChainingHashTable table = new ChainingHashTable(n, new GoodHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.add(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	/**
	 * We use our best hashfunctor to test the chaining probe methods.
	 */
	public static void doExperiment2ChainingContains() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n, 10);
			ChainingHashTable table = new ChainingHashTable(n, new GoodHashFunctor());
			for(int j = 0; j < n; j++) {
				table.add(input.get(j));
			}
			
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String item = input.get(r.nextInt(input.size()));
				long tempStart = System.nanoTime();
				table.contains(item);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	/**
	 * Our bad hashfunctor returns the ASCII integer value of the first character.
	 */
	public static void doExperiment3Bad() {
		int n = 0;
		BadHashFunctor hf = new BadHashFunctor();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String input = genRandomString(n);
				long tempStart = System.nanoTime();
				hf.hash(input);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	/**
	 * Our mediocre hashfunctor returns the sum of the ASCII values of the characters.
	 */
	public static void doExperiment3Mediocre() {
		int n = 0;
		MediocreHashFunctor hf = new MediocreHashFunctor();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String input = genRandomString(n);
				long tempStart = System.nanoTime();
				hf.hash(input);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}

	/**
	 * Our good hashfunctor is a wrapper for Java's hashcode() method.
	 */
	public static void doExperiment3Good() {
		int n = 0;
		GoodHashFunctor hf = new GoodHashFunctor();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			System.out.print(n + "\t");
			
			//First, spin computing stuff to let the thread stabilize
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {
				;
			}
			
			//Start add() timing
			startTime = System.nanoTime();
			midpointTime = startTime;
			
			for(int j = 0; j < timesToLoop; j++) {
				String input = genRandomString(n);
				long tempStart = System.nanoTime();
				hf.hash(input);
				long tempStop = System.nanoTime();
				midpointTime += (tempStop - tempStart);
			}
			
			stopTime = midpointTime;
			
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));
		}
	}
	
	private static ArrayList<String> generateStrings(int num, int size) {
		ArrayList<String> output = new ArrayList<>();
		for(int j = 0; j < num; j++) {
			output.add(genRandomString(size));
		}
		return output;
	}
	
	/**
	 * Method to generate random strings. Default size of strings is 10
	 * characters.
	 * 
	 * @return a random string
	 */
	private static String genRandomString(int DEFAULT_STRING_SIZE) {

		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		Random rng = new Random();

		char[] values = new char[DEFAULT_STRING_SIZE];

		for (int i = 0; i < DEFAULT_STRING_SIZE; i++) {
			values[i] = alphabet[rng.nextInt(alphabet.length)];
		}

		return new String(values);

	}
}
