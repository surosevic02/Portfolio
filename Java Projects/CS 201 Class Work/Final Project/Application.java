package project;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//The point of this class is to be the menu the user can use to interact with the different classes of the program to complete
//their tasks. Furthermore, this class ties all the classes together into one functioning program.
//Stefan Urosevic, 10/29/2020
public class Application {
	
	// This method is just the menu itself, setting all the required variables and creating the needed objects.
	public static void main(String[] args) throws IOException {
		boolean done = false;
		int choice;
		String yn = "0";
		FileManager fM = new FileManager();
		Scanner in = new Scanner(System.in);
		Lines ln = new Lines();
		SearchEngine se = new SearchEngine();
		
		ArrayList<Stops> stops = fM.readStationData();
		
		System.out.println("Welcome to the CTA Line Application, Please select an Option between 1-7");
		
		do {
			ArrayList[] lists = ln.storeStops(stops);

			System.out.println("_______________________________");
			System.out.println("1. Add Station");
			System.out.println("2. Modify Station");
			System.out.println("3. Remove Station");
			System.out.println("4. Search for a Station");
			System.out.println("5. Find Route");
			System.out.println("6. Find Nearest Station");
			System.out.println("7. List All Stations");
			System.out.println("8. Exit");
			
			if(in.hasNextInt()) {
				choice = in.nextInt();
				
				switch(choice) {
				case 1:
					// Add Station
					System.out.println("Adding New CTA Station");
					addStops(in, stops, lists);
					break;
				case 2:
					// Modify Station
					System.out.println("Modifying Existing CTA Station");
					modifyStops(in, stops, lists);
					break;
				case 3:
					// Remove Station
					System.out.println("Remove Existing CTA Station");
					System.out.println("Remove a stop, enter 1-" + stops.size());
					
					try {
						int number = in.nextInt();
						stops.remove(number -1);
					} catch (Exception e) {
						System.out.println("Stop does not exist, exiting to main menu...");
					}
					System.out.println("Stop Removed");
					break;
				case 4:
					// Search Station
					System.out.println("Search for a Station");
					se.menu(in, stops, lists);
					break;
				case 5:
					// Find route to station
					System.out.println("Find Route");
					String trip = se.findRoute(lists, in, stops);
					System.out.println(trip);
					
					System.out.println("Would you like to save this route? y/n");
					boolean ans = boolInput(in);
					if (ans == true) {
						fM.saveTrip(in, trip);
					}
					break;
					
				case 6:
					// Search for nearest station
					System.out.println("Enter the Latitude Coordiante (-90 to 90): ");
					double lat = doubleInput(in, 90);
					System.out.println("Enter the Longitude Coordiante (-180 to 180): ");
					double lon = doubleInput(in, 180);
					System.out.println(se.geoFindStop(lat, lon, stops));
					break;
					
				case 7:
					// List stations
					System.out.println("Viewing New CTA Station");
					System.out.println(stops);
					break;
					
				case 8:
					done = true;
					in.close();
					System.out.println("Application has exited successfully!");
					break;
				default:
					System.out.println("Invalid Option, Try Again");
					break;
			}
				
			} else {
				in.nextLine();
				System.out.println("Invalid Input, Try Again");
			}
		} while(!done);
	}
	
	// This method runs the user through the steps so they can add in a stop to the list
	public static ArrayList<Stops> addStops(Scanner in, ArrayList<Stops> stops, ArrayList[] lists) {
		boolean valid = false;
		System.out.println("Adding a stop, please fill out all the information");
		
		System.out.println("Enter the Name: ");
		String name = in.nextLine();
		name = in.nextLine();
		
		System.out.println("Enter the Longitude Coordinate (-180 to 180): ");
		double lon = doubleInput(in, 180);

		
		System.out.println("Enter the Latitude Coordiante (-90 to 90): ");
		double lat = doubleInput(in, 90);
		
		System.out.println("Enter a description: ");
		String desc = in.nextLine();
		desc = in.nextLine();
		
		System.out.println("Does the Station have Wheelchair access? 'y/n'");
		boolean access = boolInput(in);
		
		int[] stopNum = editStops(in, lists);
		
		Stops st = new Stops(name, lon, lat, desc, access, stopNum);
		stops.add(st);
		
		System.out.println("Stop: " + name + " added!");
		
		return stops;
	}
	
	// This method is made as a way to control the double input to make sure that it's valid for all uses
	public static double doubleInput(Scanner in, double maxMin) {
		boolean valid = false;
		double inp = 0.00;
		do {
			if (in.hasNextDouble()) {
				inp = in.nextDouble();
				if (inp >= (-1 * maxMin) && inp <= maxMin) {
					valid = true;
				} else {
					System.out.println("Numbers for the input are restricted between: -" + maxMin + " & " + maxMin);
				}
			} else {
				System.out.println("Invalid entry, try again!");
				in.nextLine();
			}
		} while(!valid);
		return inp;
	}
	
