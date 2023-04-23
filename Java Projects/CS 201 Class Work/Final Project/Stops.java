package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Stops {
	
	/* This class is created so that when the stops data from the csv is imported, the data can be sorted to create an object in which the other
	 * classes are able to interact with in order to allow the use to complete the task that they need to do. Here is where the main constructor stops
	 * created, and where the stops variables are set.
	 */ 
	// Stefan Urosevic, 11/11/2020
	
	private String Name;
	private double Lon;
	private double Lat;
	private String Desc;
	private boolean Access;
	private int[] StopNum;
	
	
	// The default constructor for setting the variables for stops
	public Stops() {
		Name = "Default";
		Lon = 0;
		Lat = 0;
		Desc = "Basic";
		Access = false;
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
		
	}
	
	// Constructor for stops when given a name and nothing else
	public Stops(String Name) {
		setName(Name);
		Lon = 0;
		Lat = 0;
		Desc = "Basic";
		Access = false;
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
	}
	
	// Constructor for stops when given a name, longitude, and nothing else
	public Stops(String Name, double Lon) {
		setName(Name);
		setLon(Lon);
		Lat = 0;
		Desc = "Basic";
		Access = false;
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
	}
	
	// Constructor for stops when given a name, longitude, latitude, and nothing else
	public Stops(String Name, double Lon, double Lat) {
		setName(Name);
		setLon(Lon);
		setLat(Lat);
		Desc = "Basic";
		Access = false;
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
	}
	
	// Constructor for stops when given a name, longitude, latitude, description, and nothing else
	public Stops(String Name, double Lon, double Lat, String Desc) {
		setName(Name);
		setLon(Lon);
		setLat(Lat);
		setDesc(Desc);
		Access = false;
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
	}
	
	// Constructor for stops when given a name, longitude, latitude, description, access, and nothing else
	public Stops(String Name, double Lon, double Lat, String Desc, boolean Access) {
		setName(Name);
		setLon(Lon);
		setLat(Lat);
		setDesc(Desc);
		setAccess(Access);
		StopNum = new int[] {-1, -1, -1, -1, -1, -1, -1};
	}
		
	// Passes the arguments for the constructor stop for the program
	public Stops(String Name, double Lon, double Lat, String Desc, boolean Access, int StopNum[]) {
		setName(Name);
		setLon(Lon);
		setLat(Lat);
		setDesc(Desc);
		setAccess(Access);
		setStopNum(StopNum);
	}
	
	// gets the name of the stop
	public String getName() {
		return Name;
	}
	
	// Sets the name of a stop
	public void setName(String name) {
		this.Name = name;
	}
	
	// Gets the longitude of the specific stop
	public double getLon() {
		return Lon;
	}
	
	// Sets the longitude of the specific stop
	public void setLon(double lon) {
		if (lon >= -180 && lon <= 180) {
			this.Lon = lon;
		} else {
			this.Lon = 0;
		}
	}

	// Gets the latitude for the specific stop
	public double getLat() {
		return Lat;
	}
	
	// Sets the latitude for the specific stop
	public void setLat(double lat) {
		if (lat >= -90 && lat <= 90) {
			this.Lat = lat;
		} else {
			this.Lat = 0;
		}
	}
	
	// Gets the Description of the specific stop
	public String getDesc() {
		return Desc;
	}
	
	// Sets the Description of the specific stop
	public void setDesc(String desc) {
		this.Desc = desc;
	}

	// Gets Access on specific stops
	public boolean getAccess() {
		return Access;
	}
	
	// Sets access on the specific stops
	public void setAccess(boolean access) {
		this.Access = access;
	}
	
	// Gets the stop numbers on the various lines
	public int[] getStopNum() {
		return StopNum;
	}
	
	// Sets the stops position on the various lines
	public void setStopNum(int[] StopNum) {
		this.StopNum = StopNum;
	}
	
	// Checks to see if the stop in question is the same as another stop
	public boolean equals(Stops st) {
		if (!(Name.equals(st.getName()))) {
			return false;
		} else if (Lon != st.getLon()) {
			return false;
		} else if (Lat != st.getLat()) {
			return false;
		} else if (!(Desc.equals(st.getDesc()))) {
			return false;
		} else if (Access != st.getAccess()) {
			return false;
		} else if (!Arrays.equals(StopNum, st.getStopNum())) {
			
		}
		
		return true;
	}
	
	// Returns all the data pertaining to the stop in question
	public String toString() {
		return "\n" + "Stop: " + getName() + " Longitude: " + getLon() + ", Latitude: " + getLat() + ", Description: " + getDesc() + ", Access: " + getAccess() + ", Pos on Lines: " + Arrays.toString(getStopNum());
	}
	
}
