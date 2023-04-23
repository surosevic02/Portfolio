package labs.lab4;

public class GeoLocation {

	private double lat; // Setting the variables that will be manipulated
	private double lng;
	
	public GeoLocation() { // Default Constructor
		lat = 0;
		lng = 0;
	}
	
	public GeoLocation(double lat, double lng) { // Non Default Constructor
		setLat(lat);
		setLng(lng);
	}
	
	public void setLat(double lat) { // Sets the lat if it falls between 90 and -90
		if(lat <= 90 && lat >= -90) {
			this.lat = lat;
		}
	}
	
	public void setLng(double lng) { // Sets the lng if it falls between 180 and -180
		if(lng <= 180 && lng >= -180) {
			this.lng = lng;
		}
	}
	
	public double getLat() { // Returns the value of lat
		return lat;
	}
	
	public double getLng() { // Returns the value of lng
		return lng;
	}
	
	public boolean validLat() { // Checks to see if lat falls between 90 and -90
		if (lat <= 90 && lat >= -90) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean validLng() { // Checks to see if lng falls between 180 and -180
		if (lng <= 180 && lng >= -180) {
			return true;
		}else {
			return false;
		}
	}
	
	public String toString() { // Prints out the lng and lat in the format 00.0, 00.0
		return (lat + ", " + lng);
	}
}
