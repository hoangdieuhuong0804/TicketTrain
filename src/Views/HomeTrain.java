package Views;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DB_Connection.StatementMySQL;
import Model.InfoTrain;
import Model.StatusTickets;
import Model.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HomeTrain extends JFrame {

	private JPanel contentPane;
	static Socket socketInfo;
	static Socket socketTrain;
	static String username;
	JLabel lb_clock;
	private JTable table;
	private JTable table_booked;
	private JScrollPane scrollPane_1;
	private JLabel lb_detail_booked;
	private JButton btn_list_train;
	private DefaultTableModel tableModel2;
	private DefaultTableModel tableModelStatusTickets;
    String value;
    private JMenu mnTroGiup;
    private JMenuItem mntmLienHe;
    private JMenuItem mntmThongTin;
    private JMenu mnNewMenu;
    private JMenuItem mntmBieuDoCot;
    private JMenuItem mntmBieuDoTron;
    private JMenuItem mntmQuayLai;
    private JMenu mnNewMenu_1;
    private JMenuItem mntmLichSu;
    private JMenuItem mntmDatTruoc;
    private JMenuItem mntmCapNhat;
    private JMenuItem mntmDangXuat;
    static int check = 0;
    static int serverPort1 = 8001;
	static int serverPort2 = 8002;
	static String ip = "127.0.0.1";
	static InetAddress serverAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeTrain frame = new HomeTrain(socketInfo,username);
					runSocket();
					frame.setVisible(true);
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param clientSocket
	 * @param user 
	 */
	public HomeTrain(Socket clientSocket, String user) {
		check = 0;
		this.username = user;
		this.socketInfo = clientSocket;
		runSocket();
		if(check != 0) {
			dispose();
			
		}else {
			setTitle("Home Train Tickets");

			URL url = HomeTrain.class.getResource("/Icon/HomeTrain.png");
			Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
			this.setIconImage((java.awt.Image) img);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(250, 20, 1050, 750);
			
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBackground(SystemColor.inactiveCaptionBorder);
			setJMenuBar(menuBar);
			
			JMenu mnMenu = new JMenu("Menu");
			mnMenu.setBackground(Color.WHITE);
			mnMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
			mnMenu.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/menu.png")));
			menuBar.add(mnMenu);
			
			mnNewMenu = new JMenu("Biểu đồ thống kê");
			mnNewMenu.setBackground(Color.WHITE);
			mnMenu.add(mnNewMenu);
			
			mntmBieuDoCot = new JMenuItem("Biểu đồ cột");
			mntmBieuDoCot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new ColumnChart<Image>(socketTrain);
				}

			});
			mntmBieuDoCot.setBackground(Color.WHITE);
			mnNewMenu.add(mntmBieuDoCot);
			
			mntmBieuDoTron = new JMenuItem("Biểu đồ tròn");
			mntmBieuDoTron.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new PieChart(socketTrain);
				}

			});
			mntmBieuDoTron.setBackground(Color.WHITE);
			mnNewMenu.add(mntmBieuDoTron);
			
			mntmQuayLai = new JMenuItem("Quay lại");
			mntmQuayLai.setBackground(Color.WHITE);
			mntmQuayLai.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
					new HomePage(clientSocket, user);
				}
				
			});
			mnMenu.add(mntmQuayLai);
			
			JMenu mnTaiKhoan = new JMenu("Tài Khoản");
			mnTaiKhoan.setBackground(Color.WHITE);
			mnTaiKhoan.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/man.png")));
			mnTaiKhoan.setFont(new Font("Segoe UI", Font.BOLD, 14));
			menuBar.add(mnTaiKhoan);
			
			mnNewMenu_1 = new JMenu("Hồ sơ người dùng");
			mnNewMenu_1.setBackground(Color.WHITE);
			mnTaiKhoan.add(mnNewMenu_1);
			
			mntmLichSu = new JMenuItem("Lịch sử đặt vé");
			mntmLichSu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new HistoryBooked(username,socketInfo,socketTrain);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			mntmLichSu.setBackground(Color.WHITE);
			mnNewMenu_1.add(mntmLichSu);
			
			mntmDatTruoc = new JMenuItem("Vé đặt trước");
			mntmDatTruoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new TicketsBooked(username,socketInfo,socketTrain);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			mntmDatTruoc.setBackground(Color.WHITE);
			mnNewMenu_1.add(mntmDatTruoc);
			
			mntmCapNhat = new JMenuItem("Cập nhật thông người dùng");
			mntmCapNhat.setBackground(Color.WHITE);
			mnTaiKhoan.add(mntmCapNhat);
			
			mntmDangXuat = new JMenuItem("Đăng xuất");
			mntmDangXuat.setBackground(Color.WHITE);
			mntmDangXuat.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
					new Login();
				}
				
			});
			mnTaiKhoan.add(mntmDangXuat);
			
			mnTroGiup = new JMenu("Trợ Giúp");
			mnTroGiup.setFont(new Font("Segoe UI", Font.BOLD, 14));
			mnTroGiup.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/help2.png")));
			menuBar.add(mnTroGiup);
			
			mntmLienHe = new JMenuItem("Liên hệ quản lí");
			mntmLienHe.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						new LoginChat(username);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			mntmLienHe.setBackground(Color.WHITE);
			mnTroGiup.add(mntmLienHe);
			
			mntmThongTin = new JMenuItem("Thông tin khác");
			mntmThongTin.setBackground(Color.WHITE);
			mntmThongTin.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(HomeTrain.this,"Mọi Thông Tin Khác Vui lòng Truy Cập: https://duongsatvietnam.vn/");
					try {
						new LoginChat(username);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			mnTroGiup.add(mntmThongTin);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setBackground(SystemColor.inactiveCaptionBorder);
			contentPane.setLayout(null);
			
		    lb_clock = new JLabel("Time");
			lb_clock.setFont(new Font("Tahoma", Font.BOLD, 14));
			lb_clock.setBounds(814, 26, 212, 35);
			contentPane.add(lb_clock);
			
			JLabel lblNewLabel = new JLabel("Lịch Trình Đường Sắt Việt Nam");
			lblNewLabel.setForeground(Color.RED);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
			lblNewLabel.setBounds(325, 26, 379, 35);
			contentPane.add(lblNewLabel);
			
			
			table = new JTable();
			table.setFont(new Font("Tahoma", Font.PLAIN, 12));
			table.setBackground(Color.WHITE);
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"T\u00EAn Chuy\u1EBFn T\u00E0u", "Ga B\u1EAFt \u0110\u1EA7u", "Ga K\u1EBFt Th\u00FAc", "Th\u1EDDi Gian R\u1EDDi Ga", "Th\u1EDDi Gian D\u1EF1 Ki\u1EBFn"
				}
			));
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String data = nameTrain();
					String sql = "SELECT * FROM `infotrain` WHERE `nameTrain` = '"+data+"'";
					String mash = "";
					lb_detail_booked.setText("Thông Tin Chuyến Tàu: "+data);
					
					
	            	StatementMySQL statementMySQL = new StatementMySQL();
	            	List<InfoTrain> infoTrain = new ArrayList<InfoTrain>();
	        		infoTrain = statementMySQL.LoadDataInfoTrain(sql);
	        		for(int i = 0; i < infoTrain.size();i++) {
	        			mash = infoTrain.get(i).getMash();
	        		}
	        		
