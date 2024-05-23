package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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

import com.mysql.cj.xdevapi.Client;

import DB_Connection.StatementMySQL;
import Model.ClientBookTickets;

public class TicketsBooked extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static Socket socketInfo;
	static Socket socketTrain;
	static String username;

	private JTextField tf_timkiem;
	private JTable table;
	private List<ClientBookTickets> tablelist = new ArrayList<ClientBookTickets>();
	DefaultTableModel tableModel;
	String status = "false";
	private JLabel lb_nameTrain;
	private JLabel lb_timeTrain;
	private JLabel lb_seats;
	private JLabel lb_type;
	private JLabel lb_price;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TicketsBooked dialog = new TicketsBooked(username, socketInfo, socketTrain);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param socketTrain
	 * @param socketInfo
	 * @param username
	 * @throws IOException
	 */
	public TicketsBooked(String username, Socket socketInfo, Socket socketTrain) throws IOException {
		this.username = username;
		this.socketInfo = socketInfo;
		this.socketTrain = socketTrain;
		setTitle("Lịch Trình Đã Đặt");

		URL url = TicketsBooked.class.getResource("/Icon/listproduct.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);

		setBounds(270, 155, 1004, 465);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			tf_timkiem = new JTextField();
			tf_timkiem.setFont(new Font("Tahoma", Font.PLAIN, 15));
			tf_timkiem.setBounds(327, 26, 175, 32);
			contentPanel.add(tf_timkiem);
			tf_timkiem.setColumns(10);
		}

		JButton bt_timkiem = new JButton("Tìm Kiếm");
		bt_timkiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				Search();
//				showAll();
				searchID();

			}

		});
		bt_timkiem.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(TicketsBooked.class.getResource("/Icon/search1.png"))));
		bt_timkiem.setBackground(Color.GRAY);
		bt_timkiem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bt_timkiem.setBounds(535, 25, 142, 32);
		contentPanel.add(bt_timkiem);

		JLabel lb_danhsach = new JLabel("Lịch Trình Đã Đặt");
		lb_danhsach.setForeground(Color.RED);
		lb_danhsach.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_danhsach.setBounds(201, 87, 190, 32);
		contentPanel.add(lb_danhsach);

		table = new JTable();
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//			}
//
//		});

		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID Vé", "Ga Bắt Đầu", "Ga Kết Thúc", "Ngày Đi" }));

		JScrollPane scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, 0).toString();
				ClientBookTickets clientBookTickets = new ClientBookTickets();
				clientBookTickets = detailData(value);

				lb_nameTrain.setText(loadNameTrain(clientBookTickets.getMash()));
				lb_timeTrain.setText(clientBookTickets.getTimeStart());
				lb_seats.setText(clientBookTickets.getSeats());
				lb_type.setText(loadType(clientBookTickets.getIdtype()));
				lb_price.setText(Integer.toString(clientBookTickets.getPriceTickets()));

			}
		});
		scrollPane.setBounds(42, 129, 511, 236);
		contentPanel.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Thông Tin Chi Tiết");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(689, 90, 216, 27);
		contentPanel.add(lblNewLabel);

		JLabel lb_idtickets = new JLabel("Chuyến Tàu:");
		lb_idtickets.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_idtickets.setBounds(585, 150, 147, 22);
		contentPanel.add(lb_idtickets);

		JLabel lblNewLabel_1 = new JLabel("Chỗ: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(585, 236, 85, 22);
		contentPanel.add(lblNewLabel_1);

		JLabel lb_giatien = new JLabel("Loại Ghế:");
		lb_giatien.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_giatien.setBounds(767, 236, 91, 22);
		contentPanel.add(lb_giatien);

		JButton bt_xoa = new JButton("Xóa");
//		bt_xoa.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				xoaHang();
//				xoaDon();
//			}
//		});
		bt_xoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteData();

			}

		});
		bt_xoa.setBackground(Color.GRAY);
		bt_xoa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bt_xoa.setBounds(865, 325, 85, 32);
		contentPanel.add(bt_xoa);

		JButton bt_reset = new JButton("Menu");
		bt_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				loadData();
