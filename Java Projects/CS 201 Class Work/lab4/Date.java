package labs.lab4;

public class Date {
	// Setting up the variables in question
	private int day;
	private int month;
	private int year;
	
	public Date() { // Default Constructor
		year = 0;
		month = 1;
		day = 1;
	}
	
	public Date(int year, int month, int day) { // Non Default Constructor
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	
	public int getDay() { // Returns the value of day
		return day;
	}
	
	public int getMonth() { // Returns the value of month
		return month;
	}
	
	public int getYear() { // Returns the value of year
		return year;
	}
	
	public void setYear(int year) { // Sets the year in the date
		this.year = year;
	}
	
	public void setMonth(int month) { // Sets the month if it's between 1 and 12
		if (month >= 1 && month <= 12) {
			this.month = month;
		} else {
			this.month = 1; // Incase everything fails month is set to 1
		}
	}
	
	public void setDay(int day) { // Sets the date based on the month and year
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (day >= 1 && day <= 31) { // Checks if the date is between 1 and 31 for the months above
				this.day = day;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day >= 1 && day <= 31) { // Checks if the date is between 1 and 30 for the months above
				this.day = day;
			}
			
		} else if (month == 2) { // If February this checks if the year is a leap year
			if (year%4 == 0) {	
				if (day >= 1 && day <= 29) { // If it's leap year the day can be set if it's between 1 and 29
					this.day = day;
				}
			} else {
				if (day >= 1 && day <= 28) { // If it's not leap year the day can be set if it's between 1 and 28
					this.day = day;
				}
			}
		} else {
			this.day = 1; // Incase everything fails day is set to 1
		}
	}
	
	public boolean validDay() { // Returns true if the day is between 1 and 31
		if (day >= 1 && day <= 31) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validMonth() { // Returns true if the month is between 1 and 12
		if (month >= 1 && month <= 12) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() { // Returns the variables in a string structured DD/MM/YY
		return (day + "/" + month + "/" + year);
	}
}
