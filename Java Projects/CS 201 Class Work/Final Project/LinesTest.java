package project;

import java.util.ArrayList;

/* The point of this class is to test Lines.java in which the the program takes all the stops and organizes 
 * them into what lines they serve, then it segregates these lines so the output can be an array of arrayLists
 * that carry the stops for a specific line. This application will print out the stops each line serves.
 * Stefan Urosevic 12/8/2020
 */

public class LinesTest {

	public static void main(String[] args) {
		Lines l = new Lines();
		FileManager fM = new FileManager();
		ArrayList<Stops> stops = fM.readStationData();
		
		ArrayList[] lists = l.storeStops(stops);
		
		System.out.println("Red   : " + lists[0]);
		System.out.println("Green : " + lists[1]);
		System.out.println("Blue  : " + lists[2]);
		System.out.println("Brown : " + lists[3]);
		System.out.println("Purple: " + lists[4]);
		System.out.println("Pink  : " + lists[5]);
		System.out.println("Orange: " + lists[6]);
	}

}
