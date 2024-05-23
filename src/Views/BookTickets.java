package Views;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import DB_Connection.StatementMySQL;
import Model.ClientBookTickets;
import Model.InfoTrain;
import Model.StatusTickets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class BookTickets extends JFrame {

	static Socket socketInfo;
	static Socket socketTrain;
	static String trainStation[] = { " ", "Ga Sapa", "Ga Yên Bái", "Ga Lào Cai", "Ga Hà Khẩu", "Ga Hà Nội",
			"Ga Nam Định", "Ga Ninh Bình", "Ga Vinh", "Ga Đồng Hới", "Ga Đông Hà", "Ga Huế", "Ga Đà Nẵng", "Ga Tam Kỳ",
			"Ga Quảng Ngãi", "Ga Diêu Trì", "Ga Tuy Hòa", "Ga Nha Trang", "Ga Tháp Chàm", "Ga Phan Rang",
			"Ga Phan Thiết", "Ga Bình Thuận", "Ga Sài Gòn" };
	static int timeDownStation[] = { 0, 0, 180, 405, 975, 0, 97, 129, 309, 568, 666, 736, 894, 979, 1039, 1194, 1289,
			1399, 1490, 1580, 1820, 1835, 2090 };
	static int timeUpStation[] = { 0, 3960, 3450, 3230, 2660, 2090, 1993, 1961, 1781, 1522, 1424, 1354, 1196, 1111,
			1051, 896, 801, 691, 600, 510, 270, 255, 0 };
	private JPanel contentPane;
	private JDateChooser dtCBTD;
	private ButtonGroup bt_group;
	private JTable table_info_train;
	private JTable table_info_tickets;
	private JRadioButton rbtn_gn;
	private JRadioButton rbtn_gc;
	private JRadioButton rbtn_gm;
	private DefaultTableModel tableModelInfoTrain;
	private DefaultTableModel tableModelStatusTickets;
	BufferedReader in;
	PrintWriter out;
	String value;
	static String username;
	String idTickets;
	String mashClient;
	String locationStartClient;
	String locationEndClient;
	String dayClient;
	String idtype;
	String timeStartClient;
	String expectedendTime;
	String seats;
	int priceTickets;
	String direction;
	static List<InfoTrain> infoTrainClient = new ArrayList<InfoTrain>();
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
					BookTickets frame = new BookTickets(socketInfo, socketTrain, username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param user
	 */
	public BookTickets(Socket socketInfo, Socket socketTrain, String user) {
		this.socketInfo = socketInfo;
		this.socketTrain = socketTrain;
		this.username = user;
		setTitle("Đặt Vé");

		URL url = BookTickets.class.getResource("/Icon/booktickets.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 40, 1000, 730);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setVisible(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hãng Vé Đường Sắt Việt Nam");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(340, 22, 346, 27);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(61, 69, 865, 2);
		contentPane.add(separator);

		JLabel lblNewLabel_1 = new JLabel("Ga Khởi Điểm:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(76, 97, 133, 27);
		contentPane.add(lblNewLabel_1);

		JComboBox comboBoxStart = new JComboBox();
		comboBoxStart.setBackground(SystemColor.controlLtHighlight);
		comboBoxStart.setBounds(219, 96, 180, 28);
		comboBoxStart.setModel(new DefaultComboBoxModel(new String[] { " ", "Ga Sapa", "Ga Yên Bái", "Ga Lào Cai",
				"Ga Hà Khẩu", "Ga Hà Nội", "Ga Nam Định", "Ga Ninh Bình", "Ga Vinh", "Ga Đồng Hới", "Ga Đông Hà",
				"Ga Huế", "Ga Đà Nẵng", "Ga Tam Kỳ", "Ga Quảng Ngãi", "Ga Diêu Trì", "Ga Tuy Hòa", "Ga Nha Trang",
				"Ga Tháp Chàm", "Ga Phan Rang", "Ga Phan Thiết", "Ga Bình Thuận", "Ga Sài Gòn" }));
		contentPane.add(comboBoxStart);

		JLabel lblNewLabel_2 = new JLabel("Ga Đến: ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(629, 97, 91, 27);
		contentPane.add(lblNewLabel_2);

		JComboBox comboBoxEnd = new JComboBox();
		comboBoxEnd.setBackground(SystemColor.text);
		comboBoxEnd.setModel(new DefaultComboBoxModel(new String[] { " ", "Ga Sapa", "Ga Yên Bái", "Ga Lào Cai",
				"Ga Hà Khẩu", "Ga Hà Nội", "Ga Nam Định", "Ga Ninh Bình", "Ga Vinh", "Ga Đồng Hới", "Ga Đông Hà",
				"Ga Huế", "Ga Đà Nẵng", "Ga Tam Kỳ", "Ga Quảng Ngãi", "Ga Diêu Trì", "Ga Tuy Hòa", "Ga Nha Trang",
				"Ga Tháp Chàm", "Ga Phan Rang", "Ga Phan Thiết", "Ga Bình Thuận", "Ga Sài Gòn" }));
		comboBoxEnd.setBounds(721, 99, 180, 27);
		contentPane.add(comboBoxEnd);

		JLabel lblNewLabel_3 = new JLabel("Ngày Đi: ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(76, 160, 133, 27);
		contentPane.add(lblNewLabel_3);

		dtCBTD = new JDateChooser();
		dtCBTD.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 18));
		dtCBTD.getCalendarButton().setBackground(Color.WHITE);
		dtCBTD.setBackground(Color.WHITE);
		dtCBTD.setBounds(218, 157, 181, 30);
		contentPane.add(dtCBTD);

		JButton btn_search_train = new JButton("Tra Cứu");
		btn_search_train.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = new GregorianCalendar();
				int checkTime = 0;
				int checkTimeForSql = 0;
				String[] dataTime = null;
//				String[] mash = null;
				int second = cal.get(Calendar.SECOND);
				int minute = cal.get(Calendar.MINUTE);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				String submitTime = hour + ":" + minute + ":" + second;

				int dayyy = cal.get(Calendar.DAY_OF_MONTH);
				int month = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
				int year = cal.get(Calendar.YEAR);
				String now = year + "-" + month + "-" + dayyy;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String day = "";
				if (dtCBTD.getDate() != null) {
					day = df.format(dtCBTD.getDate());
					dayClient = df.format(dtCBTD.getDate());
					dataTime = day.split("-");
//					System.out.println(dataTime[0]+ " - " + dataTime[1]+ " - " +dataTime[2]);
				} else {
					dataTime = "0-0-0".split("-");
				}

				if ((Integer.parseInt(dataTime[0])) < year) {
					checkTime = 1;
				} else {
					if ((Integer.parseInt(dataTime[1])) < month) {
						checkTime = 1;
					} else if ((Integer.parseInt(dataTime[1])) == month) {
						if ((Integer.parseInt(dataTime[2])) < dayyy) {
							checkTime = 1;
						}
					}
				}
				String direction = "";
				String locationStart = (String) comboBoxStart.getItemAt(comboBoxStart.getSelectedIndex());
				String locationEnd = (String) comboBoxEnd.getItemAt(comboBoxEnd.getSelectedIndex());
				int indexLocationStart = indexLocation(locationStart);
				// Arrays.binarySearch(trainStation,locationStart );
				int indexLocationEnd = indexLocation(locationEnd);
				// Arrays.binarySearch(trainStation,locationEnd );
				if (indexLocationStart > indexLocationEnd) {
					direction = "up";
//					System.out.println("hihi");
				} else {
					direction = "down";
//					System.out.println("hihu");
				}
				String type = "";
				if (rbtn_gn.isSelected()) {
					type = "gn";
				} else if (rbtn_gm.isSelected()) {
					type = "gm";
				} else if (rbtn_gc.isSelected()) {
					type = "gc";
				}
				idtype = type;
				locationStartClient = locationStart;
				locationEndClient = locationEnd;
//				System.out.println("Start: " + locationStart + " - Index: " + indexLocationStart + " - End: "
//						+ locationEnd + " - Index: " + indexLocationEnd + " - Direction: " + direction + " - Time: "
//						+ day + " - Type: " + type);

				if (locationStart.equals(" ") || locationEnd.equals(" ") || day.equals("") || type.equals("")) {
					JOptionPane.showMessageDialog(BookTickets.this,
							"Chưa đầy đủ thông tin, Vui lòng kiểm tra lại dữ liệu");
				} else {
					if (locationStart.equals(locationEnd)) {
						JOptionPane.showMessageDialog(BookTickets.this,
								"Ga khởi hành và Ga đến trùng nhau, Vui lòng kiểm tra lại");
					} else if (checkTime == 1) {
						JOptionPane.showMessageDialog(BookTickets.this,
								"Thời gian không phù hợp, Vui lòng kiểm tra lại");
					} else {
						String sql = "";
						if (Integer.parseInt(dataTime[2]) != dayyy) {
							sql = "SELECT * FROM `infotrain` WHERE `direction` = '" + direction + "'";

						} else {
							sql = "SELECT * FROM `infotrain` WHERE `direction` = '" + direction
									+ "' AND `timeStartTrain` > '" + submitTime + "'";
						}
						// ============= ND ===============
//						String sql = "SELECT * FROM `infotrain` WHERE `direction` = '"+direction+"' AND `timeStartTrain` > '"+submitTime+"'";
//						String sql = "SELECT * FROM `infotrain` WHERE `direction` = '" + direction
//								+ "' AND `timeStartTrain` > '06:00:00'";
//						System.out.println(sql);
						StatementMySQL statementMySQL = new StatementMySQL();
						List<InfoTrain> infoTrain = new ArrayList<InfoTrain>();
						infoTrain = statementMySQL.LoadDataInfoTrain(sql);

//						System.out.println("======= Số Result: " + infoTrain.size() + " ========");

						if (infoTrain.size() != 0) {
							for (int i = 0; i < infoTrain.size(); i++) {
								if (direction.equals("down")) {
									int x = 0;
									int locationStartTrain = indexLocation(
											"Ga " + infoTrain.get(i).getLocationStartTrain());
									// Arrays.binarySearch(trainStation,"Ga
									// "+infoTrain.get(i).getLocationStartTrain());
									int locationEndTrain = indexLocation(
											"Ga " + infoTrain.get(i).getLocationEndTrain());
									// Arrays.binarySearch(trainStation,"Ga
									// "+infoTrain.get(i).getLocationEndTrain());
//									System.out.println("=============================");
//									System.out.println("StartClient: " + indexLocationStart + " - EndClient: "
//											+ indexLocationEnd + " - StartTrain: " + locationStartTrain
//											+ " - EndTrain: " + locationEndTrain);
									if (indexLocationStart >= locationStartTrain) {
										if (indexLocationEnd <= locationEndTrain) {
											String expectedendTime;
//		            						mash[x] = infoTrain.get(i).getMash();
//											System.out.println(infoTrain.get(i).toString());

											// Xử lí expectedendTime
											int startTime = timeDownStation[indexLocationStart];
											int endTime = timeDownStation[indexLocationEnd];
											int realtime = endTime - startTime;
//											System.out.println(realtime);
											float hoursTimeTrain = 1.0f * realtime / 60;
//											System.out.println("===> hoursTimeTrain ban đầu:" + hoursTimeTrain);
											if (hoursTimeTrain > 24) {
												float sumTime = hoursTimeTrain;
												float hoursTime = sumTime - 24;
												int usehoursTime = (int) hoursTime; // Lấy được giờ chẵn
//		            							System.out.println("===> Giờ Chẵn: "+usehoursTime + "Và giá trị hoursTime tính cả giờ lẻ: "+hoursTime);
												int minTime = (int) ((hoursTime - usehoursTime) * 60); // lấy số giờ dư
																										// => chuyển
																										// sang Phút
//												System.out.println("===> Min: " + minTime);

												expectedendTime = "1 Ngày " + usehoursTime + " Tiếng " + minTime
														+ " Phút";
											} else {
												float sumTime = hoursTimeTrain;
//												System.out.println("===> Sumtime ban đầu: " + sumTime);
												int usehoursTime = (int) sumTime;
//												System.out.println("===> Giờ chẵn: " + usehoursTime
//														+ "và giá trị sumTime:  " + sumTime);
												int minTime = (int) ((sumTime - usehoursTime) * 60);
												expectedendTime = usehoursTime + " Tiếng " + minTime + " Phút";
//												System.out.println("===> Min: " + minTime);
											}

											// Xử lí timeStartTrain
											int startTimeTrain = timeDownStation[locationStartTrain]; // lấy time bắt
																										// đầu tại địa
																										// điểm của tàu
											int sumMin = startTime - startTimeTrain; // Sum số phút
//											System.out.println("SumMIn ====: " + sumMin);
											float sumHours = 1.0f * sumMin / 60; // sum giờ
											int nHours = (int) sumHours; // + số giờ bắt đầu
											int nMin = (int) ((sumHours - nHours) * 60); // + số phút bắt đầu

											String[] timeStartOfTrain = infoTrain.get(i).getTimeStartTrain().split(":");
											int sumRealHours = Integer.parseInt(timeStartOfTrain[0]) + nHours;
											if (sumRealHours >= 24) {
												sumRealHours = sumRealHours - 24;
											}
											int sumRealMin = Integer.parseInt(timeStartOfTrain[1]) + nMin;
											if (sumRealMin >= 60) {
												sumRealHours += 1;
												sumRealMin = sumRealMin - 60;
											}
											String realTimeStart = null;
											if (sumRealHours < 10) {
												if (sumRealMin < 10) {
													realTimeStart = "0" + sumRealHours + ":0" + sumRealMin + ":00";
												} else {
													realTimeStart = "0" + sumRealHours + ":" + sumRealMin + ":00";
												}
											} else {
												if (sumRealMin < 10) {
													realTimeStart = sumRealHours + ":0" + sumRealMin + ":00";
												} else {
													realTimeStart = sumRealHours + ":" + sumRealMin + ":00";
												}
											}

											InfoTrain infotrainclient = new InfoTrain(infoTrain.get(i).getMash(),
													infoTrain.get(i).getNameTrain(), locationStart, locationEnd,
													realTimeStart, expectedendTime, direction);
											infoTrainClient.add(infotrainclient);
//											System.out.println("===> Time: " + expectedendTime);
											tableModelInfoTrain.setRowCount(x);
											tableModelInfoTrain.addRow(new Object[] { infoTrain.get(i).getNameTrain(),
													realTimeStart, expectedendTime });
											x++;
										}
									}

								} else if (direction.equals("up")) {
									int x = 0;
									int locationStartTrain = Arrays.binarySearch(trainStation,
											"Ga " + infoTrain.get(i).getLocationStartTrain());
									int locationEndTrain = Arrays.binarySearch(trainStation,
											"Ga " + infoTrain.get(i).getLocationEndTrain());
//									System.out.println("======= Giá Trị========");
//									System.out.println("StartClient: " + indexLocationStart + " - EndClient: "
//											+ indexLocationEnd + " - StartTrain: " + locationStartTrain
//											+ " - EndTrain: " + locationEndTrain);
									if (indexLocationStart <= locationStartTrain) {
										if (indexLocationEnd >= locationEndTrain) {
											String expectedendTime;
//		            						mash[x] = infoTrain.get(i).getMash();
//											System.out.println(infoTrain.get(i).toString());
											int startTime = timeUpStation[indexLocationStart];
											int endTime = timeUpStation[indexLocationEnd];
											int realtime = endTime - startTime;
											float hoursTimeTrain = 1.0f * realtime / 60;
											if (hoursTimeTrain > 24) {
												float sumTime = hoursTimeTrain;
												float hoursTime = sumTime - 24;
												int usehoursTime = (int) hoursTime; // Lấy được giờ chẵn
												int minTime = (int) ((hoursTime - usehoursTime) * 60); // lấy số giờ dư
																										// => chuyển
																										// sang Phút

												expectedendTime = "1 Ngày " + usehoursTime + " Tiếng " + minTime
														+ " Phút";
											} else {
												float sumTime = hoursTimeTrain;
												int usehoursTime = (int) sumTime;
												int minTime = (int) ((sumTime - usehoursTime) * 60);
												expectedendTime = usehoursTime + " Tiếng " + minTime + " Phút";
											}

											// Xử lí timeStartTrain
											int startTimeTrain = timeUpStation[locationStartTrain]; // lấy time bắt đầu
																									// tại địa điểm của
																									// tàu
											int sumMin = startTime - startTimeTrain; // Sum số phút
//											System.out.println("SumMIn ====: " + sumMin);
											float sumHours = 1.0f * sumMin / 60; // sum giờ
											int nHours = (int) sumHours; // + số giờ bắt đầu
											int nMin = (int) ((sumHours - nHours) * 60); // + số phút bắt đầu

											String[] timeStartOfTrain = infoTrain.get(i).getTimeStartTrain().split(":");
											int sumRealHours = Integer.parseInt(timeStartOfTrain[0]) + nHours;
											if (sumRealHours >= 24) {
												sumRealHours = sumRealHours - 24;
											}
											int sumRealMin = Integer.parseInt(timeStartOfTrain[1]) + nMin;
											if (sumRealMin >= 60) {
												sumRealHours += 1;
												sumRealMin = sumRealMin - 60;
											}
											String realTimeStart = null;
											if (sumRealHours < 10) {
												if (sumRealMin < 10) {
													realTimeStart = "0" + sumRealHours + ":0" + sumRealMin + ":00";
												} else {
													realTimeStart = "0" + sumRealHours + ":" + sumRealMin + ":00";
												}
											} else {
												if (sumRealMin < 10) {
													realTimeStart = sumRealHours + ":0" + sumRealMin + ":00";
												} else {
													realTimeStart = sumRealHours + ":" + sumRealMin + ":00";
												}
											}

//											System.out.println("===> Time: " + expectedendTime);
											InfoTrain infotrainclient = new InfoTrain(infoTrain.get(i).getMash(),
													infoTrain.get(i).getNameTrain(), locationStart, locationEnd,
													realTimeStart, expectedendTime, direction);
											infoTrainClient.add(infotrainclient);
											tableModelInfoTrain.setRowCount(x);
											tableModelInfoTrain.addRow(new Object[] { infoTrain.get(i).getNameTrain(),
													realTimeStart, expectedendTime });
											x++;
										}
									}
								}
							}
						} else {
							JOptionPane.showMessageDialog(BookTickets.this,
									"Không có chuyến tàu phù hợp, Vui lòng chọn lộ trình khác");
						}

					}
				}
			}
		});
		btn_search_train.setBackground(SystemColor.text);
		btn_search_train.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_search_train.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/search.png")));
		btn_search_train.setBounds(721, 264, 180, 44);
		contentPane.add(btn_search_train);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(61, 242, 865, 2);
		contentPane.add(separator_1);

		JLabel lblNewLabel_4 = new JLabel("Lịch Trình Tàu");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setBounds(204, 352, 138, 27);
		contentPane.add(lblNewLabel_4);

		JLabel lb_info_tickets = new JLabel("Thông Tin Chuyến Tàu");
		lb_info_tickets.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_info_tickets.setBounds(662, 354, 207, 23);
		contentPane.add(lb_info_tickets);

		table_info_train = new JTable();
		table_info_train.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table_info_train.setBackground(Color.WHITE);
		table_info_train.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Tên Chuyến Tàu", "Giờ Khởi Hành", "Thời Dự Kiến", }));
		table_info_train.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String data = nameTrain();
				String sql = "SELECT * FROM `infotrain` WHERE `nameTrain` = '" + data + "'";
				String mash = "";
				lb_info_tickets.setText("Thông Tin Chuyến Tàu: " + data);

				StatementMySQL statementMySQL = new StatementMySQL();
				List<InfoTrain> infoTrain = new ArrayList<InfoTrain>();
				infoTrain = statementMySQL.LoadDataInfoTrain(sql);
				for (int i = 0; i < infoTrain.size(); i++) {
					mash = infoTrain.get(i).getMash();
				}

				mashClient = mash;
				String sql2 = "SELECT * FROM `statustickest` WHERE `mash` = '" + mash + "'";

				for (int i = 0; i < infoTrainClient.size(); i++) {
					if (infoTrainClient.get(i).getMash().equals(mash)) {
						timeStartClient = infoTrainClient.get(i).getTimeStartTrain();
						expectedendTime = infoTrainClient.get(i).getExpectedendTime();
						direction = infoTrainClient.get(i).getDirection();
					}
				}

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
//        		System.out.println(statusTickets.size());
				for (int i = 0; i < statusTickets.size(); i++) {
//        			System.out.println(statusTickets.get(i).getIdtype());
					if ((statusTickets.get(i).getIdtype()).equals("gn")) {
						totalgn = statusTickets.get(i).getTotaltickets();
						bookedgn = statusTickets.get(i).getBooked();
						remaininggn = totalgn - bookedgn;
					} else if ((statusTickets.get(i).getIdtype()).equals("gm")) {
						totalgm = statusTickets.get(i).getTotaltickets();
						bookedgm = statusTickets.get(i).getBooked();
						remaininggm = totalgm - bookedgm;
					} else {
						totalgc = statusTickets.get(i).getTotaltickets();
						bookedgc = statusTickets.get(i).getBooked();
						remaininggc = totalgc - bookedgc;
					}
				}
				tableModelStatusTickets.setRowCount(0);
				tableModelStatusTickets.addRow(new Object[] { remaininggn, remaininggm, remaininggc });

			}
		});
		JScrollPane scrollPane = new JScrollPane(table_info_train);
		scrollPane.setBounds(30, 406, 469, 140);
		contentPane.add(scrollPane);

		JLabel lblNewLabel_6 = new JLabel("Loại Ghế: ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_6.setBounds(629, 160, 91, 22);
		contentPane.add(lblNewLabel_6);

		rbtn_gm = new JRadioButton("Ghế Mềm");
		rbtn_gm.setBackground(SystemColor.info);
		rbtn_gm.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rbtn_gm.setBounds(721, 165, 103, 21);
		contentPane.add(rbtn_gm);

		rbtn_gc = new JRadioButton("Ghế Cứng");
		rbtn_gc.setBackground(SystemColor.info);
		rbtn_gc.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rbtn_gc.setBounds(834, 165, 103, 21);
		contentPane.add(rbtn_gc);

		rbtn_gn = new JRadioButton("Giường Nằm");
		rbtn_gn.setBackground(SystemColor.info);
		rbtn_gn.setFont(new Font("Tahoma", Font.ITALIC, 14));
		rbtn_gn.setBounds(770, 197, 121, 21);
		contentPane.add(rbtn_gn);

		bt_group = new ButtonGroup();
		bt_group.add(rbtn_gc);
		bt_group.add(rbtn_gm);
		bt_group.add(rbtn_gn);

		table_info_tickets = new JTable();
		table_info_tickets.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table_info_tickets.setBackground(Color.WHITE);
		table_info_tickets.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Giường Nằm", "Ghế Mềm", "Ghế Cứng", }));

		JScrollPane scrollPane_1 = new JScrollPane(table_info_tickets);
		scrollPane_1.setBounds(581, 404, 371, 142);
		contentPane.add(scrollPane_1);

		JButton btn_book = new JButton("Đặt Vé");
		btn_book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String checkklocationStart = (String) comboBoxStart.getItemAt(comboBoxStart.getSelectedIndex());
				String checkklocationEnd = (String) comboBoxEnd.getItemAt(comboBoxEnd.getSelectedIndex());

				String checktype = "";
				if (rbtn_gn.isSelected()) {
					checktype = "gn";
				} else if (rbtn_gm.isSelected()) {
					checktype = "gm";
				} else if (rbtn_gc.isSelected()) {
					checktype = "gc";
				}

				if (checkklocationStart.equals(" ") || checkklocationEnd.equals(" ") || dtCBTD.getDate() == null
						|| checktype.equals("")) {
					JOptionPane.showMessageDialog(BookTickets.this,
							"Chưa đầy đủ thông tin, Vui lòng kiểm tra lại dữ liệu");
				} else {
					// xử lí id tickets
					Random rand = new Random();
					int rand_int = rand.nextInt(10000);
					idTickets = "SH" + rand_int;

					// xử lí chỗ ngồi
					int indexSeats = 0;
					indexSeats = getIndexSeats(mashClient, idtype, dayClient);
					String seats = "";
					if (indexSeats > 10) {
						seats = "A-" + indexSeats;
					} else {
						seats = "A-0" + indexSeats;
					}
					// xử lí giá vé
					int priceTicketsClient = 0;
					try {
						priceTicketsClient = getPriceTickets(idtype);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (direction.equals("down")) {
						int minTime = timeDownStation[indexLocation(locationEndClient)]
								- timeDownStation[indexLocation(locationStartClient)];
						if (idtype.equals("gn")) {
							priceTicketsClient = priceTicketsClient + minTime * 672;
						} else if (idtype.equals("gm")) {
							priceTicketsClient = priceTicketsClient + minTime * 689;
						} else if (idtype.equals("gc")) {
							priceTicketsClient = priceTicketsClient + minTime * 702;
						}
					} else if (direction.equals("up")) {
						int minTime = timeUpStation[indexLocation(locationEndClient)]
								- timeUpStation[indexLocation(locationStartClient)];
						if (idtype.equals("gn")) {
							priceTicketsClient = priceTicketsClient + minTime * 672;
						} else if (idtype.equals("gm")) {
							priceTicketsClient = priceTicketsClient + minTime * 689;
						} else if (idtype.equals("gc")) {
							priceTicketsClient = priceTicketsClient + minTime * 702;
						}
					}
//					System.out.println(priceTicketsClient);
					float changePrice = 1.0f * priceTicketsClient / 1000;
//					System.out.println(changePrice);
					priceTicketsClient = (int) Math.ceil(changePrice) * 1000;
//					System.out.println(priceTicketsClient);
					// 673966 - 695700

//					priceTickets =  Integer.toString(priceTicketsClient);
					priceTickets = priceTicketsClient;

					System.out.println(" Username: " + user + "\n IDtickets: " + idTickets + "\n MaSH: " + mashClient
							+ "\n IdType: " + idtype + "\n LocationStart: " + locationStartClient + "\n LocationEnd: "
							+ locationEndClient + "\n Seats: " + seats + "\n TimeStart: " + timeStartClient
							+ "\n ExpectedendTime: " + expectedendTime + "\n DayClient: " + dayClient
							+ "\n PriceTickets: " + priceTickets);

					ClientBookTickets clientBookTickets = new ClientBookTickets(user, idTickets, mashClient, idtype,
							locationStartClient, locationEndClient, seats, timeStartClient, expectedendTime, dayClient,
							priceTickets, "false");
					new ShowDetalTicket(socketInfo, socketTrain, clientBookTickets);
				}
			}

		});
		btn_book.setBackground(SystemColor.inactiveCaptionBorder);
		btn_book.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/submit_tickets.png")));
		btn_book.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_book.setBounds(782, 605, 170, 44);
		contentPane.add(btn_book);

		JButton btn_back = new JButton("Quay Lại");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new HomeTrain(socketInfo, user);
			}
		});
		btn_back.setBackground(SystemColor.inactiveCaptionBorder);
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_back.setIcon(new ImageIcon(HomeTrain.class.getResource("/Icon/back.png")));
		btn_back.setBounds(581, 605, 176, 44);
		contentPane.add(btn_back);

		tableModelInfoTrain = (DefaultTableModel) table_info_train.getModel();
		tableModelStatusTickets = (DefaultTableModel) table_info_tickets.getModel();

	}

	public String nameTrain() {
		int column = 0;
		int row = table_info_train.getSelectedRow();
		value = table_info_train.getModel().getValueAt(row, column).toString();
		return value;
	}

	public int indexLocation(String location) {
		for (int i = 0; i < trainStation.length; i++) {
			if (trainStation[i].equals(location)) {
				return i;
			}
		}

		return 0;
	}

	private int getIndexSeats(String mashClient, String idtype, String dayClient) {
		int index = 0;
		try {
			in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
			out = new PrintWriter(socketTrain.getOutputStream(), true);
			String sendData = "checkSeats," + mashClient + "," + idtype + "," + dayClient;
			out.println(sendData);

			String check = in.readLine();
			if (check.equals("0") || check == null) {
				index = 0;
			} else {
				index = Integer.parseInt(check);
			}
		} catch (Exception e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);

				in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
				out = new PrintWriter(socketTrain.getOutputStream(), true);
				String sendData = "checkSeats," + mashClient + "," + idtype + "," + dayClient;
				out.println(sendData);

				String check = in.readLine();
				if (check.equals("0") || check == null) {
					index = 0;
				} else {
					index = Integer.parseInt(check);
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Xin lỗi hiện nay server gặp trục trặc, vui lòng thử lại sau",
						"Lỗi Máy Chủ", 0, new ImageIcon(Toolkit.getDefaultToolkit()
								.createImage(Login.class.getResource("/Icon/server_error.png"))));
			}
		}
		return index;
	}

	private int getPriceTickets(String idtype) throws IOException {
		// TODO Auto-generated method stub
		int price = 0;
		try {
			in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
			out = new PrintWriter(socketTrain.getOutputStream(), true);
			String sendData = "getPrice," + idtype;
			out.println(sendData);
			String check = in.readLine();
			if (check.equals("0") || check == null) {
				price = 0;
			} else {
				price = Integer.parseInt(check);
			}
		} catch (Exception e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);
				
				in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
				out = new PrintWriter(socketTrain.getOutputStream(), true);
				String sendData = "getPrice," + idtype;
				out.println(sendData);
				String check = in.readLine();
				if (check.equals("0") || check == null) {
					price = 0;
				} else {
					price = Integer.parseInt(check);
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Xin lỗi hiện nay server gặp trục trặc, vui lòng thử lại sau",
						"Lỗi Máy Chủ", 0, new ImageIcon(Toolkit.getDefaultToolkit()
								.createImage(Login.class.getResource("/Icon/server_error.png"))));
			}
		}
		return price;
	}
}
