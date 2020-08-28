package parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dbconnector.config.DBUtils;

/**
 * This class is used to parse the online time table. 
 * @author Eva
 */
public class TimeTableParser {
	
	/**
	 * Parse the time table and insert to mysql.
	 * @param date update date.
	 * @param dir go south or north.
	 * @throws SQLException SQLException
	 */	
	public static void main(String[] args) throws SQLException, IOException {
//		String url = "http://ptx.transportdata.tw/MOTC/v2/Rail/THSR/GeneralTimetable?$top=3000&$format=json";
		
		JSONParser parser = new JSONParser();
		
        try {
        	
        		Object object = parser.parse(new FileReader("src/parser/json/GeneralTimetable.json"));
            
            //convert Object to JSONObject
        		JSONArray json = (JSONArray) object;
            
            for(Object o: json){
        			ArrayList<String> dataArray = new ArrayList<String>();
            		JSONObject jsonObject = (JSONObject)o;
                Object generalTimetable = jsonObject.get("GeneralTimetable");
                
                //取得車次與方向 GeneralTrainInfo
                Object trainInfo = ((JSONObject)generalTimetable).get("GeneralTrainInfo");
                String trainId = (String)((JSONObject)trainInfo).get("TrainNo");
                Long dir = (Long)((JSONObject)trainInfo).get("Direction");
                dataArray.add("\"" + trainId + "\"");
                dataArray.add(String.valueOf(dir));
                
                //取得行車時間 StopTimes
                Object stopTimes = ((JSONObject)generalTimetable).get("StopTimes");                
                String[] stops = {"Nangang", "Taipei", "Banciao", "Taoyuan", "Hsinchu", "Miaoli", "Taichung", "Changhua", "Yunlin", "Chiayi", "Tainan", "Zuoying"};
                
                for(int i =0 ; i < stops.length ; i ++) {
                		dataArray.add(null);
                    for(Object t: (JSONArray) stopTimes) {
                    		String stopName = (String)((JSONObject)((JSONObject)t).get("StationName")).get("En");
                    		if(stopName.equals(stops[i])) {
                    			dataArray.set(i+2, "\"" + (String)((JSONObject)t).get("DepartureTime") + "\"");
                    			break;
                    		} 
                    }
                }                
                
                //取得行車星期 ServiceDay
                
                Object serviceDay = ((JSONObject)generalTimetable).get("ServiceDay");
                String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                
                for(int i =0 ; i < days.length ; i ++) {
                		Long isServe = (Long)((JSONObject)serviceDay).get(days[i]);
                		dataArray.add(String.valueOf(isServe));
                }
                
                //輸入到資料庫囉 <3
                
		    		Connection conn = null;
		    		PreparedStatement ps = null;          
				String sql = String.format("INSERT INTO timeTable VALUES (%s)", String.join(", ", dataArray));
				
				//System.out.println(sql);
				conn = DBUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				conn.close();           
                 
            	}
 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
