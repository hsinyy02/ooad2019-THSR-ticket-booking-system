package control;

import data.Order;
import data.Ticket;
import data.Train;
import dbconnector.Query;
import service.TrainService;

public class ControlManager {
	
	private static Query query = new Query();
	private static TrainService trainService = new TrainService();
	private static SearchTrainController stc = new SearchTrainController(query, trainService);
	private static BookTicketController btc = new BookTicketController(query, trainService);
	private static CheckOrderController coc = new CheckOrderController(query);
	private static CheckTimetableController ctc = new CheckTimetableController(query);
	
	/*
	 * SearchTrainController
	 */
	public static Train[] searchTrain(String date, String startStation, String endStation, String startTime, int cartType, int[] ticketTypes) {
		return stc.searchTrain(date, startStation, endStation, startTime, cartType, ticketTypes);
	}
	
	public static int getTotalPrice(Train train, String startStation, String endStation, int cartType, int[] ticketTypes) {
		return stc.getTotalPrice(train, startStation, endStation, cartType, ticketTypes);
	}
	
	public static String getTotalTime(Train train, String startStation, String endStation) {
		return stc.getTotalTime(train, startStation, endStation);
	}
	
	public static String checkEarlyBird(Train train, int standardT) {
		return stc.checkEarlyBird(train, standardT);
	}
	
	public static String checkStudent(Train train, int studentT) {
		return stc.checkStudent(train, studentT);
	}
	
	/*
	 * BookTicketController
	 */
	public static Order bookTicket(Train train, String uid, String start, String end, int cartType, int seatPrefer, int[] ticketTypes) {
		return btc.bookTicket(train, uid, start, end, cartType, seatPrefer, ticketTypes);
	}
	
	/*
	 * CheckOrderController
	 */
	public static Order checkOrder(String uid, String orderNumber) {
		return coc.checkOrder(uid, orderNumber);
	}
	
	public static Order deleteTicket(Order order, Ticket[] tickets) {
		return coc.deleteTicket(order, tickets);
	}
	
	/*
	 * CheckTimetableController
	 */
	public static Train[] checkTimetable(String date, int direction) {
		return ctc.checkTimetable(date, direction);
	}
	
	
}
