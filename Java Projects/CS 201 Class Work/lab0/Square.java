package labs.lab0;

/*Psuedo Code
public class square
public static void main(string[] args)
 char symbol = '#';
 for (i = 0, i < 4, i++)
 	for (j = 0, j < 9, j++)
 		System.out.print(symbol)
 	System.out.println()
 	
 	
 	Output:
 	#########
 	#########
 	#########
 	#########
*/

public class Square {

	public static void main(String[] args) {
		char symbol = '#';
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(symbol);
			}
			System.out.println();
		}

	}

}
