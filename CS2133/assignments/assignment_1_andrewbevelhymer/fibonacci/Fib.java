public class Fib {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);

    System.out.println(fibonacci(n));
  }

  public static int fibonacci(int n) {
    if (n <= 1) return n; // return n if it is 1 since F(1)=1
    else return fibonacci(n-1) + fibonacci(n-2); // F(n) = F(n-1)+F(n-2)
  }
}
