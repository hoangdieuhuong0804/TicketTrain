package Views;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DB_Connection.StatementMySQL;
import Model.ClientBookTickets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class ShowDetalTicket extends JFrame {

	private JPanel contentPane;
	private JLabel lb_username;
	private JLabel lb_id_ve;
	private JLabel lb_nametrain;
	private JLabel lb_daytrain;
	private JLabel lb_location_start;
	private JLabel lb_location_end;
	private JLabel lb_time_start;
	private JLabel lb_expectedend_time;
	private JLabel lb_type;
	private JLabel lb_seats;
	private JLabel lb_price;
	static Socket socketInfo;
	static Socket socketTrain;
	BufferedReader in;
	PrintWriter out;
	ObjectOutputStream outToServer;
	ObjectInputStream inFromServer;
	private static ClientBookTickets clientBookTickets;
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
					ShowDetalTicket frame = new ShowDetalTicket(socketInfo, socketTrain, clientBookTickets);
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
	 * @param clientBookTickets2
	 * @param socketTrain2
	 * @param socketInfo2
	 */
	public ShowDetalTicket(Socket socketInfo2, Socket socketTrain2, ClientBookTickets clientBookTickets2) {
		this.socketInfo = socketInfo2;
		this.socketTrain = socketTrain2;
		this.clientBookTickets = clientBookTickets2;

		setTitle("Thông Tin Vé");

		URL url = ShowDetalTicket.class.getResource("/Icon/detaltickets.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(375, 42, 850, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Thông Tin Vé Tàu");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(315, 24, 200, 27);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(47, 66, 749, 2);
		contentPane.add(separator);

		JLabel lblNewLabel_1 = new JLabel("Tài Khoản:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(64, 94, 122, 20);
		contentPane.add(lblNewLabel_1);

		lb_username = new JLabel("Minh21kit");
		lb_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_username.setBounds(188, 98, 169, 13);
		contentPane.add(lb_username);

		JLabel lblNewLabel_3 = new JLabel("ID Vé:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(523, 95, 83, 19);
		contentPane.add(lblNewLabel_3);

		lb_id_ve = new JLabel("SH89821");
		lb_id_ve.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_id_ve.setBounds(616, 95, 101, 19);
		contentPane.add(lb_id_ve);

		JLabel lblNewLabel_5 = new JLabel("Chuyến Tàu:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_5.setBounds(64, 137, 109, 20);
		contentPane.add(lblNewLabel_5);

		lb_nametrain = new JLabel("Thống Nhất SE3");
		lb_nametrain.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_nametrain.setBounds(188, 137, 169, 19);
		contentPane.add(lb_nametrain);

		JLabel lblNewLabel_7 = new JLabel("Ngày Đi:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_7.setBounds(523, 138, 83, 19);
		contentPane.add(lblNewLabel_7);

		lb_daytrain = new JLabel("15/11/2023");
		lb_daytrain.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_daytrain.setBounds(616, 138, 101, 19);
		contentPane.add(lb_daytrain);

		JLabel lblNewLabel_9 = new JLabel("Ga Đi:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_9.setBounds(64, 179, 109, 20);
		contentPane.add(lblNewLabel_9);

		lb_location_start = new JLabel("Hà Nội");
		lb_location_start.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_location_start.setBounds(188, 179, 169, 19);
		contentPane.add(lb_location_start);

		JLabel lblNewLabel_11 = new JLabel("Ga Đến:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_11.setBounds(523, 180, 83, 19);
		contentPane.add(lblNewLabel_11);

		lb_location_end = new JLabel("Sài Gòn");
		lb_location_end.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_location_end.setBounds(616, 180, 169, 19);
		contentPane.add(lb_location_end);

		JLabel lblNewLabel_13 = new JLabel("Rời Ga:");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_13.setBounds(64, 217, 76, 27);
		contentPane.add(lblNewLabel_13);

		lb_time_start = new JLabel("06:30:00");
		lb_time_start.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_time_start.setBounds(188, 217, 157, 22);
		contentPane.add(lb_time_start);

		JLabel lblNewLabel_15 = new JLabel("Dự Kiến:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_15.setBounds(523, 219, 83, 22);
		contentPane.add(lblNewLabel_15);

		lb_expectedend_time = new JLabel("1 Ngày 10 Tiếng 40 Phút");
		lb_expectedend_time.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_expectedend_time.setBounds(616, 219, 190, 22);
		contentPane.add(lb_expectedend_time);

		JLabel lblNewLabel_17 = new JLabel("Loại Ghế: ");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_17.setBounds(64, 258, 93, 24);
		contentPane.add(lblNewLabel_17);

		lb_type = new JLabel("Giường Nằm");
		lb_type.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_type.setBounds(188, 259, 145, 21);
		contentPane.add(lb_type);

		JLabel lblNewLabel_19 = new JLabel("Chỗ: ");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_19.setBounds(523, 258, 76, 27);
		contentPane.add(lblNewLabel_19);

		lb_seats = new JLabel("A-01");
		lb_seats.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_seats.setBounds(616, 259, 145, 20);
		contentPane.add(lb_seats);

		JLabel lblNewLabel_21 = new JLabel("Tổng Giá Vé");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_21.setBounds(573, 306, 109, 20);
		contentPane.add(lblNewLabel_21);

		lb_price = new JLabel("300.000VND");
		lb_price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_price.setBounds(578, 350, 122, 13);
		contentPane.add(lb_price);

		JLabel lblNewLabel_23 = new JLabel("(Giá vé trên đã có bảo hiểm, dịch vụ đi kèm và thuế GTGT)");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_23.setBounds(430, 383, 396, 13);
		contentPane.add(lblNewLabel_23);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(47, 606, 749, 2);
		contentPane.add(separator_1);

		JButton btnNewButton = new JButton("Xác Nhận");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = lb_username.getText();
				String idTickets = lb_id_ve.getText();
				String mash = clientBookTickets.getMash();
				String idtypes = clientBookTickets.getIdtype();
//				System.out.println(idtypes);
				String dayTrain = lb_daytrain.getText();
				String locationStart = lb_location_start.getText();
				String locationEnd = lb_location_end.getText();
				String timeStart = lb_time_start.getText();
				String expectedendTime = lb_expectedend_time.getText();
				String seats = lb_seats.getText();
				String priceTickets = lb_price.getText();
				int checkseats = checkSeats(mash, seats, dayTrain);
				System.out.println("checkSeats: " + checkseats);
				if (checkseats != 0) {

//					char s = seats.charAt(seats.length()-1);
					String s = seats.substring(3, 4);
					seats = seats.replace(s, Integer.toString((Integer.parseInt(s) + 1)));
					System.out.println(seats);
					SignUpTickets(username, idTickets, mash, idtypes, locationStart, locationEnd, seats, timeStart,
							expectedendTime, dayTrain, priceTickets);

				} else {
					SignUpTickets(username, idTickets, mash, idtypes, locationStart, locationEnd, seats, timeStart,
							expectedendTime, dayTrain, priceTickets);
				}

//				dispose();
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(640, 629, 145, 35);
		contentPane.add(btnNewButton);

		JButton btnQuayLi = new JButton("Quay lại");
		btnQuayLi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnQuayLi.setBackground(Color.WHITE);
		btnQuayLi.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnQuayLi.setBounds(485, 629, 145, 35);
		contentPane.add(btnQuayLi);

		JLabel lblNewLabel_24 = new JLabel("Thanh Toán");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_24.setBounds(151, 310, 109, 13);
		contentPane.add(lblNewLabel_24);

		JLabel lblNewLabel_25 = new JLabel("New label");
		lblNewLabel_25.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(ShowDetalTicket.class.getResource("/Icon/qr.png"))));
		lblNewLabel_25.setBounds(93, 339, 221, 212);
		contentPane.add(lblNewLabel_25);

		JLabel lblNewLabel_23_1 = new JLabel("(Bạn có thể chuyển khoản hoạc trả trực tiếp tại quầy vé)");
		lblNewLabel_23_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_23_1.setBounds(42, 561, 369, 20);
		contentPane.add(lblNewLabel_23_1);
		try {
			setForm();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setVisible(true);
	}

	public void setForm() throws IOException {
		lb_username.setText(clientBookTickets.getUsername());
		lb_id_ve.setText(clientBookTickets.getIdtickets());
		String nameTrain = getNameTrain(clientBookTickets.getMash());
		lb_nametrain.setText(nameTrain);
		lb_daytrain.setText(clientBookTickets.getDay());
		lb_location_start.setText(clientBookTickets.getLocationStartClient());
		lb_location_end.setText(clientBookTickets.getLocationEndClient());
		lb_time_start.setText(clientBookTickets.getTimeStart());
		lb_expectedend_time.setText(clientBookTickets.getExpectedendTime());
		String type = getType(clientBookTickets.getIdtype());
		lb_type.setText(type);
		lb_seats.setText(clientBookTickets.getSeats());
		int price = clientBookTickets.getPriceTickets();
		lb_price.setText(clientBookTickets.getPriceTickets() + " VND");

	}

	private int checkSeats(String mash, String seats, String day) {
		int num = 0;
		StatementMySQL statementMySQL = new StatementMySQL();
		num = statementMySQL.checkDataSeats(mash, seats, day);
		System.out.println("CheckData: " + num);
		return num;
	}

	public String getType(String type) {
		String typeTickets = "";
		try {
			in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
			out = new PrintWriter(socketTrain.getOutputStream(), true);
			String sendData = "typeTickets," + type;
			out.println(sendData);

			String check = in.readLine();
			if (check.equals("") == false && check != null) {
				typeTickets = check;
			}
		} catch (Exception e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);

				in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
				out = new PrintWriter(socketTrain.getOutputStream(), true);
				String sendData = "typeTickets," + type;
				out.println(sendData);

				String check = in.readLine();
				if (check.equals("") == false && check != null) {
					typeTickets = check;
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return typeTickets;
	}

	public String getNameTrain(String mash) {
		String nameTrain = "";
		try {
			in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
			out = new PrintWriter(socketTrain.getOutputStream(), true);
			String sendData = "nameTrain," + mash;
			out.println(sendData);
			String check = in.readLine();
			if (check.equals("") == false && check != null) {
				nameTrain = check;
			}
		} catch (Exception e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketTrain = new Socket(serverAddress, serverPort1);

				in = new BufferedReader(new InputStreamReader(socketTrain.getInputStream()));
				out = new PrintWriter(socketTrain.getOutputStream(), true);
				String sendData = "nameTrain," + mash;
				out.println(sendData);
				String check = in.readLine();
				if (check.equals("") == false && check != null) {
					nameTrain = check;
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		// INSERT INTO `clientbooktickets`
		// (`idtickets`,`username`,`mash`,`idtype`,`locationstartclient`,`locationendclient`,`seats`,`timestart`,`expectedendtime`,`day`,`pricetickets`)
		// VALUES('
		// SH35782','minh21kitt','se3','aa','aa','aa','aa','12:00:00','12
		// Tiếng',2023/11/22,12000)
		return nameTrain;
	}

	private void SignUpTickets(String username, String idTickets, String mash, String idtypes, String locationStart,
			String locationEnd, String seats, String timeStart, String expectedendTime, String dayTrain,
			String priceTickets) {
		// TODO Auto-generated method stub
		String checkPrice = "";
		for (int i = 0; i < priceTickets.length() - 4; i++) {
			checkPrice += priceTickets.charAt(i);
		}
//			System.out.println(checkPrice);
		try {
			in = new BufferedReader(new InputStreamReader(socketInfo.getInputStream()));
			out = new PrintWriter(socketInfo.getOutputStream(), true);
			String sendData = "signUpTickets," + username + "," + idTickets + "," + mash + "," + idtypes + ","
					+ locationStart + "," + locationEnd + "," + seats + "," + timeStart + "," + expectedendTime + ","
					+ dayTrain + "," + checkPrice + "," + "false";
			out.println(sendData);

			String check = in.readLine();
			if (check.equals("Success")) {
				JOptionPane.showMessageDialog(ShowDetalTicket.this, "Đăng Ký Vé Thành Công!!!");
				dispose();
			} else {
				JOptionPane.showMessageDialog(ShowDetalTicket.this,
						"Đăng Ký Vé Không Thành Công, Vui lòng kiểm tra lại!!!");
			}
		} catch (IOException e) {
			try {
				serverAddress = InetAddress.getByName(ip);
				socketInfo = new Socket(serverAddress, serverPort2);

				in = new BufferedReader(new InputStreamReader(socketInfo.getInputStream()));
				out = new PrintWriter(socketInfo.getOutputStream(), true);
				String sendData = "signUpTickets," + username + "," + idTickets + "," + mash + "," + idtypes + ","
						+ locationStart + "," + locationEnd + "," + seats + "," + timeStart + "," + expectedendTime
						+ "," + dayTrain + "," + checkPrice + "," + "false";
				out.println(sendData);

				String check = in.readLine();
				if (check.equals("Success")) {
					JOptionPane.showMessageDialog(ShowDetalTicket.this, "Đăng Ký Vé Thành Công!!!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(ShowDetalTicket.this,
							"Đăng Ký Vé Không Thành Công, Vui lòng kiểm tra lại!!!");
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,
						"Xin lỗi hiện nay server gặp trục trặc, vui lòng trở lại sau ít phút nữa", "Lỗi Máy Chủ", 0,
						new ImageIcon(Toolkit.getDefaultToolkit()
								.createImage(Login.class.getResource("/Icon/server_error.png"))));
				dispose();
			}

//				e.printStackTrace();
		}

//	        outToServer = new ObjectOutputStream(socketInfo.getOutputStream());
//	        inFromServer = new ObjectInputStream(socketInfo.getInputStream());
//	        LinkedList<ClientBookTickets> msgList = new LinkedList<>();
//	        ClientBookTickets clientBookTickets = new ClientBookTickets(username,idTickets,mash,idtypes,locationStart,locationEnd,seats,timeStart,expectedendTime,dayTrain,priceTickets);
//	        msgList.push(clientBookTickets);
//	        outToServer.writeObject(msgList);

	}
}
