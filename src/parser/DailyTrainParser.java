package parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.util.*;

import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.net.URL;
//import java.nio.charset.Charset;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dbconnector.config.DBUtils;

/**
 * This class is used to parse the online time table. 
 * @author Eva
 */
public class DailyTrainParser {
	
	/**
	 * Parse the time table and insert to mysql.
	 * @param date update date. XXXX-XX-XX
	 * @param dir go south or north.
	 * @throws SQLException SQLException
	 */
	public void parseDate(String date) throws SQLException {
		try {
			
			//今天星期幾
			String w = getWeekday(date) ;			
						
			//在timeTable找到星期幾有開的車車
			List<String> trainIds = new ArrayList<String>();
			
			String weekString = "week_" + w;
			String sql = "SELECT train_id from timeTable where " + weekString + "=1";
			Connection conn = null;
			PreparedStatement ps = null;  
			ResultSet rs = null;
			try{
				conn = DBUtils.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					trainIds.add(rs.getString(1));
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				DBUtils.close(rs, ps, conn);
			}
			
			
			//檢查有哪些車車 有優待票 有學生票
			
			for(String trainId: trainIds) {
				List<String> trainDetail = new ArrayList<String>();
				trainDetail.add("0");
				trainDetail.add("\"" + trainId + "\"");
				trainDetail.add("\"" + date + "\"");
				trainDetail.add("667");
				trainDetail.add("66");
				//優待的
				List<String> early = getEarlyBird(trainId, w);
				trainDetail.addAll(early);
				
				//學生的
				String studentDiscount = getStudentDiscount(trainId, w);
				trainDetail.add(studentDiscount);
				
				//System.out.println(trainDetail);
				
				//insert 成default值進來 座位 667 / 66
				
				Connection conn2 = null;
				PreparedStatement ps2 = null;  
				conn2 = DBUtils.getConnection();
					
				String sql2 = String.format("INSERT INTO dailyTrain VALUES (%s)", String.join(", ", trainDetail));
				ps2 = conn2.prepareStatement(sql2);
				ps2.executeUpdate();
				conn2.close();
				
			}
			
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("No available train. dir = " + dir);
		}
	}
	
	/**
	 * Update the time table of date.
	 * @param date update date.
	 * @throws SQLException SQLException
	 * @throws IOException IOException
	 */
	public void updateDay(String date) throws SQLException, IOException {
		System.out.println("Parsing date " + date);
		Connection conn = null;
		PreparedStatement ps = null;  
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		ps = conn.prepareStatement("SELECT COUNT(*) FROM dailyTrain WHERE date = \"" + date + "\"");
		rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		if (count > 0) {
			System.out.println("Data is already in the DB.");
			return;
		}
		DBUtils.close(rs, ps, conn);

		parseDate(date);
		System.out.println("Successed");
	}
	
	/**
	 * 輸入車次星期，可以找到早鳥。
	 * 
	 * @param trainId車次字串 w星期字串 1,2,3,4,5,6,7
	 * @return [20,50,60] 早鳥65,8,9折的票數
	 * 
	 */
	public static List<String> getEarlyBird(String trainId, String w)
	{
		int week = Integer.valueOf(w);
		List<String> earlyArray = new ArrayList<String>();
		earlyArray.add("0");
		earlyArray.add("0");
		earlyArray.add("0");
		

		try {
			JSONParser parser = new JSONParser();
			
			Object object = parser.parse(new FileReader("src/parser/json/earlyDiscount.json"));
	        
	        //convert Object to JSONObject
	    		Object json = ((JSONObject) object).get("DiscountTrains");
	    		JSONArray discountTrains = (JSONArray)json;
	    		
//	    		System.out.println(discountTrains);
	    		
	    		for(Object t : discountTrains) {
	    			String trainNo = (String)((JSONObject)t).get("TrainNo");
	    			if (trainNo.equals(trainId)) {
	    				//check discount
	    				Object discountDay = ((JSONObject)t).get("ServiceDayDiscount");
//	    				System.out.println(discountDay);
	    				String[] day = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	    				
	    				Object discountDetail = ((JSONObject)discountDay).get(day[week-1]);
	    				
	    				if(discountDetail instanceof JSONArray){
	    					for(Object d :(JSONArray) discountDetail) {
		    					Double pos =  (Double) ((JSONObject) d).get("discount");
		    					Long tickets =  (Long) ((JSONObject) d).get("tickets");
		    					
		    					if (pos == 0.65) {
		    						earlyArray.set(0, String.valueOf(tickets));
		    					}
		    					if (pos == 0.8) {
		    						earlyArray.set(1, String.valueOf(tickets));
		    					}
		    					if (pos == 0.9) {
		    						earlyArray.set(2, String.valueOf(tickets));
		    					}
		    					
		    				}
	    				}
	    			}
	    		}
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		
//		System.out.println(earlyArray);
		
		
		return earlyArray;
		
	    

	}
	
	/**
	 * 輸入車次星期，可以找到學生優惠。
	 * 
	 * @param trainId車次字串 w星期字串 1,2,3,4,5,6,7
	 * @return 0.5 學生折數
	 * 
	 */
	public static String getStudentDiscount(String trainId, String w)
	{
		int week = Integer.valueOf(w);
		String discount = "1" ;

		try {
			JSONParser parser = new JSONParser();
			
			Object object = parser.parse(new FileReader("src/parser/json/universityDiscount.json"));
	        
	        //convert Object to JSONObject
	    		Object json = ((JSONObject) object).get("DiscountTrains");
	    		JSONArray discountTrains = (JSONArray)json;
	    		
//	    		System.out.println(discountTrains);
	    		for(Object t : discountTrains) {
    			String trainNo = (String)((JSONObject)t).get("TrainNo");
    			if (trainNo.equals(trainId)) {
    				//check discount
    				Object discountDay = ((JSONObject)t).get("ServiceDayDiscount");
//    				System.out.println(discountDay);
    				String[] day = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    				
    				Object discountDetail = ((JSONObject)discountDay).get(day[week-1]);
    				
    				if(discountDetail instanceof Double){
    					discount = String.valueOf((Double) discountDetail);
    					
    				}
    			}
    		}
	    		
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return discount;
		    

	}
	
	
	/**
	 * 輸入日期，可以轉換成星期幾。
	 * 
	 * @param dateString日期字串 2019-01-11
	 * @return 星期幾 1=Monday
	 * @throws ParseException 無法將字串轉換成java.util.Date類別
	 */
	public static String getWeekday(String date)
	{

	    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdw = new SimpleDateFormat("u");

	    Date d = null;

	    try {

	    		d = sd.parse(date);

	    } catch (ParseException e) {

	    		e.printStackTrace();

	    }

	    return sdw.format(d);

	    }
	
	public static void main(String[] args) throws SQLException, IOException {
		
		Date myDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 0; i < 28; i++) {
				String date = formatter.format(myDate);
				new DailyTrainParser().updateDay(date);
				Calendar c = Calendar.getInstance();
				c.setTime(myDate);
				c.add(Calendar.DATE, 1);
				myDate = c.getTime();
			}
			JOptionPane.showMessageDialog(null, "Successed.", "InfoBox: Finish",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException | IOException e) {
			System.out.println("Error while parsing time table");
			e.printStackTrace();
		}
		
		
		

		


	}

}
