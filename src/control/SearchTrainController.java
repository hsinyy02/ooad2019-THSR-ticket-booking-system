package control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

import data.Price;
import data.Ticket;
import data.Train;
import dbconnector.Query;
import dbconnector.QueryInterface;
import discount.Children;
import discount.EarlyBird;
import discount.Elderly;
import discount.NeedLove;
import discount.Standard;
import discount.Student;
import service.TrainService;
import service.TrainServiceInterface;

public class SearchTrainController {
	
	public QueryInterface query;
	public TrainServiceInterface trainService;
	
	public SearchTrainController(QueryInterface query, TrainServiceInterface trainService) {
		this.query = query;
		this.trainService = trainService;
	}
	
	public Train[] searchTrain(String date, String startStation, String endStation, String startTime, int cartType, int[] ticketTypes) {
		Train[] trainList;
		int ticketQty=0;
		for(int ticketNum : ticketTypes)
			ticketQty += ticketNum;
		
		trainList = query.searchTrain(date, startStation, endStation, startTime, cartType, ticketQty);
//		sortTrainByTime(trainList, startStation);
		return trainList;
	}
	
	
	public int getTotalPrice(Train train, String startStation, String endStation, int cartType, int[] ticketTypes) {
		int totalPrice = 0;
		int standardT = ticketTypes[0];
		int childrenT = ticketTypes[1];
		int elderlyT = ticketTypes[2];
		int needLoveT = ticketTypes[3];
		int studentT = ticketTypes[4];
		
		totalPrice += Price.getPrice(startStation, endStation, cartType, new Children()) * childrenT;
		totalPrice += Price.getPrice(startStation, endStation, cartType, new Elderly()) * elderlyT;
		totalPrice += Price.getPrice(startStation, endStation, cartType, new NeedLove()) * needLoveT;
		totalPrice += Price.getPrice(startStation, endStation, cartType, train.getUniversityDiscount()) * studentT;
		
		if(standardT != 0 && trainService.checkEarlyBird(train, standardT) instanceof EarlyBird)
			totalPrice += Price.getPrice(startStation, endStation, cartType, trainService.checkEarlyBird(train, standardT)) * standardT;
		else
			totalPrice += Price.getPrice(startStation, endStation, cartType, new Standard()) * standardT;
		
		return totalPrice;
	}
	
	public String getTotalTime(Train train, String startStation, String endStation) {
		
		String startTime = train.getTimetable(startStation);
		String endTime = train.getTimetable(endStation);
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		long difference = 0;
		try {
			difference = format.parse(endTime).getTime() - format.parse(startTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int hours = (int) (difference/ (60 * 60 * 1000) % 24);
		int mins = (int) (difference / (60 * 1000) % 60);
		String totalTime = String.format("%d:%d", hours, mins);
		return totalTime;
	}
	
	public String checkEarlyBird(Train train, int standardT) {
		String earlyBirdDiscount = "";
		if(standardT != 0 && trainService.checkEarlyBird(train, standardT) instanceof EarlyBird)
			earlyBirdDiscount = new TrainService().checkEarlyBird(train, standardT).getName();
		else
			earlyBirdDiscount = "";
		return earlyBirdDiscount;
	}
	
	public String checkStudent(Train train, int studentT) {
		String studentDiscount = "";
		if(studentT != 0 && train.getUniversityDiscount() instanceof Student)
			studentDiscount = train.getUniversityDiscount().getName();
		else
			studentDiscount = "";
		return studentDiscount;
	}
	
	private void sortTrainByTime(Train[] trainList, String startStation) {

		Arrays.sort(trainList, new Comparator<Train>() {
			@Override
			public int compare(Train train1, Train train2) {
				int startTime1 = Integer.parseInt(train1.getTimetable(startStation));
				int startTime2 = Integer.parseInt(train2.getTimetable(startStation));
				if(startTime1 > startTime2) 
					return 1;
				else if(startTime1 < startTime2) 
					return -1;
				else
					return 0;
			}
	    });
	}
	
	public static void main(String[] args) {
		
		String startStation = "台北";
		String endStation = "桃園";
		String date = "2018/12/25";
		String startTime = "0600";
		int cartType = Ticket.CartStandard;
		
		int standard = 2;
		int children = 1;
		int elderly = 1;
		int needlove = 1;
		int student = 2;
		int[] ticketTypes = {standard, children, elderly, needlove, student};
		
		SearchTrainController searchMan = new SearchTrainController(new Query(), new TrainService());
		Train[] sortedTrainList = searchMan.searchTrain(date, startStation, endStation, startTime, cartType, ticketTypes);
		
		for(Train train : sortedTrainList) {
			System.out.println(
					"車號: " + train.getTid() + 
					", 從 " + startStation +"("+ train.getTimetable(startStation) + 
					") 到 " + endStation +"("+ train.getTimetable(endStation) + 
					"), 總價:" + searchMan.getTotalPrice(train, startStation, endStation, cartType, ticketTypes) + 
					", 行車時間:" + searchMan.getTotalTime(train, startStation, endStation) + 
					searchMan.checkEarlyBird(train, standard) +
					searchMan.checkStudent(train, student));
		}
	}
}
