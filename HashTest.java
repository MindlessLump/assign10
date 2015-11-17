package assign10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HashTest {

	//Test QuadProbeHashTable
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
	
	//Test ChainingHashTable
	@Test
	public void chainingConstruct() {
		ChainingHashTable table = new ChainingHashTable(20, new BadHashFunctor());
		assertEquals(23, table.capacity());
	}

	@Test
	public void chainingAdd() {
		ChainingHashTable table = new ChainingHashTable(3, new BadHashFunctor());
		assertEquals(3, table.capacity());
		table.add("A");
		table.add("D");
		table.add("P");
		table.add("U");
		assertEquals(4, table.size());
		assertEquals(3, table.capacity());
	}
	
	@Test
	public void chainingAddAll() {
		ChainingHashTable table = new ChainingHashTable(7, new BadHashFunctor());
		assertEquals(7, table.capacity());
		ArrayList<String> arr = new ArrayList<>();
		arr.add("j");
		arr.add("J");
		arr.add("8");
		table.addAll(arr);
		assertEquals(3, table.size());
	}
	
	@Test
	public void chainingClear() {
		ChainingHashTable table = new ChainingHashTable(107, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		table.clear();
		assertEquals(0, table.size());
		assertEquals(107, table.capacity());
	}
	
	@Test
	public void chainingContains() {
		ChainingHashTable table = new ChainingHashTable(53, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertTrue(table.contains("y"));
		assertFalse(table.contains("E"));
		assertFalse(table.containsAll(Arrays.asList("q", "w", "e", "r", "z", "x", "i")));
		assertTrue(table.containsAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")));
	}
	
	@Test
	public void chainingContainsAll() {
		ChainingHashTable table = new ChainingHashTable(53, new BadHashFunctor());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertFalse(table.containsAll(Arrays.asList("q", "w", "e", "r", "z", "x", "i")));
		assertTrue(table.containsAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")));
	}
	
	@Test
	public void chainingIsEmpty() {
		ChainingHashTable table = new ChainingHashTable(53, new BadHashFunctor());
		assertTrue(table.isEmpty());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertFalse(table.isEmpty());
		table.clear();
		assertTrue(table.isEmpty());
	}
	
	@Test
	public void chainingSize() {
		ChainingHashTable table = new ChainingHashTable(53, new BadHashFunctor());
		assertEquals(0, table.size());
		table.addAll(Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"));
		assertEquals(10, table.size());
		table.clear();
		assertEquals(0, table.size());
	}
	
	@Test
	public void chainingNextPrime() {
		assertEquals(-1, ChainingHashTable.nextPrime(-1));
		assertEquals(2, ChainingHashTable.nextPrime(0));
		assertEquals(2, ChainingHashTable.nextPrime(1));
		assertEquals(58997, ChainingHashTable.nextPrime(58992));
		assertEquals(15083, ChainingHashTable.nextPrime(15083));
	}
	
	//Test BadHashFunctor
	@Test
	public void testBadHashFunctor() {
		BadHashFunctor bad = new BadHashFunctor();
		List<String> input = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
		for(int val = 65; val <= 90; val++) {
			assertEquals(val, bad.hash(input.get(val-65)));
		}
		assertEquals(bad.hash("H"), bad.hash("Hello"));
		assertNotEquals(bad.hash("A"), bad.hash("a"));
	}
	
	//Test MediocreHashFunctor
	@Test
	public void testMediocreHashFunctor() {
		MediocreHashFunctor med = new MediocreHashFunctor();
		List<String> input = Arrays.asList("QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM");
		assertNotEquals(med.hash(input.get(0)), med.hash(input.get(1)));
		assertNotEquals(med.hash(input.get(0)), med.hash(input.get(2)));
		assertNotEquals(med.hash(input.get(1)), med.hash(input.get(2)));
		assertEquals(809, med.hash(input.get(0)));
		assertEquals(654, med.hash(input.get(1)));
		assertEquals(552, med.hash(input.get(2)));
		assertNotEquals(med.hash("H"), med.hash("HA"));
		assertNotEquals(med.hash("A"), med.hash("a"));
		assertEquals(med.hash("ABC"), med.hash("CBA"));
	}
	
	//Test GoodHashFunctor
	@Test
	public void testGoodHashFunctor() {
		
	}
}
