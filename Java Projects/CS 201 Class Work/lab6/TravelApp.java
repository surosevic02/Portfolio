package labs.lab6;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TravelApp {
	public int tripNum;
	
	public static void main(String[] args) {
		boolean exit = false;
		int choice;
		Scanner in = new Scanner(System.in);
		int view;
		
		ArrayList<Trip> trips = readExisitingFile();
		
		System.out.println("Welcome to the Travel App Please select (1-5)");
		
		while(exit == false){
			System.out.println("1. Add a Trip");
			System.out.println("2. Remove a Trip");
			System.out.println("3. View A Trip");
			System.out.println("4. Take All Trips");
			System.out.println("5. Exit");
			
			choice = in.nextInt();
			switch(choice) {
			case 1:
				addTrips(in, trips);
				break;
			case 2:
				System.out.println("Removing a trip:");
				view = findTrip(in, trips);
				if (view != -1) {
					System.out.println("Removing: " + trips.get(view).travel());
					System.out.println(trips.remove(view));
				} else {
					System.out.println("Nothing to Remove!");
				}
				break;
			case 3:
				view = findTrip(in, trips);
				if (view != -1) {
					System.out.println(trips.get(view).travel());
				} else {
					System.out.println("No trips to list!");
				}
				break;
			case 4:
				trips.clear();
				System.out.println("All Trips have been taken");
				break;
			case 5:
				exit = true;
				break;
			default:
				System.out.println("Not a Valid Option Please try again");
				break;
			}
		}
		System.out.println("Application Exited Successfully");
		in.close();
	}
	
	public static int findTrip(Scanner in, ArrayList<Trip> trips){
		if (trips.size() > 0) {
			int number = 0;
			System.out.println("What Trip would you like to view, 1-" + trips.size());
			number = in.nextInt();
			while (number > trips.size() || number <= 0) {
				System.out.println(number + " is not a valid opiton, try again!");
				number = in.nextInt();
			}
			return number - 1;
		} else {
			return -1;
		}
	}
	
	public static ArrayList<Trip> readExisitingFile() {
		ArrayList<Trip> trips = new ArrayList<Trip>();
		try {
			File file = new File("src/labs/lab5/travel.csv");
			Scanner in = new Scanner(file);
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] values = line.split(",");
				try {
					if (values[0].equalsIgnoreCase("Flight")) {
						Flight f = new Flight(values[1], values[2], Double.parseDouble(values[3]), Boolean.parseBoolean(values[4]));
						
						trips.add(f);
						
					} else if (values[0].equalsIgnoreCase("Train")) {
						String[] stops = new String[values.length - 4];
						for (int i = 0; i < stops.length; i++) {
							stops[i] = values[i + 4];
						}
						
						Train tr = new Train(values[1], values[2], Double.parseDouble(values[3]), stops);
						
						trips.add(tr);
							
					} else if (values[0].equalsIgnoreCase("Drive")) {
						Drive d = new Drive(values[1], values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]));
						
						trips.add(d);
						
					}
				} catch (Exception e) {
					System.out.print("Unable to read trip");
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading file");
		}
		
		return trips;
	}
	
	public static ArrayList<Trip> addTrips(Scanner in, ArrayList<Trip> trips) {
			System.out.println("Add A Trip, Please type in transit method (eg. Flight, Train, Drive)");
			String type = in.nextLine();
			
			while(!type.equalsIgnoreCase("flight") && !type.equalsIgnoreCase("train") && !type.equalsIgnoreCase("drive")) {
				System.out.println("Plese enter (Flight, Train, or Drive)");
				type = in.nextLine();
			}
			
			System.out.println("Origin: ");
			String origin = in.nextLine();
			
			System.out.println("Destination: ");
			String destination = in.nextLine();
			
			System.out.println("Duration, Time it takes in hours (eg: 3.5): ");
			Double duration = in.nextDouble();
			
			if (type.equalsIgnoreCase("flight")) {
				
				boolean food = false;
				
				System.out.println("Inflight Food? y/n");
				String ans = in.nextLine();
				
				while (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n")) {
					System.out.println("Answer y or n please!");
					ans = in.nextLine();
				}
				
				if (ans.equalsIgnoreCase("y")) {
					food = true;
				} else {
					food = false;
				}
				
				Flight f = new Flight(origin, destination, duration, food);
				
				trips.add(f);
				
			} else if (type.equalsIgnoreCase("train")) {
				System.out.println("How many stops between the origin and destination?");
				int stopN = in.nextInt();
				
				String[] stops = new String[stopN];
				
				String city = in.nextLine();
				
				for(int i = 0; i < stops.length; i++) {
					System.out.println("Stop #" + (i+1) + ":");
					city = in.nextLine();
					stops[i] = city;
				}
				
				Train tr = new Train(origin, destination, duration, stops);
				
				trips.add(tr);
				
			} else if (type.equalsIgnoreCase("drive")) {
				System.out.println("How many gallons of Gas?:");
				Double gas = in.nextDouble();
				
				Drive d = new Drive(origin, destination, duration, gas);
				
				trips.add(d);
				
			}
		return trips;
	}

}
