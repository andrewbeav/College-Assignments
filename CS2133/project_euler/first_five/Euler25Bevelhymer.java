// Project Euler Problem 25
// =============================================================================================
// The Fibonacci sequence is defined by the recurrence relation:
// 	Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1
// Hence the first 12 terms will be:
// 	F1 = 1
// 	F2 = 1
// 	F3 = 2
// 	F4 = 3
// 	F5 = 5
// 	F6 = 8
// 	F7 = 13
// 	F8 = 21
// 	F9 = 34
// 	F10 = 55
// 	F11 = 89
// 	F12 = 144
// The 12th term, F12, is the first term to contain three digits.
// What is the index of the first term in the Fibonacci sequence to contain 1000 digits?
// =============================================================================================

import java.math.*;

public class Euler25Bevelhymer {
	public static void main(String[] args) {
		BigInteger num1 = BigInteger.valueOf(1);
		BigInteger num2 = BigInteger.valueOf(1);

		int index = 1;

		while(true) {
			index++;

			BigInteger temp = num2;
			num2 = num1.add(num2);
			num1 = temp;

			if (findNumOfDigits(num1) >= 1000) break;
		}
		System.out.println(index);
	}

	public static int findNumOfDigits(BigInteger n) {
		String numString = n.toString();
		return numString.length();
	}
} 
