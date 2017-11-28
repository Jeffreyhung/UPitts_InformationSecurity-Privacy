import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Message_digest {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		System.out.println("Please type the word that will be hashed");
		Scanner input = new Scanner(System.in);
		
		String plaintext = input.nextLine();
		MessageDigest md_md5 = MessageDigest.getInstance("MD5");
		MessageDigest md_sha = MessageDigest.getInstance("SHA");
		md_md5.update(plaintext.getBytes());
		byte[] digest = md_md5.digest();
		StringBuffer cipher = new StringBuffer();
		for (byte b : digest) {
			cipher.append(String.format("%02x", b & 0xff));
		}
				
		md_sha.update(plaintext.getBytes());
		byte[] digest2 = md_sha.digest();
		StringBuffer cipher2 = new StringBuffer();
		for (byte b : digest2) {
			cipher2.append(String.format("%02x", b & 0xff));
		}
		System.out.println("Original text : " + plaintext);
		System.out.println("MD5 digested  : " + cipher.toString());
		System.out.println("SHA digested  : " + cipher2.toString());
	}
}
