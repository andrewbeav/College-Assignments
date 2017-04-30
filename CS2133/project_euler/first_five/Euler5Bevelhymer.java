// Problem 5
// =============================================================================================
// 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
// What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
// =============================================================================================

public class Euler5Bevelhymer {
	public static void main(String[] args) {
		int toFind = 40; // starting with 40 because it is the first number evenly divisible by 20
		boolean isMultiple = false;

		while(true) {
			for (int i = 1; i <= 20; i++) {
				if (toFind % i != 0) {
					break;
				}
				if (i == 20) isMultiple = true;
			}
			if (isMultiple == true) break;
			toFind += 2;
		}
		System.out.println(toFind);
	}
}
