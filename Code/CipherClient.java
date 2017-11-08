import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "paradox.sis.pitt.edu";
		int port = 7999;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		// -Generate a DES key.
		SecureRandom ran = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(ran);
        SecretKey secretkey = generator.generateKey();
		// -Store it in a file.
        byte[] keyAsByte = secretkey.getEncoded();
        FileOutputStream fos = new FileOutputStream("DESKEY.txt");
        fos.write(keyAsByte);
        fos.close();
        System.out.println("DES Key : "+keyAsByte);
		// -Use the key to encrypt the message above 	
        SecureRandom ran2 = new SecureRandom();
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.ENCRYPT_MODE, secretkey, ran2);  
        byte[] encryptresult = cipher.doFinal(message.getBytes());  
        System.out.println("Plaintext : "+ message);
        System.out.println("Ciphertext : "+new String(encryptresult));
        //send it over socket s to the server.
        os.writeObject(encryptresult);
	}
}