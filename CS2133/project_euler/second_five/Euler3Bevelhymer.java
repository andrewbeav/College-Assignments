// Project Euler - Problem 3
// The prime factors of 13195 are 5, 7, 13 and 29.
//
// What is the largest prime factor of the number 600851475143 ?

public class Euler3Bevelhymer {
	public static void main(String[] args) {
		long toCheck = 600851475143L;
		long maxPrimeFactor = 0;
		for (long i = 3; i*i <= toCheck; i += 2) {
			if (toCheck % i == 0 && isPrime(i)) {
				maxPrimeFactor = i;				
			}
		}

		System.out.println(maxPrimeFactor);
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
