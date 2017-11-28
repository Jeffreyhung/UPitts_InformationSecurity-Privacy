import java.io.*;
import java.util.Arrays;

public class Cryptanalysis {
	public static void main(String[] args) throws Exception 
	{
		String ciphertext;
		ciphertext=readfile();
		ciphertext = ciphertext.toLowerCase();
		char ciphertext_array[]= ciphertext.toCharArray();
		int cipher_count[] = count(ciphertext_array);
		double cipher_cal[] = calculate(cipher_count);
		double correlation[] = frequency(cipher_cal);
		int max7[] = get_max_seven(correlation);
		for(int i=0; i<max7.length;i++) {
			System.out.println(i+1 + " result : ");
			System.out.println("   Key : "+ max7[i]);
			System.out.print("   Plaintext : ");
			decrypt(ciphertext_array, max7[i]);
		}
	}
	
	private static void decrypt(char[] ciphertext_array, int n) {
		int subKey = n;
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
		System.out.println(plaintext);
	}

	private static int[] get_max_seven(double[] correlation) {
		int max7[] = new int[7];
		double[] tempArray = correlation.clone();
		Arrays.sort(tempArray);
		for(int i=1; i<8; i++) {
			double max = tempArray[tempArray.length - i];
			int result = indexOf(correlation, max);
			max7[i-1]=result;
		}
		return max7;
	}

	private static int indexOf(double[] correlation, double max) {
		int i=0;
		for(i=0; i<correlation.length;i++) {
			if(max == correlation[i])
				break;
		}
		return i;
	}

	private static double[] frequency(double[] cipher_freq) {
		double correlation[] = new double[26];
		double alphabet_freq[] = new double[]{0.08,0.015,0.03,0.04,0.13,0.02,0.015,0.06,0.065,0.005,0.005,0.035,0.03,0.07,0.08,0.02,0.002,0.065,0.06,0.09,0.03,0.01,0.015,0.005,0.02,0.002};
		double sum=0;
		int num=0;
		for(int i=0; i<26; i++) {
			sum = 0;
			for(int j=0; j<26; j++) {
				num = (j-i) % 26;
				if(num<0)
					num += 26;
				sum += cipher_freq[j] * alphabet_freq[num] ;
			}
			correlation[i] = sum;
		}
		return correlation;
	}
	
	private static double[] calculate(int[] count) {
		double result[] = new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int sum=0;
		for(int i=0; i<26; i++) {
			sum += count[i];
		}
		for(int i=0; i<26; i++) {
			result[i] = (double)count[i] / (double)sum;
		}
		return result;
	}
	
	private static int[] count(char[] ciphertext) {
		int result[] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		for(int i=0; i<ciphertext.length;i++) {
			char compare = 'a';
			if((int)ciphertext[i] >122 || (int)ciphertext[i]<97) {
				continue;
				// skip if not alphabet
			}
			char text = ciphertext[i];
			for(int j=0; j<26; j++) {
				if(text==compare) {
					result[j] += 1;
					break;
				}else {
					compare += 1;
				}
			}
		}
		return result;
	}
	
	private static String readfile() throws IOException {
		String text;
		BufferedReader textreader = new BufferedReader(new FileReader("Ciphertext.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = textreader.readLine();
	
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = textreader.readLine();
		    }
		    text = sb.toString();
		} finally {
			textreader.close();
		}
		return text;
	}
}
