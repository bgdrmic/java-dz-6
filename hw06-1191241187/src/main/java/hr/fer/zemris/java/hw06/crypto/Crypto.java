package hr.fer.zemris.java.hw06.crypto;

import java.nio.file.Path;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A console program for encrypting/decrypting a file with AES algorithm and
 * checking whether some file produces appropriate SHA-256 digest.
 * Allowed commands are:
 * 	checksha filePath
 * 	encrypt filePath
 * 	decrypt filePath
 * @author Božidar Grgur Drmić
 *
 */
public class Crypto {

	/**
	 * A buffer length constant.
	 */
	private static final int BUFFER_LENGTH = 512;
	
	/**
	 * Private method which checks whether some file produces appropriate sha-256 digest
	 * expected value of which is entered through console.
	 * @param path - path to that file.
	 * @return true only if digests are the same. False otherwise.
	 * @throws IllegalArgumentException if the path is wrong.
	 */
	private static boolean checksha(Path path) {
		System.out.printf("Please provide expected sha-256 digest for " + path.toString() + ":%n> ");
		
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		scanner.close();
		
		MessageDigest md;
		byte[] digest;

		
		try(BufferedInputStream reader = new BufferedInputStream(new FileInputStream(path.toString()))) {
			md = MessageDigest.getInstance("SHA-256");
		    digest = new byte[BUFFER_LENGTH];
		    int a = 0;
		    
		    while ((a = reader.read(digest)) != -1) {
		    	md.update(digest, 0, a);
		    }
		    digest = md.digest();
		} catch (Exception e) {
			throw new IllegalArgumentException("Something went wrong.");
		}
		
		if(Util.bytetohex(digest).equals(s.toLowerCase())) {
			System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
			return true;
		} else {
			System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest. Digestwas: " + digest);
			return false;
		}
	}
	
	/**
	 * Method which encrypts/decrypts some file with AES algorithm.
	 * Password and initalization vector are entered through console.
	 * 
	 * @param inFile - file which is decrypted/encrypted.
	 * @param outFile - file where the result of encryption/decryption is stored.
	 * @param encrypt - if this is true, than encryption is executed. Decryption otherwise.
	 */
	private static void cipher(Path inFile, Path outFile, boolean encrypt) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		String keyText = scanner.nextLine();
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
		String ivText = scanner.nextLine();
		scanner.close();
		
		if(keyText.length() != 32 || ivText.length() != 32) {
			throw new IllegalArgumentException("Wrong entry");
		}
		
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Something went wrong.");
		}
		
		
		try {
			var in = new FileInputStream(inFile.toFile());
			var out = new FileOutputStream(outFile.toFile());
			byte[] buffer = new byte[BUFFER_LENGTH];			
			int a = 0;
			
			while ((a = in.read(buffer)) != -1) {
				byte[] current = cipher.update(buffer, 0, a);
				if (current == null) continue;
				out.write(current);
			}
			byte[] current = cipher.doFinal();
			if (current != null) {
				out.write(current);
			}
			
			in.close();
		    out.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Something went wrong.");
		}
		
		
		if(encrypt){
			System.out.print("Encryption completed. ");
		} else {
			System.out.print("Decryption completed. ");
		}
		System.out.println("Generated file " + outFile.toString() + " based on file: " + inFile.toString());
	}
	
	/**
	 * Main method of this console. Expects a command through console.
	 * @param args - command
	 */
	public static void main(String[] args) {
		if(args[0].equals("checksha")) {
			
			if(args.length != 2) {
				throw new IllegalArgumentException();
			}
			
			Path path = Paths.get(args[1]);
			checksha(path);
			return;
		}
		if(args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if(args.length != 3) {
				throw new IllegalArgumentException();
			}
			
			Path inFile = Paths.get(args[1]);
			Path outFile = Paths.get(args[2]);
			
			cipher(inFile, outFile, args[0].equals("encrypt"));
			return;
		} 
	}
}
