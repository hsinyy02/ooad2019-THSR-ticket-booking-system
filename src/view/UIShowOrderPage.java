package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.CheckOrderController;
import control.ControlManager;
import data.Order;
import data.Ticket;
import dbconnector.Query;

public class UIShowOrderPage extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIShowOrderPage frame = new UIShowOrderPage();
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
	public UIShowOrderPage(String uid, String orderNum, Order order) {
		
		//CheckOrderController deleteTicket = new CheckOrderController(new Query());
		
		ArrayList<Ticket> tickets = order.getTicketList();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 360, 592);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon(getClass().getResource("..\\img\\logo.jpg")));
		icon.setBounds(-5, 0, 249, 85);
		contentPane.add(icon);
		
		JLabel label_uid = new JLabel("\u4F7F\u7528\u8005ID");
		label_uid.setBounds(20, 95, 60, 21);
		contentPane.add(label_uid);
		
		JLabel showUid = new JLabel("New label");
		showUid.setBounds(90, 95, 85, 21);
		showUid.setText(uid);
		contentPane.add(showUid);
		
		JLabel label_orderNum = new JLabel("\u8A02\u55AE\u7DE8\u865F");
		label_orderNum.setBounds(20, 125, 60, 21);
		contentPane.add(label_orderNum);
		
		JLabel showOrderNum = new JLabel("New label");
		showOrderNum.setBounds(90, 125, 85, 21);
		showOrderNum.setText(orderNum);
		contentPane.add(showOrderNum);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 156, 324, 347);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		DefaultTableModel model = new DefaultTableModel() {
		      public Class getColumnClass(int columnIndex) {
		        return String.class;
		      }
		      public boolean isCellEditable(int row, int column) {
					return column == 1;
			  }
		};
		
		table.setModel(model);
		
		int lines = 6;
	    table.setRowHeight(20 * lines);
		
	    table.setDefaultRenderer(String.class, new MultiLineCellRenderer());
	    
	    List<String> column = new ArrayList<String>();
	    
		for (int i = 0; i < tickets.size(); i++){
			Ticket ticket = tickets.get(i);
			String ticketNo = "車票代號：" + ticket.getTicketNumber() + "\n";
			String date_tid = ticket.getDate() + "  車次：" + ticket.getTid() + "\n";
			String start = ticket.getStart() + "(" + ticket.getStime() + ") → ";
			String end = ticket.getEnd() + "(" + ticket.getEtime() + ")\n";
			String seat;
			if (ticket.getCartType() == Ticket.CartStandard){
				seat = "標準廂：" + ticket.getSeatNum() + "\n";
			} else {
				seat = "商務廂：" + ticket.getSeatNum() + "\n";
			}
			String type = "票種：" + ticket.getDiscountType().getName() + "\n";
			String price = "價格：" + String.valueOf(ticket.getPrice());
			column.add(ticketNo + date_tid + start + end + seat + type + price);
			
			//isdelete.add(false);
		}
		
		model.addColumn("車票", column.toArray());
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		scrollPane.setViewportView(table);
		
		JButton btn = new JButton("\u78BA\u8A8D\u522A\u9664");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				ArrayList<Ticket> deleteT = new ArrayList<Ticket>();
				int[] selected = table.getSelectedRows();
				for (int i : selected){
					//System.out.println(selected[i]);
					deleteT.add(tickets.get(i));
				}
				ControlManager.deleteTicket(order, deleteT.toArray(new Ticket[0]));
				JOptionPane.showMessageDialog(null, "已刪除選擇車票。", "修改訂單完成！", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btn.setBounds(10, 513, 334, 40);
		contentPane.add(btn);
	}
	
	class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

		  public MultiLineCellRenderer() {
		    setLineWrap(true);
		    setWrapStyleWord(true);
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(table.getBackground());
		    }
		    setFont(table.getFont());
		    if (hasFocus) {
		      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		      if (table.isCellEditable(row, column)) {
		        setForeground(UIManager.getColor("Table.focusCellForeground"));
		        setBackground(UIManager.getColor("Table.focusCellBackground"));
		      }
		    } else {
		      setBorder(new EmptyBorder(1, 2, 1, 2));
		    }
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
	}
}
