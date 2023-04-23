package labs.lab1;

import java.util.Scanner;

public class Exercise4 {
	public static void main(String[] args) {
		char operation;
		int temp;
		
		Scanner in = new Scanner(System.in);
		System.out.println("Would you like to calculate from C or from F?");
		
		operation = in.next().toLowerCase().charAt(0);
		
		while (operation != 'c' && operation != 'f') {
			System.out.println("Please Select Again");
			operation = in.next().toLowerCase().charAt(0);
		}
		
		switch(operation) {
			case 'c':
				System.out.println("Celsius Selected");
				System.out.println("Please type your temperature rounded to the nearest whole number");
				temp = in.nextInt();
				double celsius = (temp*(1.8)) + 32;
				System.out.println("Converted to F: " + celsius);
				break;
			case 'f':
				System.out.println("Fahrenheit Selected");
				System.out.println("Please type your temperature rounded to the nearest whole number");
				temp = in.nextInt();
				double fahrenheit = ((temp - 32)*(0.5556));
				System.out.println("Converted to C: " + fahrenheit);
				break;
		}
		

	}
	//Tables For Testing
	/* 32F - 0C Got 0
	 * 40C - 104F Got 104
	 * 20C - 68F Got 68
	 * 59F - 15C  Got 15.01199
	*/
}
