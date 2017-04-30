// Problem 7
// =============================================================================================
// By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
// What is the 10001st prime number?
// =============================================================================================

public class Euler7Bevelhymer {

	public static void main(String[] args) {
		System.out.println(findNthPrime(10001));
	}

	public static int findNthPrime(int n) {
		int count = 0, i = 2;
		while (count != n) {
			if (isPrime(i)) count++;
			if (count != n) i++; 
		}
		return i;
	}

	public static boolean isPrime(int toCheck) {
		for (int i = 2; i < toCheck; i++) {
			if (toCheck % i == 0) return false;
		}
		return true;
	}
}
