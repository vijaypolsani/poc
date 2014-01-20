package com.kd.blog.learings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Factorial {
	// A simple program to 

	public static void main(String args[]) {
		Factorial rt = new Factorial();
		for (;;) {
			System.out.println("\n Input the number: ");

			try {
				System.out.println("\n Factorial Output: " + rt.calculateRecursively(rt.getInt()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Crux of the logic
	public int calculateRecursively(int n) {
		//Base Case: Exiting otherwise if become infinite loop
		// Since f(0) = 1 and f(1) =1
		if (n == 0) {
			return 1;
		}
		//Logic: Execution loop via Recursion.
		else {
			return n * calculateRecursively(n - 1);
		}
	}

	// Simple method to read an input from Console.	
	/*
	 * Same thing using Java 7
	 * Scanner in = new Scanner(System.in);
	 * int i = in.nextInt();
	 */
	public int getInt() throws IOException {
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(ir);
		String inputString = br.readLine();
		return Integer.parseInt(inputString);
	}

}
