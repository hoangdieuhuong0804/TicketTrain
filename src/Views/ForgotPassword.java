package Views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ForgotPassword extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JPanel contentPane;
	private JTextField tf_nhapsdt;
	private JTextField tf_maxn;
	private JPasswordField tf_mkmoi;
	private JPasswordField tf_mkmoi2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword frame = new ForgotPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public <Image> ForgotPassword() {
		setTitle("Password");
		URL url = ForgotPassword.class.getResource("/Icon/pass.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);
		setLocationRelativeTo(null);
		setBounds(390, 100, 872, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lb_quenmk = new JLabel("Qu\u00EAn M\u1EADt Kh\u1EA9u");
		lb_quenmk.setForeground(Color.RED);
		lb_quenmk.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 40));
		lb_quenmk.setBounds(260, 21, 323, 44);
		contentPane.add(lb_quenmk);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 75, 838, 2);
		contentPane.add(separator);
		
		JLabel lb_nhapsdt = new JLabel("Vui Lòng Nhập Số Điện Thoại:");
		lb_nhapsdt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_nhapsdt.setBounds(41, 121, 269, 31);
		contentPane.add(lb_nhapsdt);
		
		tf_nhapsdt = new JTextField();
		tf_nhapsdt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_nhapsdt.setBounds(347, 123, 198, 28);
		contentPane.add(tf_nhapsdt);
		tf_nhapsdt.setColumns(10);
		
		JLabel lb_maxn = new JLabel("Nhập Mã Xác Nhận:");
		lb_maxn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_maxn.setBounds(41, 171, 177, 28);
		contentPane.add(lb_maxn);
		
		tf_maxn = new JTextField();
		tf_maxn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_maxn.setBounds(347, 172, 198, 28);
		contentPane.add(tf_maxn);
		tf_maxn.setColumns(10);
		
		JButton bt_guima = new JButton("Gửi Mã Xác Nhận");
		bt_guima.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String phonenumber = tf_nhapsdt.getText();
				if(phonenumber.equals("")) {
					JOptionPane.showMessageDialog(ForgotPassword.this,"Vui lòng nhập vào số điện thoại của bạn!!!");
				}else {
					JOptionPane.showMessageDialog(ForgotPassword.this,"Mã Đang Về, Vui Lòng Đợi Giây Lát");
				}
				
			}
			
		});
		bt_guima.setBackground(Color.GRAY);
		bt_guima.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bt_guima.setBounds(587, 143, 227, 33);
		contentPane.add(bt_guima);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 244, 838, 2);
		contentPane.add(separator_1);
		
		JLabel lb_doimk = new JLabel("Đổi Mật Khẩu");
		lb_doimk.setForeground(Color.BLACK);
		lb_doimk.setFont(new Font("Tahoma", Font.BOLD, 22));
		lb_doimk.setBounds(20, 256, 198, 28);
		contentPane.add(lb_doimk);
		
		JLabel lb_mkmoi = new JLabel("Mật Khẩu Mới:");
		lb_mkmoi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_mkmoi.setBounds(236, 301, 150, 31);
		contentPane.add(lb_mkmoi);
		
		tf_mkmoi = new JPasswordField();
		tf_mkmoi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_mkmoi.setBounds(460, 303, 177, 29);
		contentPane.add(tf_mkmoi);
		tf_mkmoi.setColumns(10);
		
		JLabel lb_mkmoi_2 = new JLabel("Nhập Lại Mật Khẩu:");
		lb_mkmoi_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_mkmoi_2.setBounds(235, 367, 227, 31);
		contentPane.add(lb_mkmoi_2);
		
		tf_mkmoi2 = new JPasswordField();
		tf_mkmoi2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_mkmoi2.setColumns(10);
		tf_mkmoi2.setBounds(460, 369, 177, 29);
		contentPane.add(tf_mkmoi2);
		
		JButton bt_quaylai = new JButton("Quay Lại");
		bt_quaylai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
			
		});
		bt_quaylai.setBackground(Color.WHITE);
		bt_quaylai.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bt_quaylai.setBounds(544, 461, 132, 31);
		contentPane.add(bt_quaylai);
		
		JButton bt_hoanthnanh = new JButton("Hoàn Thành");
		bt_hoanthnanh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ForgotPassword.this,"Cập Nhật Mật Khẩu Thành Công");
				dispose();
				new Login();
			}
			
		});
		bt_hoanthnanh.setBackground(Color.WHITE);
		bt_hoanthnanh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bt_hoanthnanh.setBounds(698, 461, 150, 30);
		contentPane.add(bt_hoanthnanh);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 443, 838, 8);
		contentPane.add(separator_2);
		setVisible(true);
	}

}
