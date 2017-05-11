// mergeSort (O(n log n)) had significantly less running time than
// bubbleSort (O(n^2))
//
// bubbleSort was already starting to take more than 20 seconds 
// when mergeSort was still taking less than 1 second.

import java.util.*;

public class Sorting {
	public static void main(String[] args) {
		int n = 10;
		while(true) {
			try {
				double[] randomArray = makeRandomArray(n);

				double startTime = System.currentTimeMillis();
				double elapsed = 0;

				if (bubbleSort(randomArray)) {
					elapsed = System.currentTimeMillis()-startTime;
					System.out.println("bubbleSort took " + elapsed/1000 + " seconds for an array of length "+ n);
				}

				startTime = System.currentTimeMillis();
				mergeSort(randomArray);
				elapsed = System.currentTimeMillis()-startTime;
				System.out.println("mergeSort took " + elapsed/1000 + " seconds for an array of length " + n);


			}
			catch(OutOfMemoryError e) {
				System.out.println("Ran out of memory with length " + n);
				break;
			}

						
			n *= 10;
		}
	}

	public static double[] makeRandomArray(int n) {
		double[] randomArray = new double[n];
		for (int i = 0; i < n; i++) {
			randomArray[i] = Math.random();
		}

		return randomArray;
	}

	public static void mergeSort(double[] a) {
		int n = a.length;
		if (n == 1) return;
		int m = n/2;

		double[] b = Arrays.copyOfRange(a, 0, m);
		double[] c = Arrays.copyOfRange(a, m, n);

		mergeSort(b);
		mergeSort(c);

		int i = 0, j = 0, k = 0;
		while(i < m && j < n-m) {
			if (b[i] < c[j]) {
				a[k] = b[i];
				i++;
			}
			else {
				a[k] = c[j];
				j++;
			}
			k++;
		}

		while(i < m) {
			a[k] = b[i];
			k++;
			i++;
		}

		while(j < n-m) {
			a[k] = c[j];
			k++;
			j++;
		}
	}

	// returning true or false for if it was successful or not
	public static boolean bubbleSort(double[] a) {
		double startTime = System.currentTimeMillis();
		for (int i = 0; i < a.length; i++) {
			if (System.currentTimeMillis()-startTime >= 20000) {
				System.out.println("Timeout for bubbleSort on array of length " + a.length);
				return false;
			}
			boolean swapped = false;
			for (int j = a.length-1; j > 0; j--) {
				if (a[j] < a[j-1]) {
					double tmp = a[j];
					a[j] = a[j-1];
					a[j-1] = tmp;
					swapped = true;
				}
			}
			if (!swapped) break;
		}

		return true;
	}
}
