package discount;

public class EarlyBird65 extends EarlyBird{
	
	public EarlyBird65() {
		this.name = "¦­³¾65§é";
		this.discount = 0.65;
	}

	public static void main(String[] args) {
		EarlyBird65 eb65 = new EarlyBird65();
		System.out.println(eb65.getName());
		System.out.println(eb65.getDiscount());
	}

}
