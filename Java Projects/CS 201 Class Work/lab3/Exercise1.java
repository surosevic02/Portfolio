package labs.lab3;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Exercise1 {

	public static void main(String[] args) throws IOException{
		int TotalEntries = 14; // 14 is the amount of grades in the file
		double Total;
		File file = new File("src/labs/lab3/grades.csv"); // Finds the file where the test scores are stored
		Scanner in = new Scanner(file); // Loads the file in a scanner
		int[] TestScores = new int[TotalEntries]; // Sets an array based on the amount of grade enteries
		
		for(int i = 0; i < TotalEntries; i++){
			String temp = new String(in.nextLine()); // Stores a line of text from a file into a string
			String[] Split = temp.replace("%", "").split(","); // Removes the % and splits the numbers from the text via the "," and stores it into a string
			TestScores[i] = Integer.parseInt(Split[1]); // Changes the left over numbers from a string to an integer
		}
		in.close(); // Scanner no longer needed
		
		Total = IntStream.of(TestScores).sum(); // Adds the array up
		
		System.out.println("The class average is: " + Total/TotalEntries); // Divides the array by the entries to get the average
	}

}