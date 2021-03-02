package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHexToBytes() {
		String hex = "01aE22";
		
		byte[] expected = new byte[] { 1, -82, 34 };
		byte[] result = Util.hextobyte(hex);
		
		assertEquals(expected.length, result.length);
		
		for(int i = 0; i < result.length; i++) {
			assertEquals(expected[i], result[i]);
		}
		
		assertEquals(0, Util.hextobyte("").length);
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("101"));
	}
	
	@Test
	void testBytesToHex() {
		byte[] bytes = new byte[] { 1, -82, 34 };
		
		String expected = "01ae22";
		String result = Util.bytetohex(bytes);
		
		assertTrue(expected.equals(result));
		assertEquals("", Util.bytetohex(new byte[0]));
	}

}
