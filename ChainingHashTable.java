package assign10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {
	
	private ArrayList<LinkedList<String>> buckets;
	private HashFunctor hashFunctor;
	private int size;
	
	public ChainingHashTable(int capacity, HashFunctor functor) {
		buckets = new ArrayList<LinkedList<String>>(nextPrime(capacity));
		for(@SuppressWarnings("unused") LinkedList<String> list : buckets) {
			list = new LinkedList<String>();
		}
		hashFunctor = functor;
		size = 0;
	}
	
	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item -
	 *          the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is,
	 *         if the input item was actually inserted); otherwise, returns false
	 */
	public boolean add(String item) {
		int index = hashFunctor.hash(item) % buckets.size();
		if (buckets.get(index).contains(item)) {
			return false;
		}
		
		buckets.get(index).add(item);
		size++;
		
		return true;
	}

	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items -
	 *          the collection of items whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is,
	 *         if any item in the input collection was actually inserted);
	 *         otherwise, returns false
	 */
	public boolean addAll(Collection<? extends String> items) {
		boolean success = true;
		for(String s : items) {
			success = add(s) && success;
		}
		return success;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	public void clear() {
		for(LinkedList<String> list : buckets) {
			list.clear();
		}
		size = 0;
	}

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item -
	 *          the item sought in this set
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 */
	public boolean contains(String item) {
		int index = hashFunctor.hash(item) % buckets.size();
		
		return buckets.get(index).contains(item);
	}

	/**
	 * Determines if for each item in the specified collection, there is an item
	 * in this set that is equal to it.
	 * 
	 * @param items -
	 *          the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an item
	 *         in this set that is equal to it; otherwise, returns false
	 */
	public boolean containsAll(Collection<? extends String> items) {
		boolean success = true;
		for(String s : items) {
			success = contains(s) && success;
		}
		return success;
	}

	/**
	 * Returns true if this set contains no items.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of items in this set.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Next Prime
	 * 
	 * Finds the smallest prime number that is larger than the given integer value.
 	 * 
 	 * @param n The given integer value above which to test for primes.
 	 * @return The next smallest prime number, as an integer.
 	 */
	public static int nextPrime(int n) {
		//If n is positive, find the next smallest prime number
		if(n > -1) {
			if(n <= 1) {
				return 2;
			}
			else {
				boolean isPrime = false;
				int num = n;
				//Iterate through the integers, starting with n+1
				while(!isPrime) {
					//Test whether the number is prime
					for(int j = 2; j < num; j++) {
						if(num % j == 0) {
							isPrime = false;
							break;
						}
						else {
							isPrime = true;
						}
					}
					num++;
				}
				return num;
			}
		}
		//Otherwise, return -1
		else {
			return -1;
		}
	}
}
