package labs.lab2;

import java.util.Scanner;

public class Exercise3 {

	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		int command = 0;
		double math1;
		double math2;
		double result;
		
		while (command != 4) { // Will loop the program until 4(exit) is selected
			
			System.out.println("1. Say Hello - This should print 'Hello' to console.");
			System.out.println("2. Addition - This should prompt the user to enter 2 nubers and return the sum of the two.");
			System.out.println("3. Multiplication - This should prompt the user to enter 2 numbers and return the product of the two.");
			System.out.println("4. Exit - Leave the program");
			
			
			command = in.nextInt();
			
			switch(command) { // Switch reads the command input to dictate next move
			case 1:
				System.out.println("> Hello"); // Hello Command
				System.out.println("--------------------");
				break;
			
			case 2:
				System.out.println("> Addition Selected"); // Addition Command
				System.out.println("  Enter your First Number");
				math1 = in.nextDouble();
				System.out.println("  Enter your Second Number");
				math2 = in.nextDouble();
				result = math1 + math2;
				System.out.println("  " + math1 + " + " + math2 + " = " + result);
				System.out.println("--------------------");
				break;
				
			case 3:
				System.out.println("> Multiplication Selected"); // Multiplication Command
				System.out.println("  Enter your First Number");
				math1 = in.nextDouble();
				System.out.println("  Enter your Second Number");
				math2 = in.nextDouble();
				result = math1 * math2;
				System.out.println("  " +math1 + " + " + math2 + " = " + result);
				System.out.println("--------------------");
				break;
				
			case 4:
				System.out.println("> Exiting the program"); // Exit Command
				break;
			
			default: // Will loop if any other number besides 1-4 is inputed
				break;
			}
		}

	}

}

/* TEST TABLE
 * 1 = Hello
 * 4 = Exiting the program
 * 3 = input(11 * 12) = 132 Multiplication
 * 2 = input(5 + 7) = 12 Addition
 */
