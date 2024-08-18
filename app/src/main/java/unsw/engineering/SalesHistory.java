package unsw.engineering;

public class SalesHistory {

	// interaction moreso with sales person
	// maybe you could remove this class altogether and put into sales person?
	// no actually that violates SRP
	private SalesPerson salesPerson;

	public String getSalesSummary() {
		return salesPerson.getFullName() + "Sales Target: " + salesPerson.getSalesTarget()
				+ "$\n" +
				"Sales to date: " + salesPerson.getSalesAchieved() + "$";
	}
}
