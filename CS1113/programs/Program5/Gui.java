import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Gui extends JFrame
{
  private JButton getFileButton;
  private JButton sortButton;
  private JTextArea resultsText;
  private JFileChooser fileChooser;
  private JLabel sortFieldLabel;

  private JRadioButton selectId;
  private JRadioButton selectFirst;
  private JRadioButton selectLast;
  private JRadioButton selectType;
  private JRadioButton selectTime;
  private ButtonGroup buttonGroup;

  public String filePath;
  public String sortField;

  public Gui()
  {
    super("Blood Donor Sorting Progam");
    setLayout(new FlowLayout());

    // Creating icon for the frame
    ImageIcon icon = new ImageIcon("icon.png");
    setIconImage(icon.getImage());

    getFileButton = new JButton("Select Input File");
    sortButton = new JButton("sort");
    resultsText = new JTextArea(10, 50);

    add(getFileButton);
    add(sortButton);
    add(resultsText);

    sortFieldLabel = new JLabel("Field to Sort by:");
    add(sortFieldLabel);

    // Initializing buttons and adding them to the Gui
    selectId = new JRadioButton("Id Number", false);
    selectLast = new JRadioButton("Last Name", false);
    selectFirst = new JRadioButton("First Name", false);
    selectType = new JRadioButton("Blood Type", false);
    selectTime = new JRadioButton("Donation Time", false);
    add(selectId);
    add(selectLast);
    add(selectFirst);
    add(selectType);
    add(selectTime);

    // Initializing button group and adding button to the group
    buttonGroup = new ButtonGroup();
    buttonGroup.add(selectId);
    buttonGroup.add(selectLast);
    buttonGroup.add(selectFirst);
    buttonGroup.add(selectType);
    buttonGroup.add(selectTime);

    selectId.addItemListener(new RadioButtonEventHandler("id"));
    selectLast.addItemListener(new RadioButtonEventHandler("last"));
    selectFirst.addItemListener(new RadioButtonEventHandler("first"));
    selectType.addItemListener(new RadioButtonEventHandler("type"));
    selectTime.addItemListener(new RadioButtonEventHandler("time"));

    ButtonEventHandler buttonHandler = new ButtonEventHandler();
    sortButton.addActionListener(buttonHandler);

    BrowseEventHandler browseHandler = new BrowseEventHandler();
    getFileButton.addActionListener(browseHandler);
  }

  private class RadioButtonEventHandler implements ItemListener
  {
    private String results;
    public RadioButtonEventHandler(String field)
    {
      results = field;
    }
    public void itemStateChanged(ItemEvent event)
    {
      sortField = results;
    }
  }

  private class BrowseEventHandler implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      JButton open = new JButton();
      fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      fileChooser.setDialogTitle("Select File to be Sorted");
      if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION);
      filePath = fileChooser.getSelectedFile().getAbsolutePath();
    }
  }

  private class ButtonEventHandler implements ActionListener
  {
    public String results;

    public boolean isInputValid = true;

    public void actionPerformed(ActionEvent event)
    {
      // Creating string to hold the usage statement
      String usageStatement = String.format("Enter the name of the file and the field you would like to sort by in the appropriate field.%nThe file extension is optional.%n");
      usageStatement += String.format("Make sure your file has the necessary values seperated by commas with no spaces. As in this:%n");
      usageStatement += String.format("[Id Number],[Last Name],[First Name],[Blood Type],[Donation Time]%n");

      sort();

      if (isInputValid == true)
      {
        Font newResultsFont = new Font("monospaced", Font.PLAIN, 16);
        resultsText.setFont(newResultsFont);

        resultsText.setText(results);
      }
      else resultsText.setText(usageStatement);
    }

    public void sort()
    {
      // Try/Catch so the program doesn't crash when the user enters
      // a file that doesn't exist
      try
      {
        // Creating new scanner to read in from file
        Scanner file = new Scanner(new File(filePath));

        // Creating array list of blood donors
        ArrayList<BloodDonor> donors = new ArrayList<>();

        // Another try/catch to deal with the file not having proper input
        try
        {
          // Looping through each line of input and adding those values to the donors ArrayList
          while (file.hasNext())
          {
            // Reading line from file
            String line = file.nextLine();

            // Creating array of strings
            String[] values = line.split(",");

            // Creating new BloodDonor objects with the proper values and adding them
            // to the donors ArrayList
            BloodDonor donor = new BloodDonor(Integer.parseInt(values[0]), values[1], values[2], values[3], Double.parseDouble(values[4]));
            donors.add(donor);
          }

          // Sort by idNum if that's what the user entered
          if (sortField.equals("id"))
          {
            // Sort donors ArrayList using the IdComparator class
            Collections.sort(donors, new IdComparator());

            // Print the sorted list
            results = printTable(donors);
          }

          // Sort by lastName if that's what the user entered
          else if (sortField.equals("last"))
          {
            // Sort donors ArrayList using the LastNameComparator class
            Collections.sort(donors, new LastNameComparator());

            // Print the sorted list
            results = printTable(donors);
          }

          // Sort by firstName if that's what the user entered
          else if (sortField.equals("first"))
          {
            // Sort donors ArrayList using the FirstNameComparator class
            Collections.sort(donors, new FirstNameComparator());

            // Print the sorted list
            results = printTable(donors);
          }

          // Sort by bloodType if that's what the user entered
          else if (sortField.equals("type"))
          {
            // Sort donors ArrayList using the TypeComparator class
            Collections.sort(donors, new TypeComparator());

            // Print the sorted list
            results = printTable(donors);
          }

          // Sort by donationTime if that's what the user entered
          else if (sortField.equals("time"))
          {
            // Sort donors ArrayList using DonationTimeComparator class
            Collections.sort(donors, new DonationTimeComparator());

            // Print the sorted list
            results = printTable(donors);
          }

          // Print usage statement if the user did not provide
          // proper input
          else
          {
            isInputValid = false;
            usage();
          }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          isInputValid = false;
          usage();
        }
      }
      catch (FileNotFoundException e)
      {
        isInputValid = false;
        usage();
      }
    }

    // Method to print usage statement
    public void usage()
    {
      System.out.println();
      System.out.println("When prompted, enter a the name of the file. The file extension is optional.");
      System.out.println("Then, type in the name of the field you would like to sort by [id num, last, first, type, time]");
      System.out.println();
      System.out.println("Make sure your file has the necessary values seperated by commas with no spaces. As in this:");
      System.out.println("[Id Number],[Last Name],[First Name],[Blood Type],[Donation Time]");
      System.out.println();
    }

    // Method used to print the sorted list as a table
    public String printTable(ArrayList<BloodDonor> donors)
    {
      String table = "";
      // Printing titles for columns
      table += String.format("%-8s    %-15s %-15s %-8s %-4s %n %n", "Id Num", "Last", "First", "Type", "Time");

      // Printing sorted list
      for (BloodDonor d : donors)
      {
        table += String.format("%-8d    %-15s %-15s %-8s %-4.1f %n", d.idNum, d.lastName, d.firstName, d.type, d.donationTime);
      }

      return table;
    }
  }

}
