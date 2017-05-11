// =========================================================================
// CS 1113 Program 5 : Blood Sort
// Semester : Fall 2016
//
// Andrew Bevelhymer
// 61049
//
// Description: Sorts list of donors based on selected info
// =========================================================================

import java.util.*;
import java.io.*;
import javax.swing.JFrame;

public class BloodSort
{
  public static void main(String[] args) throws IOException
  {
    Gui gui = new Gui();
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setSize(600, 500);
    gui.setResizable(false);
    gui.setVisible(true);
  }
}
