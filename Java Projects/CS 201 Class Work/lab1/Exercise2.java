package labs.lab1;

public class Exercise2 {

	public static void main(String[] args) {
		int myAge = 18;
		int fathersAge = 58;
		int birthYear = 2002;
		int heightInches = 78;
		
		System.out.println("You and your fathers age difference is: " + (fathersAge - myAge)); //My age subtracted from my fathers age
		System.out.println("Your birth year multiplied by 2 is: " + (birthYear * 2)); //Birth year 2002 * 2
		System.out.println("Your Height in cm is: " + (heightInches * 2.54) + "cm"); //Converting height from inches to cm
		System.out.println("Your height in feet is: " + (heightInches/12) + "ft " + (heightInches % 12) + "in"); //Height in Feet
	}

}