//				showAll();
				try {
					loadData(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tf_timkiem.setText("");
				lb_nameTrain.setText("");
				lb_timeTrain.setText("");
				lb_seats.setText("");
				lb_type.setText("");
				lb_price.setText("");
			}

		});
		bt_reset.setBackground(Color.GRAY);
		bt_reset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bt_reset.setBounds(754, 325, 85, 32);
		contentPanel.add(bt_reset);

		JSeparator separator = new JSeparator();
		separator.setBounds(26, 367, 937, 8);
		contentPanel.add(separator);

		JLabel lb_giatien_1 = new JLabel("Giá Vé: ");
		lb_giatien_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_giatien_1.setBounds(586, 279, 85, 22);
		contentPanel.add(lb_giatien_1);

		JLabel lblNewLabel_1_1 = new JLabel("Thời Gian Rời Ga: ");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(585, 192, 147, 22);
		contentPanel.add(lblNewLabel_1_1);

		lb_nameTrain = new JLabel("");
		lb_nameTrain.setForeground(Color.BLACK);
		lb_nameTrain.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_nameTrain.setBounds(740, 150, 223, 21);
		contentPanel.add(lb_nameTrain);

		lb_timeTrain = new JLabel("");
		lb_timeTrain.setForeground(Color.BLACK);
		lb_timeTrain.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_timeTrain.setBounds(742, 193, 223, 21);
		contentPanel.add(lb_timeTrain);

		lb_seats = new JLabel("");
		lb_seats.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_seats.setBounds(643, 236, 114, 21);
		contentPanel.add(lb_seats);

		lb_type = new JLabel("");
		lb_type.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_type.setBounds(854, 237, 126, 21);
		contentPanel.add(lb_type);

		lb_price = new JLabel("");
		lb_price.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_price.setBounds(699, 280, 223, 21);
		contentPanel.add(lb_price);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton bt_quaylai = new JButton("Quay L\u1EA1i");
				bt_quaylai.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
//						DemoListProductCF.this.setVisible(false);
						dispose();
					}

				});
				bt_quaylai.setBackground(Color.WHITE);
				bt_quaylai.setFont(new Font("Tahoma", Font.PLAIN, 20));
				bt_quaylai.setActionCommand("Cancel");
				buttonPane.add(bt_quaylai);

				JSeparator separator_1 = new JSeparator();
				buttonPane.add(separator_1);
			}
		}
		tableModel = (DefaultTableModel) table.getModel();
//		loadData();
//		showAll();
		loadData(username);
		setVisible(true);
	}

	private void loadData(String username) throws IOException {

		Thread datalist = new Thread() {
			public void run() {
				try {
					tablelist.clear();
					StatementMySQL statementMySQL = new StatementMySQL();
					tablelist = statementMySQL.dataUser(username, status);

					for (int i = 0; i < tablelist.size(); i++) {
						tableModel.setRowCount(i);
						tableModel.addRow(new Object[] { tablelist.get(i).getIdtickets(),
								tablelist.get(i).getLocationStartClient(), tablelist.get(i).getLocationEndClient(),
								tablelist.get(i).getDay() });

					}
//	                	sleep(50000);
				} catch (Exception e) {
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

	private String loadType(String type) {
		StatementMySQL statementMySQL = new StatementMySQL();
		return statementMySQL.getTypeTrain(type);
	}

	private void searchID() {
		tablelist.clear();
		StatementMySQL statementMySQL = new StatementMySQL();
		tablelist = statementMySQL.dataUserByID(tf_timkiem.getText().toUpperCase(), username, status);

		if (tablelist.size() > 0) {
			for (int i = 0; i < tablelist.size(); i++) {
				tableModel.setRowCount(i);
				tableModel.addRow(
						new Object[] { tablelist.get(i).getIdtickets(), tablelist.get(i).getLocationStartClient(),
								tablelist.get(i).getLocationEndClient(), tablelist.get(i).getDay() });

			}
		} else {
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}

	}

	private ClientBookTickets detailData(String idtickets) {
		List<ClientBookTickets> resultClientBookTickets = new ArrayList<ClientBookTickets>();
		ClientBookTickets clientBookTickets = new ClientBookTickets();
		StatementMySQL statementMySQL = new StatementMySQL();
		resultClientBookTickets = statementMySQL.dataUserByID(idtickets, username, status);
		clientBookTickets = resultClientBookTickets.get(0);
		return clientBookTickets;
	}

	private void deleteData() {
		int row = table.getSelectedRow();
		String value = table.getModel().getValueAt(row, 0).toString();
//		System.out.println("Row: " +row);
//		System.out.println("Value: " +value);
		int n = JOptionPane.showConfirmDialog(TicketsBooked.this, "Bạn Có Chắc Chắn Muốn Vé Tàu Này?", "Lịch Trình",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			StatementMySQL statementMySQL = new StatementMySQL();
			int num = statementMySQL.deleteByID(value);
			if (num != 0) {
				JOptionPane.showMessageDialog(TicketsBooked.this, "Xóa Thành Công ");
				deleteRow();
			} else {
				JOptionPane.showMessageDialog(TicketsBooked.this, "Xóa Không Thành Công ");
			}

		}
//			JOptionPane.showMessageDialog(HistoryBooked.this, "Xóa Không Thành Công ");

	}

	public void deleteRow() {
		tableModel.removeRow(table.getSelectedRow());
	}
}
