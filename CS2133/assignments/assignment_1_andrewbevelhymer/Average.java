import java.util.Scanner;

public class Average {
  public static void main(String[] args) {
    System.out.println("Enter a series of numbers. Enter a negative number to quit.");

    Scanner scan = new Scanner(System.in);

    int count = 0;
    double avg;
    double sum = 0;

    while (true) {
      // Getting the number from the command line and
      // converting it to a double
      double num = Double.parseDouble(scan.nextLine());

      // This exits the program when the number is negative
      if (num < 0) break;

      sum += num;
      count++;
    }

    avg = sum / count;

    System.out.println("You entered " + count + " numbers averaging " + avg + ".");
  }
}
