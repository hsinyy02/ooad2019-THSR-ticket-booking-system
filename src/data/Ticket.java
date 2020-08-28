package data;

import java.util.Random;

import discount.*;

public class Ticket {
	
	public static final int CartStandard = 1;
	public static final int CartBusiness = 4;
	public static final int SeatWindow = 1;
	public static final int SeatAisle = 2;
	public static final int SeatNoPrefer = 3;
	
	private String ticketNumber;
	private String date;
	private String tid;
	private String startStation;
	private String endStation;
	private String startTime;
	private String endTime;
	private String seatNum;
	private int cartType;
	private Discount discount;
	private int price;
	
	public Ticket(String ticketNumber, String tid, String date, String start, String end, String stime, String etime, int cartType, String seatNum, Discount discount) {
		this.ticketNumber = ticketNumber == null ? Integer.toString(Math.abs(new Random().nextInt())) : ticketNumber;
		this.date = date;
		this.tid = tid;
		this.startStation = start;
		this.endStation = end;
		this.startTime = stime;
		this.endTime = etime;
		this.seatNum = seatNum;
		this.cartType = cartType;
		this.discount = discount;
		this.price = (int) (Price.getPrice(startStation, endStation, cartType, discount));
	}
	

	public String getTicketNumber() {
		return ticketNumber;
	}

	public String getDate() {
		return date;
	}

	public String getTid() {
		return tid;
	}

	public String getStart() {
		return startStation;
	}
	public String getEnd() {
		return endStation;
	}

	public String getStime() {
		return startTime;
	}

	public String getEtime() {
		return endTime;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public int getCartType() {
		return cartType;
	}

	public int getPrice() {
		return price;
	}
	
	public Discount getDiscountType() {
		return discount;
	}
}
