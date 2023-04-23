package labs.lab2;

import java.util.Scanner;

public class Exercise1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size;
		
		System.out.println("Enter the base size of your triagnle"); // How many stars in the base of the triangle
		size = in.nextInt();
		
		for (int i = 0; i < size; i++) { // Counts the line
			for (int j = 0; j < size; j++) { // Counts the stars in the line
				if (j <= i) {
					System.out.print("* "); // Only creates stars if J is less than or equal to the line
				}else {
					System.out.print("  ");
				}
			}
			System.out.println("");
		}

	}

}

/* Test Table
 * 10 = Gave me a triangle where the base had 10 stars
 * 5 = Gave me a triangle where the base had 5 stars
 * 4 = Gave me a triangle where the base had 4 stars
 */