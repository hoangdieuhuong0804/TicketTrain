package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DB_Connection.StatementMySQL;
import Model.ClientBookTickets;
import Model.InfoTrain;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class HistoryBooked extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private List<ClientBookTickets> tablelist = new ArrayList<ClientBookTickets>();
	DefaultTableModel tableModel;
	private JTextField tf_ngay;
	private JTextField tf_thang;
	private JTextField tf_nam;
	private JTextField tf_mahd;
	static Socket socketInfo;
	static Socket socketTrain;
	static String username;
	String status = "true";
	BufferedReader in;
	PrintWriter out;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HistoryBooked dialog = new HistoryBooked(username,socketInfo,socketTrain);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param socketTrain 
	 * @param socketInfo 
	 * @param username 
	 * @throws IOException 
	 */
	public HistoryBooked(String username, Socket socketInfo, Socket socketTrain) throws IOException {
		this.username = username;
		this.socketInfo = socketInfo;
		this.socketTrain = socketTrain;
		setTitle("Lịch Sử Đặt Vé");

		URL url = HistoryBooked.class.getResource("/Icon/File.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);

		setBounds(320, 118, 910, 490);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lb_timkiemtheongay = new JLabel("Tìm Kiếm Theo Ngày/Tháng/Năm:");
		lb_timkiemtheongay.setForeground(Color.RED);
		lb_timkiemtheongay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_timkiemtheongay.setBounds(25, 33, 312, 25);
		contentPanel.add(lb_timkiemtheongay);

		JButton bt_timkiem1 = new JButton("Tìm Kiếm");
		bt_timkiem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchByTime();;
//				showAll2();
			}

		});
		bt_timkiem1.setBackground(Color.GRAY);
		bt_timkiem1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bt_timkiem1.setBounds(201, 68, 113, 31);
		contentPanel.add(bt_timkiem1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton bt_quaylai = new JButton("Quay Lại");
				bt_quaylai.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				bt_quaylai.setBackground(Color.WHITE);
				bt_quaylai.setFont(new Font("Tahoma", Font.PLAIN, 18));
				bt_quaylai.setActionCommand("Cancel");
				buttonPane.add(bt_quaylai);
			}
		}

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID Vé", "Tên Chuyến Tàu", "Thời Gian", "Ga Bắt Đầu","Ga Kết Thúc","Giá Vé" }));
		table.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(49, 142, 794, 191);
		contentPanel.add(scrollPane);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 552, 852, 2);
		contentPanel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 495, 876, 2);
		contentPanel.add(separator_1);

		tf_ngay = new JTextField();
		tf_ngay.setBackground(Color.WHITE);
		tf_ngay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_ngay.setBounds(49, 68, 31, 31);
		contentPanel.add(tf_ngay);
		tf_ngay.setColumns(10);

		tf_thang = new JTextField();
		tf_thang.setBackground(Color.WHITE);
		tf_thang.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_thang.setBounds(90, 68, 31, 31);
		contentPanel.add(tf_thang);
		tf_thang.setColumns(10);

		tf_nam = new JTextField();
		tf_nam.setBackground(Color.WHITE);
		tf_nam.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_nam.setBounds(131, 68, 50, 31);
		contentPanel.add(tf_nam);
		tf_nam.setColumns(10);

		JLabel lb_mahd = new JLabel("Tìm Kiếm Theo Mã Hóa Đơn:");
		lb_mahd.setForeground(Color.RED);
		lb_mahd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_mahd.setBounds(574, 33, 258, 25);
		contentPanel.add(lb_mahd);

		tf_mahd = new JTextField();
		tf_mahd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_mahd.setBounds(584, 68, 127, 28);
		contentPanel.add(tf_mahd);
		tf_mahd.setColumns(10);

		JButton bt_timkiem2 = new JButton("Tìm Kiếm");
		bt_timkiem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				Search2();
//				showAll2();
				searchID();
			}

		});
		bt_timkiem2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bt_timkiem2.setBackground(Color.GRAY);
		bt_timkiem2.setBounds(730, 68, 113, 31);
		contentPanel.add(bt_timkiem2);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 400, 876, 2);
		contentPanel.add(separator_2);

		JLabel lb_day = new JLabel("(DD)");
		lb_day.setForeground(Color.RED);
		lb_day.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lb_day.setBounds(49, 109, 31, 13);
		contentPanel.add(lb_day);

		JLabel lb_month = new JLabel("(MM)");
		lb_month.setForeground(Color.RED);
		lb_month.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lb_month.setBounds(90, 109, 31, 13);
		contentPanel.add(lb_month);

		JLabel lb_year = new JLabel("(YYYY)");
		lb_year.setForeground(Color.RED);
		lb_year.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lb_year.setBounds(141, 109, 39, 13);
		contentPanel.add(lb_year);

		JButton bt_xoa = new JButton("Xóa");
		bt_xoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				xoaDon();
				deleteData();

