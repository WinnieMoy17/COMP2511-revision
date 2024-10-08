package unsw.engineering;

public abstract class Employee {
	private String title;
	private String firstName;
	private String lastName;
	private double baseSalary;

	public Employee(String title, String firstName, String lastName, int quota) {
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public double calculateTax() {
		double tax = 0;
		double salary = baseSalary;
		if (salary > 50000) {
			tax += 0.3 * (salary - 50000);
		}
		if (salary > 30000) {
			tax += 0.2 * (salary - 30000);
		}
		return tax;
	}

	public double calculateParkingFringeBenefits() {
		return (baseSalary - 50000) / 10000;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public abstract double calculateSalary();

}
