import java.io.*;

public class Substitution_encrypt {

	public static void main(String[] args) throws Exception 
	{
		String plaintext;
		int subKey;
/////read plaintext/////
		BufferedReader textreader = new BufferedReader(new FileReader("Plaintext.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = textreader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = textreader.readLine();
		    }
		    plaintext = sb.toString();
		} finally {
			textreader.close();
		}
		char plaintext_array[]= plaintext.toCharArray();	
/////read key/////
		BufferedReader keyreader = new BufferedReader(new FileReader("SubKEY.txt"));
		try {
		    String line = keyreader.readLine();
		    String key = line.toString();
		    subKey = Integer.parseInt(key);
		} finally {
			keyreader.close();
		}
/////print plaintext/////
		System.out.print("Plaintext : ");
		for(int i=0;i<plaintext_array.length;i++)
		{
			plaintext_array[i]=(char)((int)plaintext_array[i]);
		}
		for(int i=0;i<plaintext_array.length;i++)
		{
			System.out.print(plaintext_array[i]);
		}
/////encrypt//////		
		for(int i=0;i<plaintext_array.length;i++)
		{
			if((int)plaintext_array[i]<91 && (int)plaintext_array[i] >64) {
				if((int)plaintext_array[i]+subKey > 90) {
					plaintext_array[i]=(char)(subKey+(int)plaintext_array[i]-26);
				}else {
					plaintext_array[i]=(char)(subKey+(int)plaintext_array[i]);
				}
			}else if((int)plaintext_array[i]<123 && (int)plaintext_array[i] >96) {
				if((int)plaintext_array[i]+subKey > 122) {
					plaintext_array[i]=(char)(subKey+(int)plaintext_array[i]-26);
				}else {
					plaintext_array[i]=(char)(subKey+(int)plaintext_array[i]);
				}
			}else {
				plaintext_array[i]=(char)(subKey+(int)plaintext_array[i]);
			}
		}
		String ciphertext = String.valueOf(plaintext_array);
		System.out.println("Ciphertext : "+ciphertext);
/////save ciphertext/////
		PrintWriter writer = new PrintWriter("Ciphertext.txt", "UTF-8");
		writer.print(ciphertext);
		writer.close();
	}
}