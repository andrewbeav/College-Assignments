// Project Euler - Problem 36
//
// The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.
// Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.

import java.util.*;

public class Euler36Bevelhymer {
	public static void main(String[] args) {
		int sum = 0;
		for (int i = 0; i < 1000000; i++) {
			if (isDoubleBasePalindrome(i)) sum += i;
		}

		System.out.println(sum);
	}

	public static boolean isDoubleBasePalindrome(int n) {
		if (isPalindrome(n) && isPalindrome(Integer.toBinaryString(n))) {
			return true;
		}
		return false;
	}

	public static boolean isPalindrome(String s) {
		String check = "";
		for (int i = s.length()-1; i >= 0; i--) {
			check += s.charAt(i);
		}

		if (s.equals(check)) return true;
		return false;
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
