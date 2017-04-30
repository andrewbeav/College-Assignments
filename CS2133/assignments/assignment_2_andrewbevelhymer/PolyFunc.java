/**
 Function class that implements polynomial functions.
*/
public class PolyFunc extends Function{
	public int[] coefficients;
	
	/**
	 Constructor that initializes the array of coefficients
	 @param coefficients An array of coefficients where the index is the power of the term in the polynomial. i.e. x^2+2 would have the coefficients array [2, 0, 1]
	*/
	public PolyFunc(int[] coefficients) {
		this.coefficients = coefficients;
	}
	
	/**
	 Determines the power of the highest non-zero term in the polynomial
	 @return The power of the highest non-zero term in the polynomial.
	*/
	public int degree() {
		int max = 0, power = 0; 
		for (int i = 0; i < coefficients.length; i++) {
			if (coefficients[i] > max) max = coefficients[i];
		}
		for (int i = 0; i < coefficients.length; i++) {
			if (coefficients[i] == max) power = i;
		}
		return power;
	}

	/**
	 Method that creates a string representation of the polynomial.
	 @return String representation of polynomial.
	*/
	public String toString() {
		String result = "";
		for (int i = coefficients.length-1; i >= 0; i--) {
			if (coefficients[i] == -1) result += ("-" + "x");
			else if (coefficients[i] == -1 && i > 1) result += ("-" + "x^" + i);
			else if (i == 1 && coefficients[i] < 0) result += (coefficients[i] + "x");
			else if (coefficients[i] == 1 && i == 1) result += ("+" + "x");
			else if (coefficients[i] == 1 && i == coefficients.length-1) result += ("x" + "^" + i);
			else if (coefficients[i] == 1) result += ("+" + "x^" + i);
			else if (i == 1 && coefficients[i] !=0) result += ("+" + coefficients[i] + "x");
			else if (i == 0 && coefficients[i] < 0) result += coefficients[i];
			else if (i == 0 && coefficients[i] != 0) result += ("+" + coefficients[i]);
			else if (coefficients[i] < 0 || i == coefficients.length-1) result += (coefficients[i] + "x^" + i);
			else if (coefficients[i] != 0) result += ("+" + coefficients[i] + "x^" + i); 
		}
		return result;
	}

	/**
	 Method that adds a PolyFunc to this.
	 @param a PolyFunc object that should be added to this.
	 @return PolyFunc object that is the sum of this + a.
	*/
	public PolyFunc add(PolyFunc a) {
		int length = 0;
		if (a.coefficients.length > this.coefficients.length) length = a.coefficients.length;
		else length = this.coefficients.length;

		int[] newCoefficients = new int[length];

		for (int i = 0; i < length; i++) {
			if (i < coefficients.length && i < a.coefficients.length) {
				newCoefficients[i] = coefficients[i] + a.coefficients[i];
			}
			else if (a.coefficients.length > this.coefficients.length) {
				newCoefficients[i] = a.coefficients[i];
			}
			else {
				newCoefficients[i] = coefficients[i];
			}
		}
		return new PolyFunc(newCoefficients);
	}

	/**
	 Evaluates the polynomial at x = x.
	 @param x The value to plug in the polynomial for x.
	 @return Value of the polynomial at x = x.
	*/
	public double evaluate(double x) {
		double result = 0;
		for (int i = coefficients.length-1; i >= 0; i--) {
			result += Math.pow(x, i) * coefficients[i];
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("Test for the polynomial 2x^5+3x^4-8x^2+4:\n");

		int[] coefficients = {4, 0, -8, 0, 3, 2};
		PolyFunc poly = new PolyFunc(coefficients);

		System.out.print("Power of highest term in polynomial: " + poly.degree());
		if (poly.degree() == 0) System.out.println(". Test passed!\n");
		else System.out.println(". Test failed.\n");

		System.out.print("String representation of the polynomial: " + poly);
		if (poly.toString().equals("2x^5+3x^4-8x^2+4")) System.out.println(". Test passed!\n");
		else System.out.println(". Test failed.\n");

		int[] addingCoefficients = {0, -2, 4, 1};
		PolyFunc addingPoly = new PolyFunc(addingCoefficients);
		System.out.print("Add test: (" + poly + ") + (" + addingPoly + ") = " + poly.add(addingPoly));
		if (poly.add(addingPoly).toString().equals("2x^5+3x^4+x^3-4x^2-2x+4")) System.out.println(". Test passed!\n");
		else System.out.println(". Test failed.\n");

		System.out.print("Evaluate Test: ");
		if (poly.evaluate(2.0) == 84) System.out.println("poly.evaluate(2.0) returned 84. Test passed!"); 
		else System.out.println("poly.evaluate(2.0) returned " + poly.evaluate(2.0) + ". Test failed.");
	}
}
