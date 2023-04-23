package labs.lab4;

import java.util.Scanner;

public class GeoApplication {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); // Setting the variables for inputting coordinates
		double lat;
		double lng;
		
		System.out.println("Here is an example of a geo location application:"); // Example of the default application in the code
		GeoLocation loc1 = new GeoLocation();
		System.out.println(loc1.getLat() + ", " + loc1.getLng()); // Displaying the location via the accessors
		
		System.out.println("Please enter coordinates, -90 to 90 for lat, and -180 to 180 for lng"); // Prompts the user for input
		System.out.println("Lat: ");
		lat = in.nextDouble(); // Input Latitude
		System.out.println("Lng: ");
		lng = in.nextDouble(); // Input Longitude
		GeoLocation loc2 = new GeoLocation(lat, lng); // Inputs the data into the class
		System.out.println(loc2.getLat() + ", " + loc2.getLng()); // Displaying the location via the accessors
		
		System.out.println("Finished"); // Signifies the program is finished and closes the scanner
		in.close();
	}

}
