package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* The point of this class is to store all the searching functions as well as the path finding function for the user
 * This methods in this class also allow for users to find the nearest stop as well as providing useful information.
 * Stefan Urosevic 11/29/2020
 */
public class SearchEngine {
	
	// This is the menu for the search engine, it can do some basic searching in order to help the user find what they're looking for
	public void menu(Scanner in, ArrayList<Stops> stops, ArrayList[] lists) {
		boolean done = false;
		int choice;
		Application app = new Application();
		boolean found = false;
		
		System.out.println("Welcome to the search engine, here are your options for searching below.");
		
		do {
			
			System.out.println("____________________________________");
			System.out.println("1. Search by name");
			System.out.println("2. Search by access");
			System.out.println("3. Search by description");
			System.out.println("4. Search by access and description");
			System.out.println("5. Search by line");
			System.out.println("6. Exit to main menu");
			
			if(in.hasNextInt()) {
				choice = in.nextInt();
				
				switch(choice) {
				case 1:
					System.out.println("Enter the name of your station exactly as possible");
					String name;
					name = in.nextLine();
					name = in.nextLine();
					
					System.out.println(searchStation(name, stops));
					
					break;
				case 2:
					System.out.println("Do you want a list of stations with Wheelchair access or no?");
					boolean wheel = app.boolInput(in);
					
					found = false;
					for (int i = 0; i < stops.size(); i++) {
						if (stops.get(i).getAccess() == wheel) {
							found = true;
							System.out.print(stops.get(i));
						}
					}
					
					if (found == false) {
						System.out.println("No Results Found!");
					}
					
					break;
				case 3:
					System.out.println("Please enter a description of some stops.");
					String desc;
					desc = in.nextLine();
					desc = in.nextLine();
					
					found = false;
					for (int i = 0; i < stops.size(); i++) {
						String stopDesc = stops.get(i).getDesc();
						if (stopDesc.compareToIgnoreCase(desc) == 0) {
							found = true;
							System.out.print("Stop Found, Details: " + stops.get(i));
						}
					}
					
					if (found == false) {
						System.out.println("No Results Found!");
					}
					
					break;
				case 4:
					System.out.println("Does the station have Wheelchair access?");
					wheel = app.boolInput(in);
					
					ArrayList<Stops> filter = new ArrayList<Stops>();
					
					found = false;
					for (int i = 0; i < stops.size(); i++) {
						if (stops.get(i).getAccess() == wheel) {
							found = true;
							filter.add(stops.get(i));
						}
					}
					
					if (found == false) {
						System.out.println("No Results Found!");
					}
					
					found = false;
					System.out.println("Please enter description:");
					desc = in.nextLine();
					
					for (int j = 0; j < filter.size(); j++) {
						String stopDesc = filter.get(j).getDesc();
						if (stopDesc.compareToIgnoreCase(desc) == 0) {
							found = true;
							System.out.print("Stop Found, Details: " + filter.get(j));
						}
					}
					
					if (found == false) {
						System.out.println("No Results Found!");
					}
					break;
				case 5:
					System.out.println("Please enter which you would like to search:");
					String line = in.nextLine();
					
					do {
						System.out.println("Please enter a valid line color");
						line = in.nextLine();
					} while(!(line.equalsIgnoreCase("red") == true || line.equalsIgnoreCase("orange") == true 
							|| line.equalsIgnoreCase("pink") == true || line.equalsIgnoreCase("purple") == true || line.equalsIgnoreCase("green") == true
							|| line.equalsIgnoreCase("brown") == true || line.equalsIgnoreCase("blue") == true));
					
					System.out.println("Viewing stations on the " + line + " line");
					
					switch(line.toLowerCase()) {
					case "red":
						System.out.println(lists[0]);
						break;
					case "green":
						System.out.println(lists[1]);
						break;
					case "purple":
						System.out.println(lists[4]);
						break;
					case "pink":
						System.out.println(lists[5]);
						break;
					case "orange":
						System.out.println(lists[6]);
						break;
					case "brown":
						System.out.println(lists[3]);
						break;
					case "blue":
						System.out.println(lists[2]);
						break;
					}
					
					break;
				case 6:
					System.out.println("Exiting Search Engine");
					done = true;
					break;
				default:
					System.out.println("Invalid Option, Try Again!");
					break;
				}
			} else {
				in.nextLine();
				System.out.println("Invalid Input, Try Again!");
			}
			
		} while(!done);
	}
	
