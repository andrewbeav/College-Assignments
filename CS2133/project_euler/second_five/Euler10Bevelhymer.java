// Project Euler - Problem 10
//
// The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
// Find the sum of all the primes below two million.

public class Euler10Bevelhymer {
	public static void main(String[] args) {
		long sum = 2;
		for (long i = 3; i <= 2000000; i += 2) {
			if (isPrime(i)) sum += i;
		}
		System.out.println(sum);
	}
	public static boolean isPrime(long n) {
		if (n % 2 == 0) {
			return false;
		}

		for (long i = 3; i*i <= n; i += 2) {
			if (n % i == 0) return false;
		}
		return true;
	}

}
