// Project Euler Problem 48
// =============================================================================================
// The series, 11 + 22 + 33 + ... + 1010 = 10405071317.
// Find the last ten digits of the series, 11 + 22 + 33 + ... + 10001000.
// =============================================================================================

import java.math.*;

public class Euler48Bevelhymer {
	public static void main(String[] args) {
		BigInteger result = BigInteger.valueOf(0);
		for (int i = 1; i <= 1000; i++) { 
			BigInteger bigI = BigInteger.valueOf(i);
			result = result.add(bigI.pow(i));
		}
		System.out.println(result);
	}
}
