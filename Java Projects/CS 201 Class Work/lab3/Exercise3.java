package labs.lab3;

public class Exercise3 {

	public static void main(String[] args) {
		int[] array = {72, 101, 108, 108, 111, 32, 101, 118, 101, 114, 121, 111, 110, 101, 33, 32, 76, 111, 111, 107, 32, 97, 116, 32, 116, 104, 101, 115, 101, 32, 99, 111, 111, 108, 32, 115, 121, 109, 98, 111, 108, 115, 58, 32, 63264, 32, 945, 32, 8747, 32, 8899, 32, 62421};
		int compare = array[0];
		
		for (int i = 0; i < array.length; i++) { // This for statement runs the length of the array
			if (array[i] < compare ) { // This if statement compares the array with the variable to see which is the lowest
				compare = array[i]; // If the statement is true then the compare variable is set as the array number
			}
		}
		
		System.out.println("The minimum number is: " + compare); // Prints the minimum to the screen
	}

}
