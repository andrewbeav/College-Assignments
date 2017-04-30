public class Ramanujan {
	public static void main(String[] args) {
		if (args.length > 0) {
			int n = Integer.parseInt(args[0]);
			double pi = ramanujan(n);
			System.out.println("Pi according to ramanujan series: " + pi);
			System.out.println("This differs from java's value by " + calculatePrecision(pi, Math.PI) + "%");
		}
		else {
			System.out.println("Usage: java Ramanujan <n>");
		}
	}

	public static double calculatePrecision(double real, double actual) {
		double error = Math.abs(real);
    		return 100 - (error/actual)*100;
  	}

	public static double ramanujan(int n) {
		double pi = 0;
		for (int k = 0; k <= n; k++) {
			
			pi += (Factorial.calculate(4 * k) * (1103 + 26390 * k))/(Math.pow(Factorial.calculate(k), 4)*Math.pow(396, 4*k));
		}
		pi *= (2*Math.sqrt(2))/9801;
		return Math.pow(pi, -1);
	}
}
