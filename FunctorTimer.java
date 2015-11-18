package assign10;

import java.util.ArrayList;
import java.util.Random;

/**
 * Tests the running time of ChainingHashTable and QuadProbeHashTable.
 * 
 * @author Cory Rindlisbacher and Erik Martin
 *
 */
public class FunctorTimer {

	private final static int	DEFAULT_STRING_SIZE	= 10;
	private final static int	NUMBER_OF_TRIALS	= 10;

	/**
	 * Insert the test that you want to run inside of this method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		timeChainingHashTable();
	}

	/**
	 * Method that determines the average running time of QuadProbeHashTable's
	 * add method using different HashFunctions. Inserts randomly generated
	 * strings. Generates a table showing the number of strings inserted, the
	 * time to insert all items using a good hash function, a mediocre hash
	 * function, and a bad hash function.
	 * 
	 */
	public static void timeQuadProbe() {

		System.out.println("Items \t " + "Good \t  Mediocre \t  Bad");

		QuadProbeHashTable goodHash = new QuadProbeHashTable(100, new GoodHashFunctor());
		QuadProbeHashTable medHash = new QuadProbeHashTable(100, new MediocreHashFunctor());
		QuadProbeHashTable badHash = new QuadProbeHashTable(100, new BadHashFunctor());
		long startTime, endTime = 0;
		long totalTimeGood = 0;
		long totalTimeMed = 0;
		long totalTimeBad = 0;

		for (int i = 10000; i <= 100000; i += 10000) {
			int k = 0;
			totalTimeGood = totalTimeBad = totalTimeMed = 0;
			while (k < NUMBER_OF_TRIALS) {
				ArrayList<String> values = new ArrayList<String>(i);

				for (int j = 0; j < i; j++) {
					values.add(genRandomString());
				}

				startTime = System.nanoTime();
				while (System.nanoTime() - startTime < 10000) {

				}
				startTime = System.nanoTime();
				goodHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeGood += endTime - startTime;

				startTime = System.nanoTime();
				badHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeBad += endTime - startTime;

				startTime = System.nanoTime();
				medHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeMed = endTime - startTime;

				goodHash.clear();
				badHash.clear();
				medHash.clear();
				k++;
			}
			System.out.println("" + i + " \t " + totalTimeGood / NUMBER_OF_TRIALS + " \t " + totalTimeMed / NUMBER_OF_TRIALS
					+ " \t " + totalTimeBad / NUMBER_OF_TRIALS);

		}
	}

	/**
	 * Method that determines the average running time of ChainingHashTable's
	 * add method using different HashFunctions. Inserts randomly generated
	 * strings. Generates a table showing the number of strings inserted, the
	 * time to insert all items using a good hash function, a mediocre hash
	 * function, and a bad hash function.
	 * 
	 */
	public static void timeChainingHashTable() {

		System.out.println("Items \t " + "Good \t Mediocre \t Bad");

		ChainingHashTable goodHash = new ChainingHashTable(100, new GoodHashFunctor());
		ChainingHashTable medHash = new ChainingHashTable(100, new MediocreHashFunctor());
		ChainingHashTable badHash = new ChainingHashTable(100, new BadHashFunctor());
		long startTime, endTime = 0;
		long totalTimeGood = 0;
		long totalTimeMed = 0;
		long totalTimeBad = 0;

		for (int i = 10000; i <= 100000; i += 10000) {
			int k = 0;
			totalTimeGood = totalTimeBad = totalTimeMed = 0;
			while (k < NUMBER_OF_TRIALS) {
				ArrayList<String> values = new ArrayList<String>(i);

				for (int j = 0; j < i; j++) {
					values.add(genRandomString());
				}

				startTime = System.nanoTime();
				while (System.nanoTime() - startTime < 10000) {

				}
				startTime = System.nanoTime();
				goodHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeGood += endTime - startTime;

				startTime = System.nanoTime();
				badHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeBad += endTime - startTime;

				startTime = System.nanoTime();
				medHash.addAll(values);
				endTime = System.nanoTime();
				totalTimeMed = endTime - startTime;

				goodHash.clear();
				badHash.clear();
				medHash.clear();
				k++;
			}
			System.out.println("" + i + " \t " + totalTimeGood / NUMBER_OF_TRIALS + " \t " + totalTimeMed / NUMBER_OF_TRIALS
					+ " \t " + totalTimeBad / NUMBER_OF_TRIALS);

		}

	}

	/**
	 * Method to generate random strings. Default size of strings is 10
	 * characters.
	 * 
	 * @return a random string
	 */
	private static String genRandomString() {

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
