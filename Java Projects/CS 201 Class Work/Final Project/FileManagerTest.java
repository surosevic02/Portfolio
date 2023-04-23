package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* This file was created to test the file manager system for the application, it does this by reading the
 * list of stops from the given file and printing out that list from an array. The next test it performs is
 * Writing to a file, it does this by asking the user for a file name to save the word 'Test' then it writes
 * the file as a .txt file.
 * Stefan Urosevic 12/8/2020
 */

public class FileManagerTest {
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		FileManager fM = new FileManager();
		ArrayList<Stops> stops = fM.readStationData();
		// This should read the data then return all the stops in an array List for the purpose of this test
		System.out.print(stops);
		
		System.out.println("Please enter the file name where you'd like to save your text as");
		fM.saveTrip(in, "Test");
		// This should prompt the user for the name of the file they'd like to save the word text in.
		// the word  Test in a txt file.
	}

}
