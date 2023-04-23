package labs.lab3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Exercise2 {

	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		String enter = "0";
		int counter = 0;
		String[] Numbers = new String[1];
		
		System.out.println("Please type what ever you'd like to!");
		
		while(!enter.equalsIgnoreCase("done")) { // Stops the repetition of input once done is typed in
			enter = in.next(); // Input
			if (!enter.equalsIgnoreCase("done")) { // Makes sure that any code after this doesn't run after done is typed in
				if (counter == Numbers.length) { // If the counter is as big as the array length it runs code in the if statement
					String[] temp = new String[Numbers.length + 1]; // Creates a temp array to store the current contents of the array
					for (int i = 0; i < Numbers.length; i++) { // For loop to make sure that the temp array gets all the old array info
						temp[i] = Numbers[i];
					}
					Numbers = temp; // Sets the new array equal to the temp array to make sure it's expanded
				}
				Numbers[counter] = enter; // Enters the entry at the counters position in the array
				counter++; // Counts how many times the repetition part has looped
			}
		}
		
		System.out.println(Arrays.toString(Numbers));
		
		System.out.println("What do you wish to save this file as? (Inlude an extention like .txt)"); // Prompts the use for a file name to save the array
		enter = in.next();
		in.close();
		
		String path = new String("src/labs/lab3/" + enter); // Sets a universal path
		
		File save = new File(path); // Sets the file location
		save.createNewFile(); // Create the file
		
		FileWriter writer = new FileWriter(path); // Sets the writing path
		
		for (int i = 0; i < Numbers.length; i++) { // This for loop writes all the values in the file
			writer.write(Numbers[i] + ", "); // This writes the values to the file with commas after the values
		}
		writer.close();
	}
}