package control;

import data.Train;
import dbconnector.QueryInterface;
import dbconnector.Query;

public class CheckTimetableController {

	private QueryInterface query;
	
	public CheckTimetableController(QueryInterface query){
		this.query = query;
	}
	
	public Train[] checkTimetable(String date, int direction) {
		Train[] trainList = query.checkTimetable(date, direction);
		return trainList;
	}
	
	public static void main(String[] args) {
		CheckTimetableController timeWatcher = new CheckTimetableController(new Query());
		String date = "2019-01-12";
		int direction = 0;
		Train[] ts = timeWatcher.checkTimetable(date, direction);
		for(Train t:ts)
			System.out.println(t.getTid());
	}
}
