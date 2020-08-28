package data;

import java.util.Arrays;

import discount.*;

public class Price {
	
	public static final int StdPrice[][] = {
			{},
			{40},
			{70, 40},
			{200, 160, 130},
			{330, 290, 260, 130},
			{480, 430, 400, 280, 140},
			{750, 700, 670, 540, 410, 270},
			{870, 820, 790, 670, 540, 390, 130},
			{970, 930, 900, 780, 640, 500, 230, 110},
			{1120, 1080, 1050, 920, 790, 640, 380, 250, 150},
			{1390, 1350, 1320, 1190, 1060, 920, 650, 530, 420, 280},
			{1530, 1490, 1460, 1330, 1200, 1060, 790, 670, 560, 410, 140},
	};
	
	public static final int BusPrice[][] = {
			{},
			{260},
			{310, 260},
			{500, 440, 400},
			{700, 640, 590, 400},
			{920, 850, 800, 620, 410},
			{1330, 1250, 1210, 1010, 820, 610},
			{1510, 1430, 1390, 1210, 1010, 790, 400},
			{1660, 1600, 1550, 1370, 1160, 950, 550, 370},
			{1880, 1820, 1780, 1580, 1390, 1160, 770, 580, 430},
			{2290, 2230, 2180, 1990, 1790, 1580, 1180, 1000, 830, 620},
			{2500, 2440, 2390, 2200, 2000, 1790, 1390, 1210, 1040, 820, 410},
	};
	
	
	public static int getPrice(String start, String end, int cartType, Discount discount) {
		int startIdx = Arrays.asList(Station.CHI_NAME).indexOf(start);
		int endIdx = Arrays.asList(Station.CHI_NAME).indexOf(end);
		
		int station1 = Math.max(startIdx, endIdx);
		int station2 = Math.min(startIdx, endIdx);
		
		if(cartType == Ticket.CartBusiness) 
			return (int) (BusPrice[station1][station2] * discount.getDiscount());
		else return (int) (StdPrice[station1][station2] * discount.getDiscount());
	}
	
	public static void main(String[] args) {
		System.out.println(getPrice("台中", "桃園", Ticket.CartStandard, new Standard()));
		System.out.println(getPrice("桃園", "台中", Ticket.CartStandard, new EarlyBird65()));
	}
}
