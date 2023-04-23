package labs.lab1;

import java.util.Scanner;

public class Exercise6 {

	public static void main(String[] args) {
		float inch;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Input number in inches rounded to the nearest whole number to be converted to centimeter");
		inch = in.nextFloat();
		System.out.println("When converted to cm the length is: " + inch*2.54);
	}
	/* Test
	 * 1 = 2.45
	 * 10 = 25.4
	 * 7 = 17.78
	 * 77 = 195.58
	 */
}