	//This method is made as a function that can be used repetitively to answer true or false questions
	public static boolean boolInput(Scanner in) {
		boolean inp;
		String ans = in.nextLine();
		while (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n")) {
			System.out.println("'y/n' only!");
			ans = in.nextLine();
		}
			
		if (ans.equalsIgnoreCase("y")) {
			System.out.println("Yes");
			inp = true;
		} else {
			inp = false;
		}
		return inp;
	}
	
	// This function makes sure that an input is a valid integer, then it makes sure it it's a valid number for the application needs
	public static int intInput(Scanner in, int min, ArrayList[] lists, int line) {
		boolean validInt = false;
		int inte = 0;
		
		do {
			if (in.hasNextInt()) {
				inte = in.nextInt();
				if (inte >= min) {
					validInt = true;
				} else {
					in.nextLine();
					System.out.println("Input has to be larger than: " + min);
				}
			} else {
				in.nextLine();
				System.out.println("Please enter a valid value for an integer");
			}
		} while(!validInt);
		
		int lineSize = lists[line].size();
		
		if(inte > min) {
			try {
				if (lists[line].get(inte) != "") {
					inte = lineSize++;
					System.out.println("Moving stop to end of line for valid input: " + inte);
				} 
			} catch (Exception e) {
				inte = lineSize++;
				System.out.println("Stop number adjusted to: " + inte);
			}
		}
		
		return inte;
	}
	
	// This function gets all the stop numbers for adding or editing, and makes sure that the numbers fit in the stops, then it returns them
	// as an array for input into the application.
	public static int[] editStops(Scanner in, ArrayList[] lists) {
		System.out.println("Add all the stop data, if the stop doesn't exist on the line enter '-1'");
		System.out.println("Stop numbers may only be added at places where stops don't exist or at the end of a line");
		System.out.println("Red: ");
		int red = intInput(in, -1, lists, 0);
		System.out.println("Green: ");
		int green = intInput(in, -1, lists, 1);
		System.out.println("Blue: ");
		int blue = intInput(in, -1, lists, 2);
		System.out.println("Brown: ");
		int brown = intInput(in, -1, lists, 3);
		System.out.println("Purple: ");
		int purple = intInput(in, -1, lists, 4);
		System.out.println("Pink: ");
		int pink = intInput(in, -1, lists, 5);
		System.out.println("Orange: ");
		int orange = intInput(in, -1, lists, 6);
		
		int numbers[] = {red, green, blue, brown, purple, pink, orange};
		
		return numbers;
	}
	
	// This function is meant to modify station data, then to reenter the data into the arrayList.
	public static ArrayList<Stops> modifyStops(Scanner in, ArrayList<Stops> stops, ArrayList[] lists){
		boolean chosen = false;
		int station = 1;
		do {
			System.out.println("Please select a station you would like to modify, between 1-"+ stops.size());
			if(in.hasNextInt()) {
				station = in.nextInt()-1;
				chosen = true;
			} else {
				System.out.println("Please enter a valid number!");
			}
		} while(!chosen);
		
		if (chosen == true) {
			String name = "Default";
			double lon = 0.00;
			double lat = 0.00;
			String desc = "Basic";
			boolean access = false;
			int[] stopNum = {-1,-1,-1,-1,-1,-1,-1};
			System.out.println("Would you like to change the name? y/n");
			if (boolInput(in) == true) {
				System.out.println("Enter Name: ");
				 name = in.nextLine();
			} else {
				name = stops.get(station).getName();
			}
			System.out.println("Would you like to change the Longitude Coordinate? y/n");
			if (boolInput(in) == true) {
				System.out.println("Enter the Longitude Coordinate (-180 to 180): ");
				lon = doubleInput(in, 180);
			} else {
				lon = stops.get(station).getLon();
			}
			System.out.println("Would you like to change the Latitude Coordinate? y/n");
			if (boolInput(in) == true) {
				System.out.println("Enter the Latitude Coordiante (-90 to 90): ");
				lat = doubleInput(in, 90);
			} else {
				lat = stops.get(station).getLat();
			}
			System.out.println("Would you like to change the Description? y/n");
			if (boolInput(in) == true) {
				System.out.println("Enter Desc: ");
				desc = in.nextLine();
			} else {
				desc = stops.get(station).getDesc();
			}
			System.out.println("Does the station have wheel chair access? y/n");
			access = boolInput(in);
			
			System.out.println("Would you like to change the Stops? y/n");
			if (boolInput(in) == true) {
				stopNum = editStops(in, lists);
			} else {
				stopNum = stops.get(station).getStopNum();
			}
			
			Stops st = new Stops(name, lon, lat, desc, access, stopNum);
			stops.set(station, st);	
		}
		return stops;
	}
}
