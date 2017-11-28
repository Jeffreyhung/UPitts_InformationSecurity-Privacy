import java.io.*;

public class Substitution_decrypt {

	public static void main(String[] args) throws Exception 
	{
		String ciphertext;
		int subKey;
/////read ciphertext/////
		BufferedReader textreader = new BufferedReader(new FileReader("Ciphertext.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = textreader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = textreader.readLine();
		    }
		    ciphertext = sb.toString();
		} finally {
			textreader.close();
		}
		char ciphertext_array[]= ciphertext.toCharArray();	
/////read key/////
		BufferedReader keyreader = new BufferedReader(new FileReader("SubKEY.txt"));
		try {
		    String line = keyreader.readLine();
		    String key = line.toString();
		    subKey = Integer.parseInt(key);
		} finally {
			keyreader.close();
		}
/////print ciphertext/////
		System.out.print("Ciphertext : ");
		for(int i=0;i<ciphertext_array.length;i++)
		{
			ciphertext_array[i]=(char)((int)ciphertext_array[i]);
		}
		for(int i=0;i<ciphertext_array.length;i++)
		{
			System.out.print(ciphertext_array[i]);
		}
		System.out.println("");
/////////decrypt//////
		for(int i=0;i<ciphertext_array.length;i++)
		{
			if((int)ciphertext_array[i]<91 && (int)ciphertext_array[i] >64) {
				if((int)ciphertext_array[i]-subKey < 65) {
					ciphertext_array[i]=(char)((int)ciphertext_array[i]+26-subKey);
				}else {
					ciphertext_array[i]=(char)((int)ciphertext_array[i]-subKey);
				}
			}else if((int)ciphertext_array[i]<123 && (int)ciphertext_array[i] >96) {
				if((int)ciphertext_array[i]-subKey < 97) {
					ciphertext_array[i]=(char)((int)ciphertext_array[i]+26-subKey);
				}else {
					ciphertext_array[i]=(char)((int)ciphertext_array[i]-subKey);
				}
			}else {
				ciphertext_array[i]=(char)((int)ciphertext_array[i]-subKey);
			}
		}
		String plaintext = String.valueOf(ciphertext_array);
		System.out.println("Plaintext : "+plaintext);
	}
}