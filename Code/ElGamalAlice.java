import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;

public class ElGamalAlice
{
	private static BigInteger computeY(BigInteger p, BigInteger g, BigInteger d)
	{
		BigInteger y;
		y=g.modPow(d, p);
		return y;
	}

	private static BigInteger computeK(BigInteger p)
	{
		BigInteger K = BigInteger.ONE;
		BigInteger pMinusOne = p.subtract(BigInteger.ONE);
		
		while(K.gcd(pMinusOne).compareTo(BigInteger.ONE) != 0){
			K = K.add(BigInteger.ONE);
		}
		return K;
	}
	
	private static BigInteger computeA(BigInteger p, BigInteger g, BigInteger k)
	{
		BigInteger a;
		a = g.modPow(k, p);
		return a;
	}

	private static BigInteger computeB(	String message, BigInteger d, BigInteger a, BigInteger k, BigInteger p)
	{
		BigInteger m = new BigInteger(message.getBytes());
		BigInteger zero = BigInteger.ZERO;
		BigInteger B = m.subtract(d.multiply(a)).divide(k).mod(p.subtract(BigInteger.ONE));
		return B;
	}
	
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		
		String host = "localhost";
		int port = 7999;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		 
		BigInteger y, g, p; // public key
		BigInteger d; // private key

		int mStrength = 1024; // key bit length
		SecureRandom mSecureRandom = new SecureRandom();

		p = new BigInteger(mStrength, 16, mSecureRandom); // random create prime p
		g = new BigInteger(mStrength-1, mSecureRandom);
		d = new BigInteger(mStrength-1, mSecureRandom); // private key
		y = computeY(p, g, d);

		//sign
		BigInteger k = computeK(p);
		BigInteger a = computeA(p, g, k);
		BigInteger b = computeB(message, d, a, k, p);
		
		System.out.println("Message : "+message);
		System.out.println("Signature " + b);
		
		//send public key
		os.writeObject(y);
		os.writeObject(g);
		os.writeObject(p);
		// send message
		os.writeObject(message);
		// send signature
		os.writeObject(a);
		os.writeObject(b);
		s.close();
		
	}
}