//					System.out.println(value);

			}

		});
		bt_xoa.setBackground(Color.GRAY);
		bt_xoa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bt_xoa.setBounds(730, 359, 113, 31);
		contentPanel.add(bt_xoa);

		JButton btnNewButton = new JButton("Menu");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					loadData(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				showAll2();

			}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setBounds(595, 359, 85, 31);
		contentPanel.add(btnNewButton);
		tableModel = (DefaultTableModel) table.getModel();
		loadData(username);
//		showAll2();
		setVisible(true);
	}
	
	private void loadData(String username) throws IOException {
		
		Thread datalist = new Thread() {
			public void run() {
	            try {
	            		tablelist.clear();	
	                	StatementMySQL statementMySQL = new StatementMySQL();
	                	tablelist = statementMySQL.dataUser(username,status);
	                	 
	            		for (int i = 0; i < tablelist.size(); i++) {
	            			tableModel.setRowCount(i);
	            			tableModel.addRow(new Object[] { tablelist.get(i).getIdtickets(),loadNameTrain(tablelist.get(i).getMash()),tablelist.get(i).getDay(),
	            					tablelist.get(i).getLocationStartClient(),tablelist.get(i).getLocationEndClient(),tablelist.get(i).getPriceTickets()
	                   
	            			});

	            		}
//	                	sleep(50000);
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
		}
	 };
	 datalist.start();
		
		
	}
	private String loadNameTrain(String mash) {
		StatementMySQL statementMySQL = new StatementMySQL();
		return statementMySQL.getNameTrain(mash);
	}
	
	private void searchID() {
		tablelist.clear();
		StatementMySQL statementMySQL = new StatementMySQL();
		tablelist = statementMySQL.dataUserByID(tf_mahd.getText().toUpperCase(), username,status);
   	 
		if(tablelist.size() > 0) {
			for (int i = 0; i < tablelist.size(); i++) {
				tableModel.setRowCount(i);
				tableModel.addRow(new Object[] { tablelist.get(i).getIdtickets(),loadNameTrain(tablelist.get(i).getMash()),tablelist.get(i).getDay(),
						tablelist.get(i).getLocationStartClient(),tablelist.get(i).getLocationEndClient(),tablelist.get(i).getPriceTickets()
	       
				});

			}
		}else {
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}
		
	}
	
	private void searchByTime() {
		tablelist.clear();
		StatementMySQL statementMySQL = new StatementMySQL();
		tablelist = statementMySQL.dataUserByTime(tf_nam.getText(), tf_thang.getText(), tf_ngay.getText(), username,status);
		if(tablelist.size() > 0) {
			for (int i = 0; i < tablelist.size(); i++) {
				tableModel.setRowCount(i);
				tableModel.addRow(new Object[] { tablelist.get(i).getIdtickets(),loadNameTrain(tablelist.get(i).getMash()),tablelist.get(i).getDay(),
						tablelist.get(i).getLocationStartClient(),tablelist.get(i).getLocationEndClient(),tablelist.get(i).getPriceTickets()
	       
				});

			}
		}else {
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}
		
	}
	
	private void deleteData() {
		int row = table.getSelectedRow();
		String value = table.getModel().getValueAt(row, 0).toString();
//		System.out.println("Row: " +row);
//		System.out.println("Value: " +value);
		int n = JOptionPane.showConfirmDialog(HistoryBooked.this, "Bạn Có Chắc Chắn Muốn Xóa Hóa Đơn Này ?", "Hóa Đơn", JOptionPane.YES_NO_OPTION);
		if (n==JOptionPane.YES_OPTION) {
			StatementMySQL statementMySQL = new StatementMySQL();
			int num = statementMySQL.deleteByID(value);
			if(num != 0) {
				JOptionPane.showMessageDialog(HistoryBooked.this, "Xóa Thành Công ");
				deleteRow();
			}else {
				JOptionPane.showMessageDialog(HistoryBooked.this, "Xóa Không Thành Công ");
			}
			
		}
//			JOptionPane.showMessageDialog(HistoryBooked.this, "Xóa Không Thành Công ");
		
	}
	
	public void deleteRow() {
		tableModel.removeRow(table.getSelectedRow());
	}

}
