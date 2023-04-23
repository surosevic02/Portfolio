package labs.lab5;

import java.util.Arrays;

public class Train extends Trip {
	private String[] stops;
	
	public Train() {
		super();
		stops = new String[] {"A", "B", "C", "D"};
	}
	
	public Train(String origin, String destination, double duration, String[] stops) {
		super(origin, destination, duration);
		setStops(stops);
	}
	
	public String[] getStops() {
		return stops;
	}
	
	public void setStops(String[] stops) {
		this.stops = stops;
	}
	
	public boolean equals(Train tr) {
		if(!super.equals(tr)) {
			return false;
		} else if (!Arrays.equals(stops, tr.getStops())) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return("Stops: " + Arrays.toString(getStops()));
	}
	
	public String travel() {
		return "Train " + super.travel();
	}
}
