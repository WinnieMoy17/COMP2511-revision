package unsw.engineering;

public class SalesPerson extends Employee {

	private float commission;
	private double salesTarget;
	private double salesAchieved;

	public SalesPerson(String title, String firstName, String lastName, int quota) {
		super(title, firstName, lastName, quota);
		this.salesTarget = quota;
	}

	// sales target is needed in sales history for salesperson
	public double getSalesTarget() {
		return salesTarget;
	}

	// removing refused bequest
	// moved from employee as engineer does not require
	public double getSalesAchieved() {
		return salesAchieved;
	}

	@Override
	public double calculateSalary() {
		double totalSal;
		totalSal = super.getBaseSalary() + commission * getSalesAchieved()
				+ super.calculateParkingFringeBenefits()
				- super.calculateTax();
		return totalSal;
	}

}
