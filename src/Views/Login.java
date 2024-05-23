package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.tools.javac.Main;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tf_username;
	private JPasswordField tf_pass;
	BufferedReader in;
	PrintWriter out;
	static Socket clientSocket;
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
					Login frame = new Login();

					serverAddress = InetAddress.getByName(ip);
//			        int serverPort = 8001;
					clientSocket = new Socket(serverAddress, serverPort1);

					frame.setVisible(true);
				} catch (Exception e) {
//					 JOptionPane.showMessageDialog(null,"Xin lỗi hiện nay server gặp trục trặc, vui lòng đăng nhập lại sau","Lỗi Máy Chủ",0, new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/server_error.png"))) );
//					 System.exit(0);
					try {
						serverAddress = InetAddress.getByName(ip);
//				        int serverPort = 8002;
						clientSocket = new Socket(serverAddress, serverPort2);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null,
								"Xin lỗi hiện nay server gặp trục trặc, vui lòng đăng nhập lại sau", "Lỗi Máy Chủ", 0,
								new ImageIcon(Toolkit.getDefaultToolkit()
										.createImage(Login.class.getResource("/Icon/server_error.png"))));
						System.exit(0);
					}

				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public <Image> Login() {
		setTitle("Đăng Nhập");

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(525, 100, 577, 586);

		URL url = Login.class.getResource("/Icon/icondangnhap.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_username = new JLabel("Tài Khoản:");
//		lb_username.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/icontaikhoan.png"))));
		lb_username.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_username.setBounds(86, 262, 177, 67);
		contentPane.add(lb_username);

		tf_username = new JTextField();
		tf_username.setBackground(Color.WHITE);
		tf_username.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_username.setBounds(246, 282, 177, 28);
		contentPane.add(tf_username);
		tf_username.setColumns(10);

		JLabel lb_password = new JLabel("Mật Khẩu:");
//		lb_password.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/iconmatkhau.png"))));
		lb_password.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_password.setBounds(86, 332, 188, 48);
		contentPane.add(lb_password);

		tf_pass = new JPasswordField();
		tf_pass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_pass.setBackground(Color.WHITE);
		tf_pass.setBounds(246, 343, 177, 28);
		contentPane.add(tf_pass);
		tf_pass.setColumns(10);

		JLabel lb_background = new JLabel("");
		lb_background.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/avatatrain.png"))));
		lb_background.setBounds(123, 10, 399, 233);
		contentPane.add(lb_background);

		JButton bt_dangnhap = new JButton("Đăng Nhập");
		bt_dangnhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = tf_username.getText();
				String pass = tf_pass.getText();
				if (user.length() == 0 || pass.length() == 0 || user.equals(" ") == true || pass.equals(" ") == true) {
					JOptionPane.showMessageDialog(Login.this, "Vui lòng nhập đủ thông tin tài khoản!!!");
				} else {
					CheckLogin(user, pass);
				}
			}

		});
		bt_dangnhap.setBackground(Color.GRAY);
		bt_dangnhap.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_dangnhap.setBounds(332, 418, 153, 38);
		contentPane.add(bt_dangnhap);

		JLabel lb_quenmk = new JLabel("Bạn Quên Mậtt Khẩu...?");
		lb_quenmk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new ForgotPassword();
			}
		});
		lb_quenmk.setBackground(Color.WHITE);
		lb_quenmk.setForeground(Color.RED);
		lb_quenmk.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lb_quenmk.setBounds(209, 388, 135, 13);
		contentPane.add(lb_quenmk);

		JButton bt_signup = new JButton("Đăng Kí");
		bt_signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new SignUp(clientSocket);
			}
		});
		bt_signup.setBackground(Color.GRAY);
		bt_signup.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_signup.setBounds(153, 420, 147, 34);
		contentPane.add(bt_signup);
		setVisible(true);
	}

	private void CheckLogin(String user, String pass) {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);

			String sendData = "login," + user + "," + pass;
			out.println(sendData);

			String check = in.readLine();
			if (check.equals("Login Success")) {
				System.out.println("Login => Success");
//		        	clientSocket.close();
				dispose();
				new HomePage(clientSocket, user);
//		        	new Login();

			} else {
				JOptionPane.showMessageDialog(Login.this,
						"Đăng Nhập Thất Bại, Vui lòng kiểm tra lại thông tin tài khoản!!!");

			}
		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null,"Xin lỗi hiện nay server gặp trục trặc, vui lòng đăng nhập lại sau","Lỗi Máy Chủ",0, new ImageIcon(Toolkit.getDefaultToolkit().createImage(Login.class.getResource("/Icon/server_error.png"))) );
//			System.exit(0);
			try {
				serverAddress = InetAddress.getByName(ip);
				clientSocket = new Socket(serverAddress, serverPort2);

				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				String sendData = "login," + user + "," + pass;
				out.println(sendData);

				String check = in.readLine();
				if (check.equals("Login Success")) {
					System.out.println("Login => Success");
//			        	clientSocket.close();
					dispose();
					new HomePage(clientSocket, user);
//			        	new Login();

				} else {
					JOptionPane.showMessageDialog(Login.this,
							"Đăng Nhập Thất Bại, Vui lòng kiểm tra lại thông tin tài khoản!!!");

				}

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Xin lỗi hiện nay server gặp trục trặc, vui lòng đăng nhập lại sau",
						"Lỗi Máy Chủ", 0, new ImageIcon(Toolkit.getDefaultToolkit()
								.createImage(Login.class.getResource("/Icon/server_error.png"))));
				System.exit(0);
			}
		}

	}
}
