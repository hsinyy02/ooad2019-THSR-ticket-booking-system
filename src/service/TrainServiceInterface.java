package service;

import data.Train;
import discount.Discount;

public interface TrainServiceInterface {
	
	public void initCartList(Train train);
	
	public Discount checkEarlyBird(Train train, int number);
	
	public int getStdSeatNumber(Train train);
	
	public int getBusSeatNumber(Train train);
	
	public void setUnavailableSeat(Train train, String seatNum);
	
	public String bookSeat(Train train, int cartType, int seatPrefer);
	
}
