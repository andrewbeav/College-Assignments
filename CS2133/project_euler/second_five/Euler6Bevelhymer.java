// Project Euler - Problem 6
// The sum of the squares of the first ten natural numbers is 1^2 + 2^2 + ... + 10^2 = 385
// The square of the sum of the first ten natural numbers is (1 + 2 + ... + 10)^2 = 552 = 3025
//
// Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
// Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.

public class Euler6Bevelhymer {
	public static void main(String[] args) {
		int squareSum = 0, regSum = 0;
		for (int i = 0; i <= 100; i++) {
			squareSum += i*i;
			regSum += i;
		}
		System.out.println(Math.pow(regSum, 2) - squareSum);
	}
}
