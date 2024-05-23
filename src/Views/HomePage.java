package Views;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class HomePage extends JFrame {

	private JPanel contentPane;
	static Socket clientSocket;
	static String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage(clientSocket,username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param clientSocket 
	 * @param user 
	 */
	public HomePage(Socket clientSocket, String user) {
		this.clientSocket = clientSocket;
		this.username = user;
		setTitle("HomePage");
		
		URL url = HomePage.class.getResource("/Icon/HomePage.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 200, 450, 330);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTrain = new JButton("Đặt Vé Tàu");
		btnTrain.setBackground(Color.LIGHT_GRAY);
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new HomeTrain(clientSocket,user);
			}
		});
		btnTrain.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTrain.setIcon(new ImageIcon(HomePage.class.getResource("/Icon/Trainn.png")));
		btnTrain.setBounds(118, 37, 215, 54);
		contentPane.add(btnTrain);
		
		JButton btnContact = new JButton("Liên Hệ Quản Lí");
		btnContact.setBackground(Color.LIGHT_GRAY);
		btnContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new LoginChat(username);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnContact.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnContact.setIcon(new ImageIcon(HomePage.class.getResource("/Icon/mess.png")));
		btnContact.setBounds(118, 121, 215, 54);
		contentPane.add(btnContact);
		
		JButton btnLogout = new JButton("Đăng Xuất");
		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogout.setIcon(new ImageIcon(HomePage.class.getResource("/Icon/Logout.png")));
		btnLogout.setBounds(118, 200, 215, 54);
		contentPane.add(btnLogout);
		setVisible(true);
	}
}
