package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;





public class SignUp<JDateChooser> extends JFrame {
	

	private final JPanel contentPanel = new JPanel();
	private JTextField tf_dangkitk;
	private JPasswordField tf_dkimk;
	private JTextField tf_hovaten;
	private ButtonGroup gioitinh;
	private JTextField tf_sdt;
	private JPasswordField tf_nhaplaimk;
	private ButtonGroup bt_group;
	private JTextField tf_cccd;
	BufferedReader in;
	PrintWriter out;
	static Socket clientSocket;
	private JTextField tf_email;

//	 Run As -> Run Configurations -> (x)= Arguments -> VM arguments -> add: -Dsun.stdout.encoding=UTF-8
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp(clientSocket);
					
//					InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
//			        int serverPort = 8001;
//			        clientSocket = new Socket(serverAddress, serverPort);

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param clientSocket2 
	 */
	public <Image> SignUp(Socket clientSocket2) {
		this.clientSocket = clientSocket2;
		
		setTitle("Đăng Kí");	

		setBounds(390, 90, 830, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		URL url = SignUp.class.getResource("/Icon/dki.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);
		{
			JLabel lb_dangki = new JLabel("Đăng Kí");
			lb_dangki.setForeground(Color.RED);
			lb_dangki.setBounds(315, 23, 207, 49);
			lb_dangki.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 40));
			contentPanel.add(lb_dangki);
		}

		JSeparator separator = new JSeparator();
		separator.setBounds(37, 82, 769, -10);
		contentPanel.add(separator);
		{
			JSeparator separator_1 = new JSeparator();
			separator_1.setBounds(10, 76, 796, 13);
			contentPanel.add(separator_1);
		}
		{
			JLabel lb_thongtin = new JLabel("Thông Tin");
			lb_thongtin.setFont(new Font("Tahoma", Font.BOLD, 25));
			lb_thongtin.setBounds(20, 82, 142, 31);
			contentPanel.add(lb_thongtin);
		}
		{
			JLabel lb_dktaikhoan = new JLabel("Tài Khoản:");
			lb_dktaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lb_dktaikhoan.setBounds(49, 146, 113, 31);
			contentPanel.add(lb_dktaikhoan);
		}

		tf_dangkitk = new JTextField();
		tf_dangkitk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_dangkitk.setBounds(184, 147, 181, 28);
		contentPanel.add(tf_dangkitk);
		tf_dangkitk.setColumns(10);

		JLabel lb_dkmatkhau = new JLabel("Mật Khẩu:");
		lb_dkmatkhau.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dkmatkhau.setBounds(404, 146, 113, 31);
		contentPanel.add(lb_dkmatkhau);

		tf_dkimk = new JPasswordField();
		tf_dkimk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_dkimk.setColumns(10);
		tf_dkimk.setBounds(580, 147, 181, 30);
		contentPanel.add(tf_dkimk);

		JLabel lb_hovaten = new JLabel("Họ Và Tên:");
		lb_hovaten.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_hovaten.setBounds(51, 202, 99, 22);
		contentPanel.add(lb_hovaten);

		tf_hovaten = new JTextField();
		tf_hovaten.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_hovaten.setBounds(184, 198, 181, 31);
		contentPanel.add(tf_hovaten);
		tf_hovaten.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 389, 796, 13);
		contentPanel.add(separator_1);

		gioitinh = new ButtonGroup();

		JLabel lb_sdt = new JLabel("Số Điện Thoại");
		lb_sdt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_sdt.setBounds(51, 253, 123, 21);
		contentPanel.add(lb_sdt);

		tf_sdt = new JTextField();
		tf_sdt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_sdt.setColumns(10);
		tf_sdt.setBounds(184, 249, 181, 28);
		contentPanel.add(tf_sdt);

		JButton bt_xoatt = new JButton("Xóa Thông Tin");
		bt_xoatt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = JOptionPane.showConfirmDialog(SignUp.this, "Bạn Có Chắc Chắn Muốn Xóa Thông Tin?", "Cảnh Báo",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					xoatt();
				}
			}

		});
		bt_xoatt.setBackground(Color.GRAY);
		bt_xoatt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bt_xoatt.setBounds(638, 340, 168, 39);
		contentPanel.add(bt_xoatt);

		JLabel lb_nhaplaimk = new JLabel("Nhập Lại Mật Khẩu:");
		lb_nhaplaimk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_nhaplaimk.setBounds(404, 202, 166, 22);
		contentPanel.add(lb_nhaplaimk);

		bt_group = new ButtonGroup();

		JLabel lb_cccd = new JLabel("CMT/CCCD:");
		lb_cccd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_cccd.setBounds(273, 313, 99, 22);
		contentPanel.add(lb_cccd);

