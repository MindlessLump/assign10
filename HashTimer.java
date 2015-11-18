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
		System.out.println("-------------------QuadProbeHashTable add() with a bad hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Bad();
		System.out.println("-------------------QuadProbeHashTable add() with a mediocre hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Mediocre();
		System.out.println("-------------------QuadProbeHashTable add() with a good hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment1Good();
		
		System.out.println("\nExperiment 2:");
		System.out.println("-------------------QuadProbeHashTable add() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2QuadProbeAdd();
		System.out.println("-------------------QuadProbeHashTable contains() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2QuadProbeContains();
		System.out.println("-------------------ChainingHashTable add() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2ChainingAdd();
		System.out.println("-------------------ChainingHashTable contains() with Java's hashfunctor--------------------");
		System.out.println("N\tT(N)\t|\tT(N)/1\t\tT(N)/logN\t\tT(N)/N\t\tT(N)/NlogN\t\tT(N)/N^2");
		doExperiment2ChainingContains();
	}
	
	/**
	 * Our bad hashfunctor returns the ASCII integer value of the first character.
	 */
	public static void doExperiment1Bad() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n);
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
		}
	}
	
	/**
	 * Our mediocre hashfunctor returns the sum of the ASCII values of the characters.
	 */
	public static void doExperiment1Mediocre() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n);
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
		}
	}
	
	/**
	 * Our good hashfunctor is a wrapper for Java's hashcode() method.
	 */
	public static void doExperiment1Good() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n);
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
	public static void doExperiment2QuadProbeAdd() {
		int n = 0;
		Random r = new Random();
		
		for(n = startSize; n <= endSize; n += stepSize) {
			ArrayList<String> input = generateStrings(n);
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
			ArrayList<String> input = generateStrings(n);
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
			ArrayList<String> input = generateStrings(n);
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
			ArrayList<String> input = generateStrings(n);
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
	
	public static ArrayList<String> generateStrings(int n) {
		Random r = new Random();
		ArrayList<String> output = new ArrayList<>();
		for(int j = 0; j < n; j++) {
			output.add(Integer.toString(r.nextInt()));
		}
		return output;
	}
}
