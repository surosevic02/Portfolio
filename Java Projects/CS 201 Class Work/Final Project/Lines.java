package project;

import java.util.ArrayList;

/* The point of this class is to take the list of imported stops and sort them into the lines that they service, some stops
 * may be duplicated as they server multiple lines. This class will assist in the path finding algorithm as well as assist
 * in the modifications and additions of other stops
 * Stefan Urosevic, 11/20/2020
 */

public class Lines {
	// Once called this method adds the stops to a line array list, then compiling the lists into an array so they can be accessed.
	// It also initializes the arrays so that this function can be carried out.
	public ArrayList[] storeStops(ArrayList<Stops> stops) {
		int[] initialize = {33, 28, 33, 27, 27, 22, 16};
		
		FileManager fm = new FileManager();
		
		ArrayList[] lists = new ArrayList[7];
		
		ArrayList<String> redLine = new ArrayList<String>();
		ArrayList<String> greenLine = new ArrayList<String>();
		ArrayList<String> blueLine = new ArrayList<String>();
		ArrayList<String> brownLine = new ArrayList<String>();
		ArrayList<String> purpleLine = new ArrayList<String>();
		ArrayList<String> pinkLine = new ArrayList<String>();
		ArrayList<String> orangeLine = new ArrayList<String>();
		
		for (int k = 0; k < initialize.length; k++) {
			for (int s = 0; s < initialize[k]; s++) {
				switch(k) {
				case 0:
					redLine.add("");
					break;
				case 1:
					greenLine.add("");
					break;
				case 2:
					blueLine.add("");
					break;
				case 3:
					brownLine.add("");
					break;
				case 4:
					purpleLine.add("");
					break;
				case 5:
					pinkLine.add("");
					break;
				case 6:
					orangeLine.add("");
					break;
				}
			}
		}
		
		for (int i = 0; i < stops.size(); i++) {
			int temp[] = stops.get(i).getStopNum();
			String name = stops.get(i).getName();
			for (int j = 0; j < temp.length; j++) {
				if (temp[j] != -1) {
					switch(j) {
					case 0:
						try {
							redLine.set(temp[0], name);
						} catch(Exception e) {
							redLine.add(name);
						}
						break;
					case 1:
						try {
							greenLine.set(temp[1], name);
						} catch(Exception e) {
							greenLine.add(name);
						}
						break;
					case 2:
						try {
							blueLine.set(temp[2], name);
						} catch(Exception e) {
							blueLine.add(name);
						}
						break;
					case 3:
						try {
							brownLine.set(temp[3], name);
						} catch(Exception e) {
							brownLine.add(name);
						}
						break;
					case 4:
						try {
							purpleLine.set(temp[4], name);
						} catch(Exception e) {
							purpleLine.add(name);
						}
						break;
					case 5:
						try {
							pinkLine.set(temp[5], name);
						} catch(Exception e) {
							pinkLine.add(name);
						}
						break;
					case 6:
						try {
							orangeLine.set(temp[6], name);
						} catch(Exception e) {
							pinkLine.add(name);
						}
						break;
					}
				}
			}
			lists[0] = redLine;
			lists[1] = greenLine;
			lists[2] = blueLine;
			lists[3] = brownLine;
			lists[4] = purpleLine;
			lists[5] = pinkLine;
			lists[6] = orangeLine;
		}
		
		return lists;
	}
}