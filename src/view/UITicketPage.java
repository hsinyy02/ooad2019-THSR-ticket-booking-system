package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import control.ControlManager;
import data.Order;
import data.Ticket;
import data.Train;

public class UITicketPage extends JFrame {

	private JPanel contentPane;

	/*
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UITicketPage frame = new UITicketPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */

	public UITicketPage(String uid, String date, String startStn, String endStn, Train train, int cartType, int seatPrefer, int[] ticketTypes) {
		
		String tid = train.getTid();
		String startTime = train.getTimetable(startStn);
		String endTime = train.getTimetable(endStn);
		
		String earlyBird = "";
		String student = "";
		if(ticketTypes[0]!=0 && !ControlManager.checkEarlyBird(train, ticketTypes[0]).equals("")) 
			earlyBird = ControlManager.checkEarlyBird(train, cartType);
		if (ticketTypes[4] != 0 && !ControlManager.checkStudent(train, ticketTypes[4]).equals("")) 
			student = ControlManager.checkStudent(train, cartType);
		String discount = (earlyBird + student).equals("") ? "無" : earlyBird + "\n" + student;
		
		//SearchTrainController searchMan = new SearchTrainController(new Query(), new TrainService());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 381, 592);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(-5, 0, 249, 85);
		lblNewLabel_5.setIcon(new ImageIcon(getClass().getResource("..\\img\\logo.jpg")));
		contentPane.add(lblNewLabel_5);
		
		JLabel label = new JLabel("\u8A02\u7968\u8CC7\u8A0A");
		label.setBounds(20, 95, 60, 21);
		contentPane.add(label);
		
		JLabel label_userid = new JLabel("\u4F7F\u7528\u8005ID");
		label_userid.setBounds(30, 135, 60, 21);
		contentPane.add(label_userid);
		
		JLabel showUserID = new JLabel("<dynamic>");
		showUserID.setBounds(90, 135, 85, 21);
		showUserID.setText(uid);
		contentPane.add(showUserID);
		
		JLabel label_date = new JLabel("\u642D\u4E58\u65E5\u671F");
		label_date.setBounds(180, 135, 60, 21);
		contentPane.add(label_date);
		
		JLabel showDate = new JLabel("New label");
		showDate.setBounds(240, 135, 85, 21);
		showDate.setText(date);
		contentPane.add(showDate);
		
		JLabel label_tid = new JLabel("\u8ECA\u6B21");
		label_tid.setBounds(30, 170, 60, 21);
		contentPane.add(label_tid);
		
		JLabel showTid = new JLabel("<dynamic>");
		showTid.setBounds(90, 170, 85, 21);
		showTid.setText(tid);
		contentPane.add(showTid);
		
		JLabel label_discount = new JLabel("\u9069\u7528\u512A\u60E0");
		label_discount.setBounds(180, 170, 60, 21);
		contentPane.add(label_discount);
		
		JLabel showDiscount = new JLabel("New label");
		showDiscount.setVerticalAlignment(SwingConstants.TOP);
		showDiscount.setBounds(240, 170, 145, 56);
		showDiscount.setText(discount);
		contentPane.add(showDiscount);
		
		JLabel label_cart = new JLabel("\u8ECA\u5EC2");
		label_cart.setBounds(30, 205, 60, 21);
		contentPane.add(label_cart);
		
		JLabel showCart = new JLabel("<dynamic>");
		showCart.setBounds(90, 205, 85, 21);
		if (cartType == Ticket.CartStandard)
			showCart.setText("標準");
		else
			showCart.setText("商務");
		contentPane.add(showCart);
		
		JButton btnConfirm = new JButton("\u78BA\u8A8D\u8A02\u7968");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Order order = ControlManager.bookTicket(train, uid, startStn, endStn, cartType, seatPrefer, ticketTypes);
				String message = "使用者ID: " + uid + "\n訂單代號: " + order.getOrderNumber() + "\n欲查詢車票詳細資訊，請至訂單查詢頁面。";
				JOptionPane.showMessageDialog(null, message, "已成功訂票！", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		btnConfirm.setBounds(20, 512, 334, 40);
		contentPane.add(btnConfirm);
		
		JLabel label_adult = new JLabel("\u5168\u7968");
		label_adult.setBounds(30, 250, 60, 21);
		contentPane.add(label_adult);
		
		JLabel showAdult = new JLabel("\u5168\u7968");
		showAdult.setBounds(90, 250, 40, 21);
		showAdult.setText(String.valueOf(ticketTypes[0]));
		contentPane.add(showAdult);
		
		JLabel label_1 = new JLabel("\u5F35");
		label_1.setBounds(130, 250, 30, 21);
		contentPane.add(label_1);
		
		JLabel label_kid = new JLabel("\u5B69\u7AE5");
		label_kid.setBounds(30, 290, 60, 21);
		contentPane.add(label_kid);
		
		JLabel showKid = new JLabel("\u5168\u7968");
		showKid.setBounds(90, 290, 40, 21);
		showKid.setText(String.valueOf(ticketTypes[1]));
		contentPane.add(showKid);
		
		JLabel label_2 = new JLabel("\u5F35");
		label_2.setBounds(130, 290, 30, 21);
		contentPane.add(label_2);
		
		JLabel label_old = new JLabel("\u656C\u8001");
		label_old.setBounds(30, 330, 60, 21);
		contentPane.add(label_old);
		
		JLabel showOld = new JLabel("\u5168\u7968");
		showOld.setBounds(90, 330, 40, 21);
		showOld.setText(String.valueOf(ticketTypes[2]));
		contentPane.add(showOld);
		
		JLabel label_3 = new JLabel("\u5F35");
		label_3.setBounds(130, 330, 30, 21);
		contentPane.add(label_3);
		
		JLabel label_prior = new JLabel("\u611B\u5FC3");
		label_prior.setBounds(30, 370, 60, 21);
		contentPane.add(label_prior);
		
		JLabel showPrior = new JLabel("\u5168\u7968");
		showPrior.setBounds(90, 370, 40, 21);
		showPrior.setText(String.valueOf(ticketTypes[3]));
		contentPane.add(showPrior);
		
		JLabel label_4 = new JLabel("\u5F35");
		label_4.setBounds(130, 370, 30, 21);
		contentPane.add(label_4);
		
		JLabel label_student = new JLabel("\u5927\u5B78\u751F");
		label_student.setBounds(30, 410, 60, 21);
		contentPane.add(label_student);
		
		JLabel showStudent = new JLabel("\u5168\u7968");
		showStudent.setBounds(90, 410, 40, 21);
		showStudent.setText(String.valueOf(ticketTypes[4]));
		contentPane.add(showStudent);
		
		JLabel label_5 = new JLabel("\u5F35");
		label_5.setBounds(130, 410, 30, 21);
		contentPane.add(label_5);
		
		JLabel label_total = new JLabel("\u5171");
		label_total.setBounds(200, 410, 40, 21);
		contentPane.add(label_total);
		
		int sum = 0;
		for (int i = 0; i < ticketTypes.length; i++){
			sum += ticketTypes[i];
		}
		JLabel showTotal = new JLabel("\u5168\u7968");
		showTotal.setBounds(240, 410, 40, 21);
		showTotal.setText(String.valueOf(sum));
		contentPane.add(showTotal);
		
		JLabel label_6 = new JLabel("\u5F35");
		label_6.setBounds(280, 410, 30, 21);
		contentPane.add(label_6);
		
		JLabel label_price = new JLabel("\u7E3D\u50F9");
		label_price.setBounds(200, 450, 40, 21);
		contentPane.add(label_price);
		
		int price = ControlManager.getTotalPrice(train, startStn, endStn, cartType, ticketTypes);
		JLabel showPrice = new JLabel("\u5168\u7968");
		showPrice.setBounds(240, 450, 40, 21);
		showPrice.setText(String.valueOf(price));
		contentPane.add(showPrice);
		
		JLabel label_8 = new JLabel("\u5143");
		label_8.setBounds(280, 450, 30, 21);
		contentPane.add(label_8);
		
	}
}
