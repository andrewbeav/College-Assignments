public class Gregory {
  public static void main(String[] args) {
    long n = Long.parseLong(args[0]);
    double pi = gregory(n);
    double precision = calculatePrecision(pi, Math.PI);

    System.out.println("Pi according to Gregory series: " + pi);
    System.out.println("This differs from Java's value by " + precision + " percent.");
  }

  // This method calculates the percent yield of a result(real)
  // and the number it should be(actual)
  public static double calculatePrecision(double real, double actual) {
    return 100 - (real/actual)*100; // subtracting from 100 because we need to
                                    // know what percent we are off by
  }

  // This method uses the gregory series to calculate pi using n terms of
  // the Gregory sequence
  public static double gregory(long n) {
    double pi = 0;

    for (int i = 1; i <= n; i++) {
      pi += (Math.pow(-1, (i+1))/((2 * i) - 1));
    }

    return 4 * pi; // Multiplying by 4 because the Gregory sequence finds pi/4
  }
}
