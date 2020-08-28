package data;

import java.util.ArrayList;
import java.util.HashMap;

import discount.Discount;
import discount.Standard;
import discount.Student50;
import discount.Student70;
import discount.Student85;

public class Train{
	

	private String tid; 
	private String date;
	private int earlyBird65;
	private int earlyBird80;
	private int earlyBird90;
	private Discount universityDiscount;
	private ArrayList<Cart> cartList;
	private HashMap<String, String> timetable;

	public static final int BusCartNum = 6;
	public static final int TotalCartNum = 9;
	
	
	public Train(String tid, String date, int earlyBird65, int earlyBird80, int earlyBird90, double universityDiscount, String[] time){
		this.tid = tid;
		this.date = date;
		this.earlyBird65 = earlyBird65;
		this.earlyBird80 = earlyBird80;
		this.earlyBird90 = earlyBird90;
		
		if(universityDiscount == 0.5)
			this.universityDiscount = new Student50();
		else if(universityDiscount == 0.7)
			this.universityDiscount = new Student70();
		else if(universityDiscount == 0.85)
			this.universityDiscount = new Student85();
		else if(universityDiscount == 1)
			this.universityDiscount = new Standard();
		else {
			this.universityDiscount = new Standard();
		}
		
		cartList = new ArrayList<Cart>();
		timetable = new HashMap<String, String>();
		for(int i=0; i<Station.CHI_NAME.length; i++) {
			timetable.put(Station.CHI_NAME[i], time[i]);
		}
	} 

	public String getTid() {
		return tid;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getEarlyBird65() {
		return earlyBird65;
	}
	
	public int getEarlyBird80() {
		return earlyBird80;
	}
	
	public int getEarlyBird90() {
		return earlyBird90;
	}

	public Discount getUniversityDiscount() {
		return universityDiscount;
	}

	public ArrayList<Cart> getCartList() {
		return cartList;
	}
	
	public void setEarlyBird90(int newEarlyBird90) {
		this.earlyBird90 = newEarlyBird90;
	}
	
	public void setEarlyBird65(int newEarlyBird65) {
		this.earlyBird65 = newEarlyBird65;
	}
	
	public void setEarlyBird80(int newEarlyBird80) {
		this.earlyBird80 = newEarlyBird80;
	}
	
	public String getTimetable(String station) {
		return timetable.get(station);
	}
}