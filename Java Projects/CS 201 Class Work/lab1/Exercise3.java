package labs.lab1;

import java.util.Scanner;

public class Exercise3 {

	public static void main(String[] args) {
		String nameF;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please type your first name");
		nameF = input.next();
		System.out.println("First initial: " + nameF.charAt(0));
	}

}
