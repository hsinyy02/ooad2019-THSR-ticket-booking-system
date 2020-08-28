package data;

public class Seat {
	
	private int cartNumber;
    private int seatRow;
    private char seatColumn;
    private boolean availableStatus;
    
    
	public Seat(int cartNumber, int seatRow, char seatColumn, boolean availableStatus) {
    	this.cartNumber = cartNumber;
    	this.seatRow = seatRow;
		this.seatColumn = seatColumn;
    	this.availableStatus = availableStatus;
	}
    
    
    public int getSeatType() {
    	if(seatColumn == 'A' || seatColumn == 'E') return Ticket.SeatWindow;
    	else if(seatColumn == 'C' || seatColumn == 'D') return Ticket.SeatAisle;
    	else return 0;
    }
    
    public String getSeatNumber() {
    	return "" + String.format("%02d", cartNumber) + String.format("%02d", seatRow) + seatColumn;
    }
    
    public boolean getAvailableStatus() {
    	return availableStatus;
    }
    
    public void setAvailableStatus(boolean availableStatus) {
    	this.availableStatus = availableStatus;
    }
    
    public static void main(String[] args) {
    	Seat s = new Seat(4, 11, 'B', true);
    	System.out.println(s.getSeatNumber());
    	s.setAvailableStatus(false);
    	System.out.println(s.getAvailableStatus());
    	System.out.println(s.getSeatType());
    }
}
