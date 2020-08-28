package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import control.ControlManager;
import data.Station;
import data.Train;

public class UITimetablePage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UITimetablePage frame = new UITimetablePage();
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
	public UITimetablePage(String date) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 592, 618);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel icon = new JLabel("");
		icon.setBounds(-5, 0, 249, 85);
		icon.setIcon(new ImageIcon(getClass().getResource("..\\img\\logo.jpg")));
		contentPane.add(icon);
		
		JLabel label_date = new JLabel("\u67E5\u8A62\u65E5\u671F");
		label_date.setHorizontalAlignment(SwingConstants.RIGHT);
		label_date.setBounds(380, 95, 60, 21);
		contentPane.add(label_date);

		JLabel showDate = new JLabel("New label");
		showDate.setHorizontalAlignment(SwingConstants.RIGHT);
		showDate.setBounds(450, 95, 121, 21);
		showDate.setText(date);
		contentPane.add(showDate);
		
		JLabel label_headSouth = new JLabel("\u5357\u4E0B");
		label_headSouth.setBounds(10, 95, 60, 21);
		contentPane.add(label_headSouth);
		
		// list train 
		//CheckTimetableController timeWatcher = new CheckTimetableController(new Query());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 122, 566, 200);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u8ECA\u6B21", "\u5357\u6E2F", "\u53F0\u5317", "\u677F\u6A4B", "\u6843\u5712", "\u65B0\u7AF9", "\u82D7\u6817", "\u53F0\u4E2D", "\u5F70\u5316", "\u96F2\u6797", "\u5609\u7FA9", "\u53F0\u5357", "\u5DE6\u71DF"
			}
		){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		int count = table.getColumnCount();
		for (int i = 0; i < count; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		Train[] trainList = ControlManager.checkTimetable(date, 0);
		for(Train train : trainList) {
			List<String> trainTime = new ArrayList<String>();
			trainTime.add(train.getTid());
			for(String station : Station.CHI_NAME) {
				String time = train.getTimetable(station)==null ? " - " : train.getTimetable(station).substring(0, 5);
				trainTime.add(time);
			}
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(trainTime.toArray());
		}
		
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		
		//---------------------------------------
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 363, 566, 200);
		contentPane.add(scrollPane_1);
		
		table_2 = new JTable();
		table_2.setRowSelectionAllowed(false);
		table_2.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u8ECA\u6B21", "\u5DE6\u71DF", "\u53F0\u5357", "\u5609\u7FA9", "\u96F2\u6797", "\u5F70\u5316",  "\u53F0\u4E2D", "\u82D7\u6817","\u65B0\u7AF9","\u6843\u5712",  "\u677F\u6A4B","\u53F0\u5317",  "\u5357\u6E2F" 
				}
			){
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
		DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
		centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
		int count2 = table_2.getColumnCount();
		for (int i = 0; i < count2; i++) {
			table_2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer2);
		}
		
		Train[] trainList2 = ControlManager.checkTimetable(date, 1);
		for(Train train : trainList2) {
			List<String> trainTime = new ArrayList<String>();
			trainTime.add(train.getTid());
			for(int i=Station.CHI_NAME.length-1; i>=0; i--) {
				String station = Station.CHI_NAME[i];
				String time = train.getTimetable(station)==null ? " - " : train.getTimetable(station).substring(0, 5);
				trainTime.add(time);
			}
			DefaultTableModel model = (DefaultTableModel) table_2.getModel();
			model.addRow(trainTime.toArray());
		}
		
		table_2.setRowHeight(30);
		scrollPane_1.setViewportView(table_2);
		
		JLabel label = new JLabel("\u5317\u4E0A");
		label.setBounds(10, 337, 60, 21);
		contentPane.add(label);
		
		
		
	}
}
