public class FibTest {
	public static void main(String[] args) {
		if (fibIter(1) == 1) System.out.println("fibIter(1) returned 1. Test Passed!"); 
		else System.out.println("fibIter(1) returned " + fibIter(1) + " and did not return 1. Test failed.");
		
		if (fibIter(6) == 8) System.out.println("fibIter(6) returned 8. Test passed!");
		else System.out.println("fibIter(6) returned " + fibIter(6) + " and did not return 8. Test failed.");

		if (fibRecur(1) == 1) System.out.println("fibRecur(1) returned 1. Test Passed!"); 
		else System.out.println("fibRecur(1) did not return 1. Test failed.");

		if (fibRecur(6) == 8) System.out.println("fibRecur(6) returned 8. Test passed!");
		else System.out.println("fibRecur(6) did not return 8. Test failed.");
		
		long initTime = System.currentTimeMillis();
		int fibIter40 = fibIter(40);
		long elapsedTime = System.currentTimeMillis() - initTime;
		System.out.println("fibIter(40) returned " + fibIter40 + " and took " + elapsedTime + " milliseconds.");

		initTime = System.currentTimeMillis();
		int fibRecur40 = fibRecur(40);
		elapsedTime = System.currentTimeMillis() - initTime;
		System.out.println("fibRecur(40) returned " + fibRecur40 + " and took " + elapsedTime + " milliseconds.");
	}

	public static int fibIter(int n) {
		int num1 = 1, num2 = 1, count = 1;
		while (count < n) {
			int temp = num2;
			num2 += num1;
			num1 = temp;
			count++;
		}

		return num1;
	}

	public static int fibRecur(int n) {
		if (n <= 1) return n;
		else return fibRecur(n-1) + fibRecur(n-2);
	}
}
