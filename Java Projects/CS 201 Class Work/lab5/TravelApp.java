package labs.lab5;

import java.io.File;
import java.util.Scanner;

public class TravelApp {

	public static void main(String[] args) {
		boolean exit = false;
		int choice;
		Scanner in = new Scanner(System.in);
		
		Trip[] trips = readExisitingFile();
		
		System.out.println("Welcome to the Travel App Please select (1-3)");
		
		while(exit == false){
			System.out.println("1. Take a Trip");
			System.out.println("2. Add a Trip");
			System.out.println("3. List Trips");
			System.out.println("4. Exit");
			choice = in.nextInt();
			switch(choice) {
			case 1:
				trips = takeTrips(in, trips);
				break;
			case 2:
				trips = addTrips(in, trips);
				break;
			case 3:
				for (int i = 0; i < trips.length; i++) {
					System.out.println((i + 1) + ". " + trips[i].travel());
				}
				System.out.println(" ");
				break;
			case 4:
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
	
	public static Trip[] readExisitingFile() {
		Trip[] trips = new Trip[0];
		try {
			File file = new File("src/labs/lab5/travel.csv");
			Scanner in = new Scanner(file);
			while(in.hasNextLine() && trips.length < 5) {
				String line = in.nextLine();
				String[] values = line.split(",");
				try {
					if (values[0].equalsIgnoreCase("Flight")) {
						Flight f = new Flight(values[1], values[2], Double.parseDouble(values[3]), Boolean.parseBoolean(values[4]));
						
						Trip[] temp = new Trip[trips.length + 1];
						for (int i = 0; i < trips.length; i++) {
							temp[i] = trips[i];
						}
						trips = temp;
						temp = null;
						
						trips[trips.length - 1] = f;
						
					} else if (values[0].equalsIgnoreCase("Train")) {
						String[] stops = new String[values.length - 4];
						for (int i = 0; i < stops.length; i++) {
							stops[i] = values[i + 4];
						}
						
						Train tr = new Train(values[1], values[2], Double.parseDouble(values[3]), stops);
						
						Trip[] temp = new Trip[trips.length + 1];
						for (int i = 0; i < trips.length; i++) {
							temp[i] = trips[i];
						}
						trips = temp;
						temp = null;
						
						trips[trips.length - 1] = tr;
							
					} else if (values[0].equalsIgnoreCase("Drive")) {
						Drive d = new Drive(values[1], values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]));
						
						Trip[] temp = new Trip[trips.length + 1];
						for (int i = 0; i < trips.length; i++) {
							temp[i] = trips[i];
						}
						trips = temp;
						temp = null;
						
						trips[trips.length - 1] = d;
						
					}
				} catch (Exception e) {
					System.out.print("Unable to read trip");
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading file");
		}
		
		if (trips.length > 5) {
			System.out.println("Too many Trips!");
		}
		
		return trips;
	}
	
	public static Trip[] takeTrips(Scanner in, Trip[] trips) {
		System.out.println("What trip should be taken? Enter 1-" + trips.length);
		int number = in.nextInt();
		
		if (trips.length > 0) {
			while(number > trips.length || number <= 0){
				System.out.println("Not a valid trip try again");
				number = in.nextInt();
			}
			
			int index = -1;
			for(int i = 0; index == -1 && i < trips.length; i++) {
				if (i == number - 1) {
					index = i;
				}
			}
			
			if (index == -1) {
				return trips;
			}
			
			Trip[] temp = new Trip[trips.length - 1];
			for (int i = 0; i < index; i++) {
				temp[i] = trips[i];
			}
			for (int i = index; i < temp.length; i++) {
				temp[i] = trips[i+1];
			}
			trips = temp;
			temp = null;
		} else {
			System.out.println("No trips are on this list");
		}
		
		return trips;
	}
	
	public static Trip[] addTrips(Scanner in, Trip[] trips) {
		if (trips.length < 5) {
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
				
				Trip[] temp = new Trip[trips.length + 1];
				for(int i = 0; i < trips.length; i++) {
					temp[i] = trips[i];
				}
				trips = temp;
				temp = null;
				
				trips[trips.length - 1] = f;
				
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
				
				Trip[] temp = new Trip[trips.length + 1];
				for(int i = 0; i < trips.length; i++) {
					temp[i] = trips[i];
				}
				trips = temp;
				temp = null;
				
				trips[trips.length - 1] = tr;
				
			} else if (type.equalsIgnoreCase("drive")) {
				System.out.println("How many gallons of Gas?:");
				Double gas = in.nextDouble();
				
				Drive d = new Drive(origin, destination, duration, gas);
				
				Trip[] temp = new Trip[trips.length + 1];
				for(int i = 0; i < trips.length; i++) {
					temp[i] = trips[i];
				}
				trips = temp;
				temp = null;
				
				trips[trips.length - 1] = d;
			}
		} else {
			System.out.println("Too many trips!");
		}
		return trips;
	}

}
