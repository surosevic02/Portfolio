package labs.lab1;

import java.util.Scanner;

public class Exercise5 {

	public static void main(String[] args) {
		float length;
		float depth;
		float height;
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter all numbers in inches and rounded to the nearest whole number!");
		System.out.println("Enter Length: ");
		length = input.nextFloat();
		float lengthFt = length/12;
		
		System.out.println("Enter Depth: ");
		depth = input.nextFloat();
		float depthFt = depth/12;
		
		System.out.println("Enter Height: ");
		height = input.nextFloat();
		float heightFt = height/12;
		
		float calculate = 2*((depthFt*lengthFt)+(heightFt*lengthFt)+(heightFt*depthFt));
		
		System.out.println("The Amount of wood needed in square feet is: " + calculate);
	}
	
	/*
	 * Testing
	 * 24L 24D 24H = 24
	 * 12L 12D 12H = 6
	 * 60L 96D 24H = 132
	 */

}
