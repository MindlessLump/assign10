package assign10;

/**
 * Class representation of a mediocre hash functor
 * Returns the sum of the ASCII values of the characters in the String item
 * 
 * @author Erik Martin and Cory Rindlisbacher
 */
public class MediocreHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		int sum = 0;
		for(int j = 0; j < item.length(); j++) {
			sum += (int) item.charAt(j);
		}
		return sum;
	}
}
