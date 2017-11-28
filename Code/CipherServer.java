import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Handler;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket server = new ServerSocket(port);;
		Socket client = server.accept();
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());
		byte[] encryptresult = (byte[]) is.readObject();;
		// -Read the key from the file generated by the client.
		FileInputStream read = new FileInputStream("DESKEY.txt");
        byte[] b = new byte[read.available()];
        read.read(b);
        read.close();
		SecretKey secretkey2 = new SecretKeySpec(b, 0, b.length, "DES");
		// -Use the key to decrypt the incoming message from socket s.	
		SecureRandom ran3 = new SecureRandom();
		Cipher cipher2 = Cipher.getInstance("DES");  
        cipher2.init(Cipher.DECRYPT_MODE, secretkey2, ran3);  
        byte[] decryptresult = cipher2.doFinal(encryptresult);
		// -Print out the decrypt String to see if it matches the orignal message.
        System.out.println("DES Key : " + b);
        String encrypt_string = new String(encryptresult, StandardCharsets.UTF_8);
        System.out.println("Ciphertext: "+ encrypt_string);
        System.out.println("Plaintext: "+ new String(decryptresult));
        server.close();
        
	}
}