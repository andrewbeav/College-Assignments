// Project Euler - Problem 4
// A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
//
// Find the largest palindrome made from the product of two 3-digit numbers.

import java.util.*;

public class Euler4Bevelhymer {
	public static void main(String[] args) {
		int maxPal = 0;
		
		for (int i = 100; i < 1000; i++) {
			for (int j = 100; j < 1000; j++) {
				if (isPalindrome(i * j) && i*j > maxPal) {
					maxPal = i * j;
				}
			}
		}

		System.out.println(maxPal);
	}

	public static boolean isPalindrome(int n) {
		ArrayList<Integer> digits = new ArrayList<>();
		while (n != 0) {
			digits.add((int)(n % 10));
			n /= 10;
		}
		return isPalindrome(digits);
	}

	public static boolean isPalindrome(ArrayList<Integer> digits) {
		int[] palindromeTestArray = new int[digits.size()];
		int count = 0;
		for (int i = digits.size()-1; i >= 0; i--) {
			palindromeTestArray[count] = digits.get(i);
			count++;
		}
		for (int i = 0; i < palindromeTestArray.length; i++) {
			if (palindromeTestArray[i] != digits.get(i)) return false;
		}

		return true;
	}
}
