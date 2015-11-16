package assign10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class HashTest {

	@Test
	public void quadProbeConstruct() {
		QuadProbeHashTable table = new QuadProbeHashTable(20, new BadHashFunctor());
		assertEquals(23, table.capacity());
	}

	@Test
	public void quadProbeAdd() {
		QuadProbeHashTable table = new QuadProbeHashTable(3, new BadHashFunctor());
		assertEquals(3, table.capacity());
		table.add("A");
		table.add("D");
		table.add("P");
		table.add("U");
		assertEquals(4, table.size());
		assertEquals(17, table.capacity());
	}
	
	@Test
	public void quadProbeAddAll() {
		QuadProbeHashTable table = new QuadProbeHashTable(7, new BadHashFunctor());
		assertEquals(7, table.capacity());
		ArrayList<String> arr = new ArrayList<>();
		arr.add("j");
		arr.add("J");
		arr.add("8");
		table.addAll(arr);
		assertEquals(3, table.size());
	}
	
	@Test
	public void quadProbeClear() {
		QuadProbeHashTable table = new QuadProbeHashTable(107, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		table.clear();
		assertEquals(0, table.size());
		assertEquals(107, table.capacity());
	}
	
	@Test
	public void quadProbeContains() {
		QuadProbeHashTable table = new QuadProbeHashTable(53, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertTrue(table.contains("y"));
		assertFalse(table.contains("E"));
		assertFalse(table.containsAll(Arrays.asList("q", "w", "e", "r", "z", "x", "i")));
		assertTrue(table.containsAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")));
	}
	
	@Test
	public void quadProbeContainsAll() {
		QuadProbeHashTable table = new QuadProbeHashTable(53, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertFalse(table.containsAll(Arrays.asList("q", "w", "e", "r", "z", "x", "i")));
		assertTrue(table.containsAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")));
	}
	
	@Test
	public void quadProbeIsEmpty() {
		QuadProbeHashTable table = new QuadProbeHashTable(53, new BadHashFunctor());
		assertTrue(table.isEmpty());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertFalse(table.isEmpty());
		table.clear();
		assertTrue(table.isEmpty());
	}
	
	@Test
	public void quadProbeSize() {
		QuadProbeHashTable table = new QuadProbeHashTable(53, new BadHashFunctor());
		assertEquals(0, table.size());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertEquals(10, table.size());
		table.clear();
		assertEquals(0, table.size());
	}
	
	@Test
	public void quadProbeNextPrime() {
		assertEquals(-1, QuadProbeHashTable.nextPrime(-1));
		assertEquals(2, QuadProbeHashTable.nextPrime(0));
		assertEquals(2, QuadProbeHashTable.nextPrime(1));
		assertEquals(58997, QuadProbeHashTable.nextPrime(58992));
		assertEquals(15083, QuadProbeHashTable.nextPrime(15083));
	}
}
