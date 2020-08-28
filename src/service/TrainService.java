package service;

import data.Cart;
import data.Ticket;
import data.Train;
import discount.*;

public class TrainService implements TrainServiceInterface{
	
	public void initCartList(Train train) {
		for(int cartNum=1; cartNum<=Train.TotalCartNum; cartNum++) {
			train.getCartList().add(new Cart(cartNum));
		}
	}
	
	public Discount checkEarlyBird(Train train, int number) {
		if(train.getEarlyBird65() >= number) return new EarlyBird65();
		else if(train.getEarlyBird80() >= number) return new EarlyBird80();
		else if(train.getEarlyBird90() >= number) return new EarlyBird90();
		else return new Standard();
	}
	
	public int getStdSeatNumber(Train train) {
		int seatNumber = 0;
		for(Cart cart : train.getCartList()) {
			if(cart.getCartNumber() == Train.BusCartNum) 
				continue;
			seatNumber += cart.getTotalSeatNum();
		}
		return seatNumber;
	}
	
	
	public int getBusSeatNumber(Train train) {
		int seatNumber = 0;
		for(Cart cart : train.getCartList()) {
			if(cart.getCartNumber() == Train.BusCartNum) 
				seatNumber = cart.getTotalSeatNum();
		}
		return seatNumber;
	}
	
	
	public void setUnavailableSeat(Train train, String seatNum) {
		for(Cart cart : train.getCartList()) {
			if(cart.getCartNumber() != Integer.parseInt(seatNum.substring(0, 2))) 
				continue;
			cart.setUnavailableSeat(seatNum);
		}
	}


	public String bookSeat(Train train, int cartType, int seatPrefer) {
		for(Cart cart : train.getCartList()) {
			if(cartType == Ticket.CartBusiness && cart.getCartNumber() != Train.BusCartNum)
				continue;
			if(seatPrefer == Ticket.SeatWindow && cart.getWindowSeatNum()>0) 
				return cart.bookSeat(seatPrefer);
			else if(seatPrefer == Ticket.SeatAisle && cart.getAisleSeatNum()>0) 
				return cart.bookSeat(seatPrefer);
			else if(cart.getTotalSeatNum()>0)
				return cart.bookSeat();
			else continue;
		}
		return "";
	}

	public static void main(String[] args) {
		String[] trainATimetable = {"0800", "0810", "0815", "0835", "0855", "-1", "0940", "-1", "-1", "1030", "1035", "1050"};
    	Train train = new Train("1072", "2018/12/25", 5, 10, 15, 0.85, trainATimetable);
    	TrainService trainService = new TrainService();
    	System.out.println("Train ID: " + train.getTid());
    	System.out.println("Date: " + train.getDate());
    	
    	trainService.initCartList(train);
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	trainService.setUnavailableSeat(train, "0102A");
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	trainService.setUnavailableSeat(train, "0101C");
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	trainService.setUnavailableSeat(train, "0101D");
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	System.out.println(trainService.bookSeat(train, Ticket.CartStandard, Ticket.SeatWindow));
    	System.out.println(trainService.bookSeat(train, Ticket.CartStandard, Ticket.SeatAisle));
    	System.out.println(trainService.bookSeat(train, Ticket.CartBusiness, Ticket.SeatWindow));
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	for(int i=0; i<10; i++) {
    		System.out.println(trainService.bookSeat(train, Ticket.CartStandard, Ticket.SeatNoPrefer));
    	}
    	for(int i=0; i<5; i++) {
    		System.out.println(trainService.bookSeat(train, Ticket.CartBusiness, Ticket.SeatNoPrefer));
    	}
    	System.out.println("Std seat number: " + trainService.getStdSeatNumber(train));
    	System.out.println("Bus seat number: " + trainService.getBusSeatNumber(train));
    	
    	System.out.println(train.getTimetable("оч╢щ"));
    }
	
}
