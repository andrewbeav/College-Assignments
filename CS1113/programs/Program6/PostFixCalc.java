// =========================================================================
// CS 1113 Program 6 : PostFix Calculator
// Semester : Fall 2016
//
// Andrew Bevelhymer
// 61049
//
// Description: PostFix Calculator with memory
// =========================================================================

import java.util.*;

public class PostFixCalc
{
  // Declaring HashMap to use for memory
  public static HashMap<String, Double> memory = new HashMap<>();

  public static void main(String[] args)
  {
    // Printing info
    System.out.println("Postfix Calculator with Memory by Andrew Bevelhymer\n");

    Scanner scan = new Scanner(System.in); // Creating scanner

    // Infinite loop to continously prompt user for input
    while(true)
    {
      System.out.print("> "); // Prompt user

      // Getting line and splitting it by spaces
      String line = scan.nextLine();
      String[] stringValues = line.split(" ");

      // If the program starts with a space or user hits enter continue
      if (stringValues.length == 0 || stringValues.length > 0 && stringValues[0].equals(""))
      {
        continue;
      }
      // Quit if user types quit
      else if (stringValues[0].equalsIgnoreCase("quit"))
      {
        break;
      }
      else if (stringValues[0].equalsIgnoreCase("clear"))
      {
        // Clear variables
        memory.clear();
      }
      else if (stringValues[0].equalsIgnoreCase("delete"))
      {
        if (stringValues.length > 1)
        {
          memory.remove(stringValues[1]);
        }
        else
        {
          usage();
        }
      }
      else if (stringValues[0].equalsIgnoreCase("var"))
      {
        // Print all variable names
        System.out.println(memory);
      }
      // Assignment
      else if (stringValues.length > 1 && stringValues[1].equals("="))
      {
        if (calculate(stringValues, true) != null)
        {
          memory.put(stringValues[0], calculate(stringValues, true));
          System.out.println(memory.get(stringValues[0]));
        }
        else usage();
      }
      // Postfix expression
      else
      {
        // Call the calculate method if the input was proper
        // If not print usage statement
        if (calculate(stringValues, false) != null) System.out.println(calculate(stringValues, false));
        else usage();
      }
    }
  }

  // Usage method:
  // Prints usage statement to screen
  public static void usage()
  {
    System.out.println("Given input is not a command, PostFix expression, or Assignment");
    System.out.println("Please enter proper input");
    System.out.println();
    System.out.println("Example Input:");
    System.out.println();
    System.out.println("  Commands:");
    System.out.println("    quit: quit the program");
    System.out.println("    clear: remove all variables");
    System.out.println("    delete [varName]: Deletes given variable");
    System.out.println("    var: print all variables");
    System.out.println();
    System.out.println("  PostFix expression (+, -, *, /, ^):");
    System.out.println("    5 5 +");
    System.out.println("    [variable] 5 *");
    System.out.println("    [variable] 5 + 6 *");
    System.out.println();
    System.out.println("  Assignment (create variable):");
    System.out.println("    [variable] = 4");
    System.out.println("    [variable] = 5 5 +");
  }

  // This method is used to calculate a postfix expression
  // using Double instead of double so that null can be returned
  // if the input is invalid
  public static Double calculate(String[] stringValues, boolean isAssignment)
  {
    // Declaring stack to use for operations
    Stack<Double> stack = new Stack<>();

    // Initializing result variable to zero
    Double result = 0.0;

    int startPos;
    if (isAssignment) startPos = 2; // if it is an assignment start the expression at 2
    else startPos = 0;

    Scanner scan = new Scanner(stringValues[startPos]);

    // If statement to see if the value is just one number
    if ((startPos == 2 && stringValues.length == 3 || stringValues.length == 1) && scan.hasNextDouble())
    {
      result = Double.parseDouble(stringValues[startPos]);
    }
    else
    {
      try
      {
        for (int i = startPos; i < stringValues.length; i++)
        {
          // Creating scanner to see if it is a double
          Scanner valueScan = new Scanner(stringValues[i]);

          if (stringValues[i].equals("+"))
          {
            double num2 = stack.pop();
            double num1 = stack.pop();
            result = num1 + num2;
            stack.push(result);
          }
          else if (stringValues[i].equals("-"))
          {
            double num2 = stack.pop();
            double num1 = stack.pop();
            result = num1 - num2;
            stack.push(result);
          }
          else if (stringValues[i].equals("*"))
          {
            double num2 = stack.pop();
            double num1 = stack.pop();
            result = num1 * num2;
            stack.push(result);
          }
          else if (stringValues[i].equals("/"))
          {
            double num2 = stack.pop();
            double num1 = stack.pop();
            result = num1 / num2;
            stack.push(result);
          }
          else if (stringValues[i].equals("^"))
          {
            double num2 = stack.pop();
            double num1 = stack.pop();
            result = Math.pow(num1, num2);
            stack.push(result);
          }
          else if (valueScan.hasNextDouble()) // Value is a double
          {
            stack.push(Double.parseDouble(stringValues[i])); // Push to stack
          }
          else if (memory.containsKey(stringValues[i])) // Value is a predefined variable
          {
            stack.push(memory.get(stringValues[i]));
          }
          else // Value is a variable that hasn't been made yet
          {
            memory.put(stringValues[i], 0.0);
            stack.push(memory.get(stringValues[i]));
          }
        }

        // The following lines of code are used to check if
        // The stack had more than one element left
        stack.pop();
        if (!stack.empty())
        {
          result = null;
        }

        stack.clear(); // Clearing stack
      }
      catch(EmptyStackException e)
      {
        result = null;
      }
    }
    return result;
  }
}