package labs.lab6;

public class Drive extends Trip {
	double gallonsOfGas;
	
	public Drive() {
		super();
		gallonsOfGas = 3;
	}
	
	public Drive(String origin, String destination, double duration, double gallonsOfGas) {
		super(origin, destination, duration);
		setGallonsOfGas(gallonsOfGas);
	}
	
	public double getGallonsOfGas() {
		return gallonsOfGas;
	}
	
	public void setGallonsOfGas(double gallonsOfGas) {
		if (gallonsOfGas > 0) {
			this.gallonsOfGas = gallonsOfGas;
		}
	}
	
	public boolean equals(Drive d) {
		if(!super.equals(d)) {
			return false;
		} else if (gallonsOfGas != d.getGallonsOfGas()) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return("Gallons of gas needed for this trip: " + getGallonsOfGas());
	}
	
	public String travel() {
		return "Drive " + super.travel();
	}
}