//		dtCBTD = new JDateChooser();
//		dtCBTD.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 18));
//		dtCBTD.getCalendarButton().setBackground(Color.WHITE);
//		dtCBTD.setBackground(Color.WHITE);
//		dtCBTD.setBounds(580, 244, 181, 30);
//		contentPanel.add(dtCBTD);

		tf_nhaplaimk = new JPasswordField();
		tf_nhaplaimk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_nhaplaimk.setBounds(580, 199, 181, 31);
		contentPanel.add(tf_nhaplaimk);
		tf_nhaplaimk.setColumns(10);
		
		tf_cccd = new JTextField();
		tf_cccd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_cccd.setBounds(382, 310, 181, 27);
		contentPanel.add(tf_cccd);
		tf_cccd.setColumns(10);
		
		JLabel lb_email = new JLabel("Email:");
		lb_email.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_email.setBounds(404, 253, 99, 22);
		contentPanel.add(lb_email);
		
		tf_email = new JTextField();
		tf_email.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_email.setColumns(10);
		tf_email.setBounds(580, 247, 181, 27);
		contentPanel.add(tf_email);
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
						new Login();

					}

				});
				bt_quaylai.setBackground(Color.WHITE);
				bt_quaylai.setFont(new Font("Tahoma", Font.PLAIN, 18));
				bt_quaylai.setActionCommand("Quay Lại");
				buttonPane.add(bt_quaylai);
			}
			{
				JButton bt_hoanthanh = new JButton("Hoàn Thành");
				bt_hoanthanh.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String s1 = tf_dangkitk.getText().toLowerCase();
						String s = tf_hovaten.getText();
						String s2 = tf_dkimk.getText().toLowerCase();
						String s3 = tf_nhaplaimk.getText().toLowerCase();
						String s4 = tf_sdt.getText();
						String s5 = tf_cccd.getText();
						String s6 = tf_email.getText();
						if (s1.equals("") == false && s4.equals("") == false && s2.equals("") == false
								&& s.equals("") == false && s5.equals("") == false && s6.equals("") == false) {
							if (tf_dkimk.getText().equalsIgnoreCase(tf_nhaplaimk.getText()) == false)
								JOptionPane.showMessageDialog(SignUp.this, "Mật Khẩu Không Khớp");
							else {
								
								try {
									SignUpUser(s1,s2,s,s5,s4,s6);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}

						} else {
							JOptionPane.showMessageDialog(SignUp.this, "Thiếu Thông Tin");
						}
					}

				

				});
				bt_hoanthanh.setBackground(Color.WHITE);
				bt_hoanthanh.setFont(new Font("Tahoma", Font.PLAIN, 18));
				bt_hoanthanh.setActionCommand("OK");
				buttonPane.add(bt_hoanthanh);
				getRootPane().setDefaultButton(bt_hoanthanh);
			}
		}
		setVisible(true);
	}

	private void xoatt() {
		tf_dangkitk.setText("");
		tf_dkimk.setText("");
		tf_hovaten.setText("");
		tf_sdt.setText("");
		tf_nhaplaimk.setText("");
		tf_cccd.setText("");

	}
	
	private void SignUpUser(String user, String pass, String fullname, String ccccd , String phonenumber, String email ) throws IOException {
		 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 out = new PrintWriter(clientSocket.getOutputStream(), true);
		 
//		 System.out.println(user+ " - " + pass +  " - "+ fullname + " - " + ccccd + " - " + phonenumber + " - " + email);
		 
		 String sendData = "signup,"+user+","+pass+","+fullname+","+ccccd+","+phonenumber+","+email;
		 out.println(sendData);
		 
		 String check = in.readLine();
		 if(check.equals("Sign Up Success")) {
			 System.out.println("Sign_Up => Success");
//	        	clientSocket.close();
			 JOptionPane.showMessageDialog(SignUp.this, "Đăng Ký Thành Công");
	        	dispose();
	        	new Login();
	        	
		 }else {
			 JOptionPane.showMessageDialog(SignUp.this, "Đăng Ký Thất Bại, Vui lòng kiểm tra lại thông tin của bạn!!!");
		 }
		
	}
}