package labs.lab6;

public abstract class Trip {
	
	private String origin;
	private String destination;
	private double duration;
	
	public Trip() {
		origin = "A";
		destination = "B";
		duration = 3;
	}
	
	public Trip(String origin, String destination, double duration) {
		setOrigin(origin);
		setDestination(destination);
		setDuration(duration);
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setDuration(double duration) {
		if (duration > 0) {
			this.duration = duration;
		}
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public double getDuration() {
		return duration;
	}
	
	public boolean equals(Trip t) {
		if(origin.equals(t.getOrigin())) {
			return false;
		} else if (destination.equals(t.getDestination())) {
			return false;
		} else if (duration != t.getDuration()) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return("Origin: " + getOrigin() + ", Destination: " + getDestination() + ", Duration: " + getDuration() + " hours");
	}
	
	public String travel() {
		return("from: " + getOrigin() + " to: " + getDestination() + " - " + getDuration() + " hours");
	}
}
