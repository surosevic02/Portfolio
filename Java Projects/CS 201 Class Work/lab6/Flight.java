package labs.lab6;

public class Flight extends Trip {

	private boolean meals;
	
	public Flight() {
		super();
		meals = false;
	}

	public Flight(String origin, String destination, double duration, boolean meals) {
		super(origin, destination, duration);
		setMeals(meals);
	}
	
	public boolean hasMeals() {
		return meals;
	}
	
	public void setMeals(boolean meals) {
		this.meals = meals;
	}
	
	public String toString() {
		return "Meals: " + (meals?"Yes":"No");
	}

	public boolean equals(Flight f) {
		if(!super.equals(f)) {
			return false;
		} else if (meals != f.hasMeals()) {
			return false;
		}
		return true;
	}
	
	public String travel() {
		return "Flight " + super.travel();
	}
}
