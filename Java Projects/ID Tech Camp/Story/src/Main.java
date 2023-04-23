import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner adjectiveInput = new Scanner (System.in); 
		System.out.println("Add a color");
		String color = adjectiveInput.nextLine();
		Scanner nameInput = new Scanner (System.in);
		System.out.println("Add a name");
		String name  = nameInput.nextLine();
		Scanner verbInput = new Scanner (System.in);
		System.out.println("Add a verb");
		String verb = verbInput.nextLine();
		System.out.println( name +" " + verb +" over the " + color + " moon!");
	}

}