	// This method takes in the longitude and latitude coordinates, then uses the distance formula to calculate the distance
	// between the given coordinates and each point in order to find the nearest station to the user.
	public String geoFindStop(double lonIn, double latIn, ArrayList<Stops> stops) {
		String stationName = "";
		double currentDistance = 1000;
		double newDistance;
		double x = 0;
		double y;
		int originLine = 0;
		int destinationLine = 0;
		
		for(int i = 0; i < stops.size(); i++) {
			x = Math.pow((stops.get(i).getLat() - latIn), 2);
			y = Math.pow((stops.get(i).getLon() - lonIn), 2);
			newDistance = Math.abs(Math.sqrt(x + y));
			
			if (currentDistance > newDistance) {
				currentDistance = newDistance;
				stationName = stops.get(i).getName();
			}	
		}
		
		return "The nearest station to you is, " + stationName;
	}
	
	// This function is meant to search for the name of a station so the user can path find or find a station.
	public String searchStation(String name, ArrayList<Stops> stops) {
		boolean found = false;
		String result = "";
		for (int i = 0; i < stops.size(); i++) {
			String stopName = stops.get(i).getName();
			if (stopName.compareToIgnoreCase(name) == 0) {
				found = true;
				System.out.println(stops.get(i));
				result = "Stop(s) Found =============";
			}
		}
		
		if (found == false) {
			result = "No Results Found!";
		}
		return result;
	}
	
	// This function finds the path from the origin to the users destination so they can figure out where to go
	public String findRoute(ArrayList[] lists, Scanner in, ArrayList<Stops> stops) {
		String origin = "";
		String destination = "";
		String input = "";
		boolean result = false;
		boolean searchLine = false;
		int originLine = 0;
		int destinationLine = 0;
		int lineNumber = 0;
		String middleString = "ERROR";
		String dLineName = "";
		String oLineName = "";
		
		System.out.println("Input Origin Details");
		
		for (int c = 0; c < 2; c++) {
			String lineColor;
			do {
				System.out.println("Please enter line color:");
				lineColor = in.nextLine();
				
				switch(lineColor.toLowerCase()) {
				case "red":
					lineNumber = 0;
					searchLine = true;
					break;
				case "green":
					lineNumber = 1;
					searchLine = true;
					break;
				case "purple":
					lineNumber = 4;
					searchLine = true;
					break;
				case "pink":
					lineNumber = 5;
					searchLine = true;
					break;
				case "orange":
					lineNumber = 6;
					searchLine = true;
					break;
				case "brown":
					lineNumber = 3;
					searchLine = true;
					break;
				case "blue":
					lineNumber = 2;
					searchLine = true;
					break;
				default:
					System.out.println("Not a valid line color try again.");
					break;
				}
			}while(!searchLine);
			
			String[] temp = getStringArray(lists[lineNumber]);
			result = false;
			
			do {
				System.out.println("Search for Station");
				input = in.nextLine();
				for (int i = 0; i < temp.length; i++) {
					if (temp[i].compareToIgnoreCase(input) == 0) {
						result = true;
					}
				}
				if (result != true){
					System.out.println("No station found serach again!");
				}
			}while(!result);
			
			if (c == 0) {
				oLineName = lineColor;
				originLine = lineNumber;
				origin = input;
				System.out.println("Input Destination Details");
			}
			if (c == 1) {
				dLineName = lineColor;
				destinationLine = lineNumber;
				destination = input;
			}
		}
		
		String boardingString = "1. Board at the origin: " + origin;
		String unloadingString = "3. You have arrived at: " + destination;
		
		if (originLine == destinationLine) {
			middleString = "2. Ride on the same train until you reach your destination";
		} else {
			String transfer = "";
			
			String[] Temp1 = getStringArray(lists[originLine]);
			String[] Temp2 = getStringArray(lists[destinationLine]);
			
			for (int i = 0; i < Temp1.length; i++) {
				for (int j = 0; j < Temp2.length; j++) {
					if(Temp1[i].equalsIgnoreCase(Temp2[j])) {
						transfer = Temp2[j];
					}
				}
			}
			
			middleString = "2. Ride until you arrive at " + transfer + " station, get off from the " + oLineName + " train then board the " + dLineName + " train and ride it to your destination";
		}
		
		return boardingString + "\n" + middleString + "\n" + unloadingString;
	}
	
	// This function is created to convert the line arraylist to strings so the names can be used elsewhere in
	// the program
	public static String[] getStringArray(ArrayList<String> arr) {
		String[] str = new String[arr.size()];
		for (int j = 0; j < arr.size(); j++) {
			str[j] = arr.get(j);
		}
		
		return str;
	}
}
