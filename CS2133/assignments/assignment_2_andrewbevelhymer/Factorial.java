public class Factorial {
	public static void main(String[] args) {
		if (calculate(0) == 1) System.out.println("Factorial.calculate(0) returned 1. Test passed!");
		else System.out.println("Factorial.calculate(0) did not return 1. Test failed.");

		if (calculate(5) == 120) System.out.println("Factorial.calculate(5) returned 120. Test passed!");
		else System.out.println("Factorial.calcualte(5) did not return 120. Test failed.");
	}

	public static long calculate(long n) {
		if (n < 0 || n > 20) {
			System.out.println("Factorial Error: n must be between 0 and 20");
			System.exit(0);
		}
		else if (n == 0) return 1;
		return n * calculate(n-1);
	}
}
