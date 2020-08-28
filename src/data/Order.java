package data;

import java.util.ArrayList;
import java.util.Random;

public class Order {

	private String orderNumber;
	private String uid;
	private ArrayList<Ticket> ticketList;
    
    public Order(String orderNumber, String uid) {
    	this.orderNumber = (orderNumber == null) ? 
    			Integer.toString(Math.abs(new Random().nextInt())) : orderNumber;
    	this.uid = uid;
    	this.ticketList = new ArrayList<Ticket>();
    }
    
	public String getOrderNumber() {
		return orderNumber;
	}
	
    public String getUid() {
		return uid;
	}
	
	public int getTotalPrice() {
    	int totalPrice = 0;
    	for(Ticket ticket : ticketList) 
    		totalPrice += ticket.getPrice();
    	return totalPrice; 
    }
	
	public void addTicket(Ticket ticket) {
        ticketList.add(ticket);
    }
	
	public void deleteTicket(Ticket deleteTicket) {
		ticketList.remove(deleteTicket);
	}
	
	public ArrayList<Ticket> getTicketList(){
		return ticketList;
	}
	
	public void showTicketDetails() {
		for(Ticket ticket : ticketList) {
			System.out.println("車票代號: " + ticket.getTicketNumber());
			System.out.println("車次: " + ticket.getTid());
			System.out.println("日期: " + ticket.getDate());
			System.out.println("起站: " + ticket.getStart() + "(" + ticket.getStime() + ")");
			System.out.println("迄站: " + ticket.getEnd() + "(" + ticket.getEtime()+ ")");
			System.out.println("座位號碼: " + ticket.getSeatNum());
			System.out.println("票種: " + ticket.getDiscountType().getName());
			System.out.println("價格: " + ticket.getPrice());
			System.out.println();
		}
	}
}