//	        		System.out.println(mash);
	        		
	        		String sql2 = "SELECT * FROM `statustickest` WHERE `mash` = '"+mash+"'";
	        		int totalgn = 0;
	        		int bookedgn = 0;
	        		int remaininggn = 0;
	        		int totalgm = 0;
	        		int bookedgm = 0;
	        		int remaininggm = 0;
	        		int totalgc = 0;
	        		int bookedgc = 0;
	        		int remaininggc = 0;
	        		StatementMySQL statementMySQ2 = new StatementMySQL();
	        		List<StatusTickets> statusTickets = new ArrayList<StatusTickets>();
	        		statusTickets = statementMySQ2.LoadDataStatusTickets(sql2);
//	        		System.out.println(statusTickets.size());
	        		for(int i = 0; i<statusTickets.size();i++) {
//	        			System.out.println(statusTickets.get(i).getIdtype());
	        			if((statusTickets.get(i).getIdtype()).equals("gn")) {
	        				totalgn = statusTickets.get(i).getTotaltickets();
	        				bookedgn = statusTickets.get(i).getBooked();
	        				remaininggn =  totalgn - bookedgn;
	        			}else if((statusTickets.get(i).getIdtype()).equals("gm")) {
	        				totalgm = statusTickets.get(i).getTotaltickets();
	        				bookedgm = statusTickets.get(i).getBooked();
	        				remaininggm =  totalgm - bookedgm;
	        			}else {
	        				totalgc = statusTickets.get(i).getTotaltickets();
	        				bookedgc = statusTickets.get(i).getBooked();
	        				remaininggc =  totalgc - bookedgc;
	        			}
//	        			System.out.println(remaininggn + "- " +remaininggm + " - "+ remaininggc );
	        		}
	        		tableModelStatusTickets.setRowCount(0);
	        		tableModelStatusTickets.addRow(new Object[] {totalgn,remaininggn,totalgm,remaininggm,totalgc,remaininggc});
	        		
					
					
					
				}
			});
			
			
			table_booked = new JTable();
			table_booked.setFont(new Font("Tahoma", Font.PLAIN, 12));
			table_booked.setBackground(Color.WHITE);
			table_booked.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Vé Giường Nằm", "Số Vé Còn Lại", "Vé Ghế Mềm", "Số Vé Còn Lại", "Vé Ghế Cứng","Số Vé Còn Lại"
				}
			));
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(37, 95, 973, 330);
			contentPane.add(scrollPane);
			
			scrollPane_1 = new JScrollPane(table_booked);
			scrollPane_1.setBounds(411, 531, 567, 39);
			contentPane.add(scrollPane_1);
			
			lb_detail_booked = new JLabel("Thông Tin Chuyến Tàu");
			lb_detail_booked.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
			lb_detail_booked.setBounds(525, 487, 352, 24);
			contentPane.add(lb_detail_booked);
			
			JButton btn_book = new JButton("Tiến Hành Đặt Vé ");
			btn_book.setBackground(Color.WHITE);
			btn_book.setFont(new Font("Tahoma", Font.BOLD, 16));
			btn_book.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/booktickets.png")));
			btn_book.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					new BookTickets(socketInfo,socketTrain,user);
				}

			});
			
			btn_book.setBounds(95, 577, 263, 67);
			
			contentPane.add(btn_book);
			
			btn_list_train = new JButton("Danh Sách Tàu");
			btn_list_train.setBackground(Color.WHITE);
			btn_list_train.setForeground(Color.BLACK);
			btn_list_train.setFont(new Font("Tahoma", Font.BOLD, 16));
			btn_list_train.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/listtrain.png")));
			btn_list_train.setBounds(95, 477, 263, 67);
			contentPane.add(btn_list_train);
			
			tableModel2 = (DefaultTableModel) table.getModel();
			tableModelStatusTickets = (DefaultTableModel) table_booked.getModel();
			clock();
			dataInfoRoute();
			setVisible(true);
		}
		
	}

	private static void runSocket(){
		// TODO Auto-generated method stub
		try {
			serverAddress = InetAddress.getByName(ip);
//			int serverPort = 8002;
			socketTrain = new Socket(serverAddress, serverPort2);
			
//			username = "minh21kit";
		} catch (UnknownHostException e) {
//			e.printStackTrace();
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Xin lỗi hiện nay server gặp trục trặc, vui lòng thử lại sau","Lỗi Máy Chủ",0, new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/server_error.png"))) );
				check++;
				new HomePage(socketInfo,username);
			}
			
		} catch (IOException e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"Xin lỗi hiện nay server gặp trục trặc, vui lòng thử lại sau","Lỗi Máy Chủ",0, new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/server_error.png"))) );
				check++;
				new HomePage(socketInfo,username);
			}
			
		}

	}
	
	public void clock() {
	    Thread clock = new Thread() {
	        public void run() {
	            try {
	                while (true) {
	                    Calendar cal = new GregorianCalendar();
	                    int second = cal.get(Calendar.SECOND);
	                    int minute = cal.get(Calendar.MINUTE);
	                    int hour = cal.get(Calendar.HOUR_OF_DAY);
	                    int day = cal.get(Calendar.DAY_OF_MONTH);
	                    int month = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
	                    int year = cal.get(Calendar.YEAR);
	                    

	                   String time= day+"/"+month+"/"+year+" - "+hour + ":" + minute + ":" + second;
	                   lb_clock.setText(time);
	                    sleep(1000);
	                }
	            } catch (Exception e) {
//	                e.printStackTrace();
	            }
	        }
	        };
	        clock.start(); 
	}
	
	private void dataInfoRoute() {
		Thread datalist = new Thread() {
			public void run() {
	            try {
	                while (true) {
	                	String sql = "SELECT * FROM `infotrain`";
	                	StatementMySQL statementMySQL = new StatementMySQL();
	                	List<InfoTrain> infoTrain = new ArrayList<InfoTrain>();
	            		infoTrain = statementMySQL.LoadDataInfoTrain(sql);
	            		
//	            		System.out.println(infoTrain.toString() + " --- ");
//	            		System.out.println(infoTrain.size());
	            		
	            		for (int i = 0; i < infoTrain.size(); i++) {
	            			tableModel2.setRowCount(i);
	            			tableModel2.addRow(new Object[] { infoTrain.get(i).getNameTrain(),infoTrain.get(i).getLocationStartTrain(),infoTrain.get(i).getLocationEndTrain(),
	            					infoTrain.get(i).getTimeStartTrain(),infoTrain.get(i).getExpectedendTime()
	                   
	            			});

	            		}
	                	sleep(50000);
	                }
	            }catch(Exception e) {
//	                e.printStackTrace();
	            }
		}
	 };
	 datalist.start();
	}
	
	public String nameTrain() {
		int column = 0;
		int row = table.getSelectedRow();
		value = table.getModel().getValueAt(row, column).toString();
		return value;
	}
}
