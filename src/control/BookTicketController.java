package control;

import data.Order;
import data.Ticket;
import data.Train;
import dbconnector.Query;
import dbconnector.QueryInterface;
import discount.Children;
import discount.Discount;
import discount.EarlyBird;
import discount.Elderly;
import discount.NeedLove;
import service.TrainService;
import service.TrainServiceInterface;

public class BookTicketController {
	
	public QueryInterface query;
	public TrainServiceInterface trainService;
	
	public BookTicketController(QueryInterface query, TrainServiceInterface trainService) {
		this.query = query;
		this.trainService = trainService;
	}
	
	public Order bookTicket(Train train, String uid, String start, String end, int cartType, int seatPrefer, int[] ticketTypes) {
		setupTrainCart(train);
		Order order = new Order(null, uid);
		
		int standardT = ticketTypes[0];
		int childrenT = ticketTypes[1];
		int elderlyT = ticketTypes[2];
		int needLoveT = ticketTypes[3];
		int studentT = ticketTypes[4];
		
		if(standardT != 0)
			makeSingleBooking(train, order, uid, start, end, cartType, seatPrefer, trainService.checkEarlyBird(train, standardT), standardT);
		if(childrenT != 0)
			makeSingleBooking(train, order, uid, start, end, cartType, seatPrefer, new Children(), childrenT);
		if(elderlyT != 0)
			makeSingleBooking(train, order, uid, start, end, cartType, seatPrefer, new Elderly(), elderlyT);
		if(needLoveT != 0)
			makeSingleBooking(train, order, uid, start, end, cartType, seatPrefer, new NeedLove(), needLoveT);
		if(studentT != 0)
			makeSingleBooking(train, order, uid, start, end, cartType, seatPrefer, train.getUniversityDiscount(), studentT);
		
		return order;
	}
	
	private void setupTrainCart(Train train) {
		trainService.initCartList(train);
		String[] unavailableSeatList = query.getUnavailableSeatList(train);  // {"0104E", "0312A", "0601B" , ... }
		if(unavailableSeatList.length != 0) {
			for(String seatNum : unavailableSeatList) {
				trainService.setUnavailableSeat(train, seatNum);
			}
		}
	}
	
	private void makeSingleBooking(Train train, Order order, String uid, String start, String end, int cartType, int seatPrefer, Discount discount, int num){
		
		for(int i=0; i<num; i++) {
			String seatNumber = trainService.bookSeat(train, cartType, seatPrefer);
			Ticket ticket = new Ticket(null, train.getTid(), train.getDate(), start, end, train.getTimetable(start), train.getTimetable(end), cartType, seatNumber, discount);
			query.addTicket(order.getOrderNumber(), uid, ticket);
			order.addTicket(ticket);
		}
		if(discount instanceof EarlyBird) 
			query.updateEarlyBird(train.getTid(), train.getDate() , discount.getDiscount(), num);
		query.updateSeatLeft(train.getTid(), train.getDate(), cartType, num);
	}

	public static void main(String[] args) {

		String[] trainATimetable = {"1400", "1413", "1421", "1454", "1527", "1535", "1550", "1612", "1627", "1643", "1702", "1720"};
		Train trainA = new Train("928", "2018/12/25", 0, 0, 13, 1, trainATimetable);
    	
		String uid = "c37102001";
		String startStation = "台北";
		String endStation = "桃園";
		int cartType = Ticket.CartStandard;
		int seatPrefer = Ticket.SeatAisle;
		int[] ticketTypes = {2, 1, 1, 1, 2};
		
		
		BookTicketController bookingHelper = new BookTicketController(new Query(), new TrainService());
		Order myOrder = bookingHelper.bookTicket(trainA, uid, startStation, endStation, cartType, seatPrefer, ticketTypes);
		
		myOrder.showTicketDetails();
		System.out.println("訂單總額:" + myOrder.getTotalPrice());
		
		TrainService ts = new TrainService();
		System.out.println("剩下標準車廂座位: " + ts.getStdSeatNumber(trainA));
	}
}
