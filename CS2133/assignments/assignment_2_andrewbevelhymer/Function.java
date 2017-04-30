/** A class to define different functions. 
*/
public abstract class Function {
	/**
	 Abstract method that defines the function to be called.
	 @param x The value to be evaulated in the function.
	 @return The result of evaluating the function for x.
	*/
	public abstract double evaluate(double x); 

	/**
	 Finds root of a function between a and b.
	 @param a Value x can't be smaller than.
	 @param b Value x can't be bigger than.
	 @param epsilon Amount of error we are willing to tolerate.
	 @return root of the function that is between a and b with precision defined by epsilon.
	*/
	public double findRoot(double a, double b, double epsilon) {
		double x = (a+b)/2;
		if (Math.abs(a-x) < epsilon) return x;
		else if (hasSameSign(this.evaluate(x), this.evaluate(a))) return findRoot(x, b, epsilon);
		else return findRoot(a, x, epsilon);
	}

	private boolean hasSameSign(double a, double b) {
		boolean isAPositive = false, isBPositive = false;
		if (a >= 0) isAPositive = true;
		if (b >= 0) isBPositive = true;

		if (isAPositive == isBPositive) return true;
		return false;
	}

	public static void main(String[] args) {
		SinFunc sinFunc = new SinFunc();
		System.out.println("SinFunc.findRoot(3, 4, 0.00000001) returns " + sinFunc.findRoot(3, 4, 0.00000001) + "\n");

		CosFunc cosFunc = new CosFunc();
		System.out.println("CosFunc.findRoot(1, 3, 0.00000001) returns " + cosFunc.findRoot(1, 3, 0.00000001) + "\n");
		int[] coefficients = {-3, 0, 1};
		PolyFunc firstPoly = new PolyFunc(coefficients);
		System.out.println("Positive root of " + firstPoly + ": " + firstPoly.findRoot(0, 100, 0.00000001));

		int[] secondCoefficients = {-2, -1, 1};
		PolyFunc secondPoly = new PolyFunc(secondCoefficients);
		System.out.println("Positive root of " + secondPoly + ": " + secondPoly.findRoot(0, 100, 0.00000001));
	}
}
