package assign10;

/**
 * Class representation of a bad hash functor.
 * Returns the ASCII code for the first character in the String item.
 * 
 * @author Erik Martin and Cory Rindlisbacher
 */
public class BadHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		return (int) item.charAt(0);
	}
}
