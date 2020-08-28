package discount;

public abstract class DiscountTicket implements Discount{
	protected String name;
	protected double discount;
	
	public String getName() {
		return this.name;
	}

	public double getDiscount() {
		return this.discount;
	}
}
