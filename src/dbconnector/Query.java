package dbconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data.Station;
import data.Ticket;
import data.Train;
import dbconnector.config.DBUtils;
import discount.Children;
import discount.Discount;
import discount.EarlyBird65;
import discount.EarlyBird80;
import discount.EarlyBird90;
import discount.Elderly;
import discount.NeedLove;
import discount.Standard;
import discount.Student50;
import discount.Student70;
import discount.Student85;

public class Query implements QueryInterface {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public Train[] searchTrain(String date, String startStation, String endStation, String startTime, int cartType,	int ticketQty) {
		startStation = Station.getEngName(startStation);
		endStation = Station.getEngName(endStation);
		String cartCode = (cartType == Ticket.CartStandard) ? "std_left" : "bus_left";

		
		Train t = null;
		List<Train> trains = new ArrayList<Train>();
		String sql = "select d.`train_id` as tid,d.`date`,d.`early65_left` as early65,d.`early80_left` as early80,"
				+ "d.`early90_left` as early90,d.`student_discount` as universityDiscount,t.`Nangang`,t.`Taipei`,"
				+ "t.`Banciao`,t.`Taoyuan`,t.`Hsinchu`,t.`Miaoli`,t.`Taichung`,t.`Changhua`,t.`Yunlin`,t.`Chiayi`,"
				+ "t.`Tainan`,t.`Zuoying` from dailyTrain as d left join timeTable as t on d.train_id = t.train_id "
				+ "where date = ? and t." + startStation + " < t." + endStation + " and t." + startStation
				+ " > ? and t." + endStation + " > ? and d." + cartCode + " >=? ORDER BY t." + startStation + " ASC";

		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, startTime);
			ps.setString(3, startTime);
			ps.setInt(4, ticketQty);
			rs = ps.executeQuery();
			while (rs.next()) {
				// Train(String tid, String date, int earlyBird65, int earlyBird80, int
				// earlyBird90,
				// double universityDiscount, String[] time)
				String[] timeString = new String[] { rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
						rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18) };
				t = new Train(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getDouble(6), timeString);
				trains.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
		return trains.toArray(new Train[0]);
	}

	public String[] getUnavailableSeatList(Train train) {
		List<String> bookedSeats = new ArrayList<String>();

		String sql = "SELECT tickets.`seat` FROM tickets WHERE train_id = ? AND date = ?";

		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, train.getTid());
			ps.setString(2, train.getDate());
			rs = ps.executeQuery();
			while (rs.next()) {
				bookedSeats.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}

		return bookedSeats.toArray(new String[0]);
	}

	public void addTicket(String orderNumber, String uid, Ticket ticket) {

		String sql = "INSERT INTO `thsr`.`tickets` (`tid`, `uid`, `code`, `train_id`, `date`, `start`, `end`,"
				+ " `start_time`, `end_time`, `type`, `seat`, `price`, `created_at`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ticket.getTicketNumber());
			ps.setString(2, uid);
			ps.setString(3, orderNumber);
			ps.setString(4, ticket.getTid());
			ps.setString(5, ticket.getDate());
			ps.setString(6, ticket.getStart());
			ps.setString(7, ticket.getEnd());
			ps.setString(8, ticket.getStime());
			ps.setString(9, ticket.getEtime());
			ps.setString(10, ticket.getDiscountType().getName());
			ps.setString(11, ticket.getSeatNum());
			ps.setInt(12, ticket.getPrice());
			ps.setString(13, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
	}

	@Override
	public void updateEarlyBird(String tid, String date, double earlyBirdDiscount, int num) {
		String earlyCode="";

		switch (Double.toString(earlyBirdDiscount)) {
		case "0.65":
			earlyCode = "early65_left = early65_left - " + num;
			break;
		case "0.8":
			earlyCode = "early80_left = early80_left - " + num;
			break;
		case "0.9":
			earlyCode = "early90_left = early90_left - " + num;
			break;
		}

		String sql = "UPDATE dailyTrain SET " + earlyCode + " WHERE train_id=? AND date=?";
		
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, tid);
			ps.setString(2, date);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
	}

	@Override
	public void updateSeatLeft(String tid, String date, int cartType, int num) {
		
		String seatType = (cartType == Ticket.CartStandard) ? ("std_left = std_left - " + num) : ("bus_left = bus_left - " + num);
		
		String sql = "UPDATE dailyTrain SET " + seatType + " WHERE train_id=? AND date=?";
		
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, tid);
			ps.setString(2, date);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
	}
	
	public Ticket[] getOrderTicket(String uid, String orderNumber) {
		//Ticket(String ticketNumber, String tid, String date, String start, String end, String stime, String etime,
		//			int cartType, String seatNum, Discount discount)
		String sql = "SELECT t.`tid`, t.`train_id`, t.`date`, t.`start`, t.`end`, t.`start_time`, t.`end_time`,"
				+ "t.`seat`, t.`type` FROM tickets AS t WHERE uid = ? AND code = ?";
		List<Ticket> orderTickets = new ArrayList<Ticket>();
		
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setString(2, orderNumber);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String ticketNumber = rs.getString(1);
				String tid = rs.getString(2);
				String date = rs.getString(3);
				String start = rs.getString(4);
				String end = rs.getString(5);
				String stime = rs.getString(6);
				String etime = rs.getString(7);
				int cartType = rs.getString(8).substring(0, 2).equals("06") ? Ticket.CartBusiness : Ticket.CartStandard;
				String seatNum = rs.getString(8);
				Discount discount = getDiscount(rs.getString(9));
				//Ticket(String ticketNumber, String tid, String date, String start, String end, String stime, String etime,
				//			int cartType, String seatNum, Discount discount)
				Ticket ticket = new Ticket(ticketNumber, tid, date, start, end, stime, etime, cartType ,seatNum , discount);
				orderTickets.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
		
		return orderTickets.toArray(new Ticket[0]);
		
	};

	public void deleteTicket(Ticket ticket) {
		String sql = "DELETE FROM tickets WHERE tid=?";
		
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ticket.getTicketNumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
	}

	public Train[] checkTimetable(String date, int direction) {
		
		String startStation = direction == 0 ? "Nangang" : "Zuoying";
		Train t = null;
		List<Train> trains = new ArrayList<Train>();
		String sql = "select d.`train_id` as tid,d.`date`,d.`early65_left` as early65,d.`early80_left` as early80,"
				+ "d.`early90_left` as early90,d.`student_discount` as universityDiscount,t.`Nangang`,t.`Taipei`,"
				+ "t.`Banciao`,t.`Taoyuan`,t.`Hsinchu`,t.`Miaoli`,t.`Taichung`,t.`Changhua`,t.`Yunlin`,t.`Chiayi`,"
				+ "t.`Tainan`,t.`Zuoying` from dailyTrain as d left join timeTable as t on d.train_id = t.train_id "
				+ "where date = ? and t.`direction` = " + direction + " ORDER BY t." + startStation + " ASC";

		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while (rs.next()) {
				// Train(String tid, String date, int earlyBird65, int earlyBird80, int
				// earlyBird90,
				// double universityDiscount, String[] time)
				String[] timeString = new String[] { rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
						rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18) };
				t = new Train(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getDouble(6), timeString);
				trains.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, ps, conn);
		}
		return trains.toArray(new Train[0]);
	}
	
	private Discount getDiscount(String dName) {
		switch(dName) {
		case "布": return new Standard();
		case "担布": return new Children();
		case "穛ρ布": return new Elderly();
		case "稲み布": return new NeedLove();
		case "Ν尘65ч": return new EarlyBird65();
		case "Ν尘8ч": return new EarlyBird80();
		case "Ν尘9ч": return new EarlyBird90();
		case "厩ネ布5ч": return new Student50();
		case "厩ネ布7ч": return new Student70();
		case "厩ネ布85ч": return new Student85();
		default: return new Standard();
		}
			
	}
	
}