package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
// The role of this document is to handle the saving and reading of data in order to get the full functionality of the program
// This program will read the data from the CSV and put it into an array, Similarly the save function will take a rout string
// and save it to a .txt file so a user can access it.
// Stefan Urosevic, 10/29/2020
	
	// This method reads in a CSV file, splits it up, then passes the data into an Array
	public ArrayList<Stops> readStationData() {
		ArrayList<Stops> stops = new ArrayList<Stops>();
		boolean startAdd = false;
		try {
			File data = new File("src/project/CTAStops.csv");
			Scanner read = new Scanner(data);
			while(read.hasNextLine()) {
				String line = read.nextLine();
				String[] values = line.split(",");
				try {
					if (startAdd == true) {
						int[] stopNum = new int[values.length - 5];
						for (int i = 0; i < stopNum.length; i++) {
							stopNum[i] = Integer.parseInt(values[i+5]);
						}
						
						Stops st = new Stops(values[0], Double.parseDouble(values[1]), Double.parseDouble(values[2]), values[3], Boolean.parseBoolean(values[4]), stopNum);
						
						stops.add(st);
					}
					startAdd = true;
					
				} catch(Exception e) {
					System.out.println("Unable to read data!");
					System.out.println("Error: " + e);
				}
				
			}
			read.close();
		} catch(Exception e) {
			System.out.println("Unable to locate file!");
			System.out.println("Error: " + e);
		}
		
		 return stops;
	}
	
	// This method takes the route string and saves it to a text file a user can read.
	public void saveTrip(Scanner in, String Path) throws IOException {
		String fName;
		
		System.out.println("Input file name to save this trip:");
		fName = in.nextLine();
		
		File save = new File("src/project/" + fName + ".txt");
		FileWriter writer = new FileWriter(save);
		
		writer.write(Path);
		writer.flush();
		writer.close();
		
		System.out.println("Route saved to: src/project/" + fName + ".txt");
	}
}
