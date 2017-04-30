public class FunctionTest {
	public static void main(String[] args) {
		System.out.println(findRoot(3, 4, 0.00000001));
	}

	public static double findRoot(double a, double b, double epsilon) {
		double x = (a+b)/2;
		if (Math.abs(a-x) < epsilon) return x;
		else if (hasSameSign(Math.sin(x), Math.sin(a))) return findRoot(x, b, epsilon);
		else return findRoot(a, x, epsilon);
	}

	public static boolean hasSameSign(double a, double b) {
		boolean isAPositive = false, isBPositive = false;
		if (a >= 0) isAPositive = true;
		if (b >= 0) isBPositive = true;

		return (isAPositive == isBPositive);
	}
}
