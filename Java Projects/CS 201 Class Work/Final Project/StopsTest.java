package project;


/* The point of this class is to test the Stops.java file, where the constructors for a stop is set, then
 * the system spits out the stop with all the info that it needs. This program will test 3 of the constructors
 * by running it then printing out the to string method.
 * Stefan Urosevic 12/8/2020
 */
public class StopsTest {

	public static void main(String[] args) {
		Stops stop1 = new Stops();
		System.out.println(stop1.toString());
		// Should print all of the parameters highlighted in the default constructor.
		
		Stops stop2 = new Stops("Stop 2", 81, 63);
		System.out.println(stop2.toString());
		// Should print the given custom parameters, then print the default parameters.
		
		int[] services = new int[] {0, 0, 0, 0, 1, 3, 0};
		Stops stop3 = new Stops("Stop 3", 80, 45, "Elevated", false, services);
		System.out.println(stop3.toString());
		// Should print out all of the custom parameters.
	}

}
