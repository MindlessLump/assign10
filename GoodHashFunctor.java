package assign10;

/**
 * Class representation of a good hash functor
 * Simply returns the result of Java's hash functor
 * 
 * @author Erik Martin and Cory Rindlisbacher
 */
public class GoodHashFunctor implements HashFunctor {

	@Override
	public int hash(String item) {
		return item.hashCode();
	}
}
