import java.io.*;
import java.util.*;

public class Caesar {
	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			System.exit(0);
		}

		int key = 0;
		try {
			key = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			usage();
			System.exit(0);
		}
		String inFileName = args[1];
		if (args.length == 2) encrypt(key, inFileName);
		else {
			String outFileName = args[2];
			encrypt(key, inFileName, outFileName);
		}
	}

	public static void encrypt(int key, String inFileName) {
		try {
			Scanner fileScan = new Scanner(new File(inFileName));
			String output = "";
			while(fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				for(int i = 0; i < line.length(); i++) {
					int charValue = (int)line.charAt(i);
					char outputChar;
					if (charValue < 32 || charValue > 126) {
						outputChar = (char)charValue;
					}
					charValue += key;
					if (charValue > 126) charValue -= 95;
					if (charValue < 32) charValue += 95;
					outputChar = (char)charValue;
					output += outputChar;
				}
				output += "\n";
			}
			System.out.println(output);
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.exit(0);
		}
	}

	public static void encrypt(int key, String inFileName, String outFileName) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outFileName);
			Scanner fileScan = new Scanner(new File(inFileName));
			String output = "";
			while(fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				for(int i = 0; i < line.length(); i++) {
					int charValue = (int)line.charAt(i);
					char outputChar;
					if (charValue < 32 || charValue > 126) {
						outputChar = (char)charValue;
					}
					charValue += key;
					if (charValue > 126) charValue -= 95;
					if (charValue < 32) charValue += 95;
					outputChar = (char)charValue;
					output += outputChar;
				}
				output += "\n";
			}
			writer.println(output);

		}
		catch(FileNotFoundException e) {
			System.out.println("Error while writing to file " + outFileName);
			System.exit(0);
		}
		finally {
			writer.close();
		}
	} 

	public static void usage() {
		System.out.println("Usage: java Caesar key infile [outfile]");
	}
}
