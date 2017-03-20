package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import edu.princeton.cs.algs4.Stopwatch;


//Ecoli.1M.36mer.txt
public class BloomFilter {

	public static void main(String[] args) throws FileNotFoundException{
		
		// making a hash array
		int M = 4000000;// a big M makes less false-positive results
		long[] hashArray = new long[M];
		String fileName = "Ecoli.1M.36mer.txt";
		File inputFile = new File(fileName);
		Scanner in = new Scanner(inputFile);
		Stopwatch timer = new Stopwatch();

		while(in.hasNextLine()){
			String s = in.nextLine();
			// first hash function
			int R = 37;
			int hash_1 = 0;
			for(int j = 0; j < s.length(); j++){
				hash_1 = (R * hash_1 + s.charAt(j))%M;
			}
			
			//second hash function
			R = 31;
			int hash_2 = 0;
			for(int i = 0; i < s.length(); i++){
				hash_2 = (R * hash_2 + s.charAt(i))%M;
			}
			//third hash function
			R = 27;
			int hash_3 = 0;
			for(int i = 0; i < s.length(); i++){
				hash_3 = (R * hash_3 + s.charAt(i))%M;
			}
			
			hashArray[hash_1] = 1;
			hashArray[hash_2] = 1;
			hashArray[hash_3] = 1;
		}
		double time_compression = timer.elapsedTime();

		// write array into a file
		PrintWriter out = new PrintWriter("S1_compressed.txt");
		for(long number: hashArray){
			out.print(number + "\n");
		}

		out.close();
		
		//checking if the data in another file is a part of data that was used
		File readFile = new File("S1_compressed.txt");
		Scanner new_in = new Scanner(readFile);
		//save the data into a new array of length M
		long[] newHashArray = new long[M];
		int counter = 0;
		while(new_in.hasNextLine()){
			String number = new_in.nextLine();
			newHashArray[counter] = Integer.parseInt(number);
			counter++;
		}
		
		//compare arrays
		File compareFile = new File("Ecoli.2M.36mer.txt");
		Scanner inn = new Scanner(compareFile);
		int counterPositive = 0;
		Stopwatch timer1 = new Stopwatch();

		while(inn.hasNextLine()){
			String s = inn.nextLine();
			// first hash function
			int R = 37;
			int hash_1 = 0;
			for(int j = 0; j < s.length(); j++){
				hash_1 = (R * hash_1 + s.charAt(j))%M;
			}
			
			//second hash function
			R = 31;
			int hash_2 = 0;
			for(int i = 0; i < s.length(); i++){
				hash_2 = (R * hash_2 + s.charAt(i))%M;
			}
			//third hash function
			R = 27;
			int hash_3 = 0;
			for(int i = 0; i < s.length(); i++){
				hash_3 = (R * hash_3 + s.charAt(i))%M;
			}
			
			if(newHashArray[hash_1] == 1 && newHashArray[hash_2] == 1 && newHashArray[hash_3] == 1){
				counterPositive++;
			}
		}
		double time_querying = timer1.elapsedTime();

		inn.close();
		new_in.close();
		in.close();

		System.out.println(counterPositive);
		System.out.println("Time comression: " + time_compression);
		System.out.println("Time querying: " + time_querying);
	}

}
