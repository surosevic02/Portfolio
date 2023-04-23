package labs.lab4;

import java.util.Scanner;

public class DateAppication {

	public static void main(String[] args) {
		// Initializing the variables required for date manipulation
		Scanner in = new Scanner(System.in);
		int day;
		int month;
		int year;
		
		// Tells the user what the default date constructor looks like 
		System.out.println("Default date looks like this DD/MM/YY");
		Date date1 = new Date();
		System.out.println(date1.toString());
		
		// Prompts the user to enter variables for a nun default date constructor
		System.out.println("Please enter a date when prompted for each component");
		System.out.println("Year: "); // Year is prompted and entered
		year = in.nextInt();
		System.out.println("Month: "); // Month is prompted and entered
		month = in.nextInt();
		System.out.println("Day: "); // Day is prompted and entered
		day = in.nextInt();
		Date date2 = new Date(year, month, day); // New Date is initialized and printed to screen
		System.out.println(date2.toString());
		
		// Tells the use the program is finished
		System.out.println("Finished");
		in.close();
	}

}
