package com.kd.blog.learings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RecursionTriangle {
	// A simple program to 

	public static void main(String args[]) {
		RecursionTriangle rt = new RecursionTriangle();
		for (;;) {
			System.out.println("\n Input the number: ");

			try {
				System.out.println("\n Output Number: " + rt.calculateRecursively(rt.getInt()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Crux of the logic
	public int calculateRecursively(int n) {
		//Base Case: Exiting otherwise if become infinite loop
		if (n == 1) {
			return n;
		}
		//Logic: Execution loop via Recursion.
		else {
			return n + calculateRecursively(n - 1);
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
