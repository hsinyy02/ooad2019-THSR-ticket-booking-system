package data;
import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
	
	private static final char[] SeatColumn = {'A', 'B', 'C', 'D', 'E'}; 
    private int cartNumber;
    private ArrayList<Seat> seatList;
	
    
	public Cart(int cartNumber) {
    	this.cartNumber = cartNumber;
    	seatList = new ArrayList<Seat>();
    	initCartSeats();
    }
    
	
    private void initCartSeats() {
    	if(cartNumber == 1)
			for (int row = 1; row <= 13; row++)
				for (int col = 0; col < 5; col++) {
					if(row == 1 && col>=3) continue;
					seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
				}
    	if(cartNumber == 5)
			for (int row = 1; row <= 17; row++)
				for (int col = 0; col < 5; col++) {
					if(row == 1 && col>=3) continue;
					seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
				}
    	if(cartNumber == 3 || cartNumber == 9)
			for (int row = 1; row <= 18; row++)
				for (int col = 0; col < 5; col++) {
					if(row == 1 && col>=3) continue;
					seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
				}
    	
    	if(cartNumber == 2 || cartNumber == 4 || cartNumber == 8) {
    		for (int row = 1; row <= 20; row++)
				for (int col = 0; col < 3; col++)
					seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
    		
    		for (int row = 2; row <= 19; row++)
				for (int col = 3; col < 5; col++)
					seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
    	}
    	
    	if(cartNumber == 6) {
    		for (int row = 1; row <= 17; row++)
    			for (int col = 0; col < 5; col++) {
    				if ((row == 1 && col<3) || (col == 1)) continue;
    				seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
    			}
    	}
    	
    	if(cartNumber == 7) {
    		for (int row = 1; row <= 11; row++) {
    			for (int col = 0; col < 5; col++) {
    				seatList.add(new Seat(cartNumber, row, SeatColumn[col], true));
    			}
    		}
    		seatList.add(new Seat(cartNumber, 12, 'D', true));
    		seatList.add(new Seat(cartNumber, 12, 'E', true));
    	}
    	
    }
    
    
    public int getCartNumber() {
    	return cartNumber;
    }
    

    public void setUnavailableSeat(String seatNum) {
    	for(Seat seat : seatList) {
    		if(seat.getSeatNumber().equals(seatNum)) seat.setAvailableStatus(false); ;
    	}
    }
    
    
    public int getTotalSeatNum() {
    	int totalNum = 0;
    	for(Seat seat : seatList) {
    		if(seat.getAvailableStatus() == true) totalNum++ ;
    	}
    	return totalNum;
    }
    
    
    public int getWindowSeatNum() {
    	int windowNum = 0;
    	for(Seat seat : seatList) {
    		if(seat.getSeatType() == Ticket.SeatWindow && seat.getAvailableStatus() == true) windowNum++ ;
    	}
    	return windowNum;
    }
    
    
    public int getAisleSeatNum() {
    	int aisleNum = 0;
    	for(Seat seat : seatList) {
    		if(seat.getSeatType() == Ticket.SeatAisle && seat.getAvailableStatus() == true) aisleNum++ ;
    	}
    	return aisleNum;
    }
    
    //book seat without preference
    public String bookSeat() {
    	for(Seat seat : seatList) {
    		if(seat.getAvailableStatus() == true) {
    			seat.setAvailableStatus(false);
    			return  seat.getSeatNumber();
    		}
    	}
    	return "";
    }
    
  //book seat with preference
    public String bookSeat(int seatPrefer) {
    	for(Seat seat : seatList) {
    		if(seat.getSeatType() == seatPrefer && seat.getAvailableStatus() == true) {
    			seat.setAvailableStatus(false);
    			return seat.getSeatNumber();
    		}
    	}
    	return "";
    }
    
    
    public static void main(String[] args) {
    	for(int i=1; i<=9; i++) {
    		Cart cart = new Cart(i);
    		System.out.println("Cart Number: " + cart.getCartNumber());
	    	System.out.println("Total left: " + cart.getTotalSeatNum());
	    	System.out.println("Window left: " + cart.getWindowSeatNum());
	    	System.out.println("Aisle left: " + cart.getAisleSeatNum());
	    	System.out.println("");
    	}
//    	System.out.println("Book seat " + cart.bookSeat(Ticket.SeatWindow));
//    	System.out.println("Book seat " + cart.bookSeat(Ticket.SeatWindow));
//    	System.out.println("Book seat " + cart.bookSeat(Ticket.SeatAisle));
//    	System.out.println("Book seat " + cart.bookSeat());
//    	System.out.println("Book seat " + cart.bookSeat());
//    	System.out.println("Book seat " + cart.bookSeat());
    	
//    	System.out.println("Total left: " + cart.getTotalSeatNum());
//    	System.out.println("Window left: " + cart.getWindowSeatNum());
//    	System.out.println("Aisle left: " + cart.getAisleSeatNum());
    }
}
