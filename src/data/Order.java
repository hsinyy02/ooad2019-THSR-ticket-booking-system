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
			System.out.println("�����N��: " + ticket.getTicketNumber());
			System.out.println("����: " + ticket.getTid());
			System.out.println("���: " + ticket.getDate());
			System.out.println("�_��: " + ticket.getStart() + "(" + ticket.getStime() + ")");
			System.out.println("����: " + ticket.getEnd() + "(" + ticket.getEtime()+ ")");
			System.out.println("�y�츹�X: " + ticket.getSeatNum());
			System.out.println("����: " + ticket.getDiscountType().getName());
			System.out.println("����: " + ticket.getPrice());
			System.out.println();
		}
	}
}
