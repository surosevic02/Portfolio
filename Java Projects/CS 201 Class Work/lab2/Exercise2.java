package labs.lab2;

import java.util.Scanner;

public class Exercise2 {

	public static void main(String[] args) {
		int counter = 0;
		double scores = 0;
		double scoresAdded = 0;
		
		Scanner in = new Scanner(System.in);
		
		while (scores != -1) { // Repeats until -1(exit is entered)
			System.out.println("Input Score:"); // Input the score number
			
			scores = in.nextDouble();
			
			if (scores != -1) { // Makes sure -1 isn't entered as a score
				scoresAdded += scores;
				System.out.println("Cumulative Score: " + scoresAdded); // Outputs Cumulative score so ou can keep track
				counter++; // Keeps track of the amount of tests
			}
		}
		
		System.out.println("The Average Test score is: " + (scoresAdded/counter)); // Prints out the average by dividing the cumulative by the amount of tests
	}

}

/* TEST TABLE
 *  100 + 100 + 100 + 100 = 100
 *  50 + 75 + 50 + 25 = 50
 *  25 + 25 + 25 + 75 = 37.5
 */