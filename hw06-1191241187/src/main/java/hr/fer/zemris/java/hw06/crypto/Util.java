package hr.fer.zemris.java.hw06.crypto;

/**
 * Util is a utility class which offers static methods for conversion from
 * hex to bytes format an vice versa.
 * @author Božidar Grgur Drmić
 *
 */
public class Util {

	/**
	 * A method which converts a string of hex digits into an array of bytes.
	 * Every two digits are converted to one byte.
	 * 
	 * @param keyText - string which is converted.
	 * @return the array of bytes.
	 * @throws IllegalArgumentException if length of keyText is an odd number.
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid hex format");
		}
		
		byte[] bytes = new byte[keyText.length() / 2];
		
		for(int i = 0; i < keyText.length() / 2; i++) {
			char c = keyText.charAt(2 * i);
			char d = keyText.charAt(2 * i + 1);
			
			int x, y;
			x = Character.digit(c, 16);
			y = Character.digit(d, 16);

			bytes[i] = (byte) (16*x + y);
		}
		
		return bytes;
	}

	/**
	 * A method which converts an array of bytes into a string of hex digits.
	 * Each byte is converted to two digits.
	 * 
	 * @param keyText - array which is converted.
	 * @return the string of hex digits.
	 */
	public static String bytetohex(byte[] keyText) {	
		StringBuilder hex = new StringBuilder();
		
		for(int i = 0; i < keyText.length; i++) {
			hex.append(String.format("%02x", keyText[i]));
		}
		
		return hex.toString().toLowerCase();
	}
}
