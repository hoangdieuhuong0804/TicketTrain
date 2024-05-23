package Server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class ServerGui {

	public static int port = 8080;
	private JFrame frmServerMangement;
	private JTextField txtIP, txtPort;
	private JLabel lblStatus;
	private static TextArea txtMessage;
	public static JLabel lblUserOnline;
	ServerCore server;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui window = new ServerGui();
					window.frmServerMangement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerGui() {
		initialize();
	}
	
	public static String getLabelUserOnline() {
		return lblUserOnline.getText();
	}
	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

	public static void updateNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number + 1));
	}
	
	public static void decreaseNumberClient() {
		int number = Integer.parseInt(lblUserOnline.getText());
		lblUserOnline.setText(Integer.toString(number - 1));

	}

	private void initialize() {
		frmServerMangement = new JFrame();
		frmServerMangement.getContentPane().setBackground(Color.WHITE);
		frmServerMangement.setForeground(UIManager.getColor("RadioButtonMenuItem.foreground"));
		frmServerMangement.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 13));
		frmServerMangement.getContentPane().setForeground(UIManager.getColor("RadioButtonMenuItem.acceleratorSelectionForeground"));
		frmServerMangement.setTitle("Server");
		frmServerMangement.setResizable(false);
		frmServerMangement.setBounds(450, 50, 730, 686);
		frmServerMangement.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmServerMangement.getContentPane().setLayout(null);
		frmServerMangement.setBackground(Color.ORANGE);
		frmServerMangement.setVisible(true);
		
		URL url = ServerGui.class.getResource("/Image/Hserver.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.frmServerMangement.setIconImage((java.awt.Image) img);

		JLabel lblIP = new JLabel("\u0110\u1ECBa Ch\u1EC9 IP:");
		lblIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblIP.setBounds(26, 120, 89, 16);					////// Vi tri lbl IP
		frmServerMangement.getContentPane().add(lblIP);

		txtIP = new JTextField();
		txtIP.setEditable(false);
		txtIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtIP.setBounds(126, 114, 176, 28);				////// Vi tri text Ip
		frmServerMangement.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Port:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel.setBounds(415, 120, 48, 16);			////// Vi tri lbl Port
		frmServerMangement.getContentPane().add(lblNewLabel);

		txtPort = new JTextField();
		txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtPort.setEditable(false);
		txtPort.setColumns(10);
		txtPort.setBounds(458, 114, 224, 28);
		frmServerMangement.getContentPane().add(txtPort);			///// Vi tri cua text Port
		txtPort.setText("8080");

		JButton btnStart = new JButton("START");
		btnStart.setForeground(Color.GREEN);
		btnStart.setBackground(Color.WHITE);
		btnStart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		btnStart.setBounds(354, 194, 143, 43);			/////// Vi tri button START
		frmServerMangement.getContentPane().add(btnStart);
		
		
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File(ServerGui.class.getResource("/image/serverManager.png").getFile()));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//		Image dimg = img.getScaledInstance(64, 64,
//		        Image.SCALE_SMOOTH);
//		ImageIcon imageIcon = new ImageIcon(dimg);

		
		JLabel lblNhom = new JLabel("Qu\u1EA3n L\u00ED Server");
		lblNhom.setForeground(new Color(0, 0, 255));
		lblNhom.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblNhom.setBounds(287, 10, 176, 76);
//		lblNhom.setIcon(new javax.swing.ImageIcon(ServerGui.class.getResource("/image/servermanager64x64.png")));
		frmServerMangement.getContentPane().add(lblNhom);

		txtMessage = new TextArea();					
		txtMessage.setBackground(Color.BLACK);
		txtMessage.setForeground(Color.GREEN);
		txtMessage.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtMessage.setEditable(false);
		txtMessage.setBounds(10, 274, 696, 365);		////// Vi tri textArea
		frmServerMangement.getContentPane().add(txtMessage);

		JButton btnStop = new JButton("STOP");
		btnStop.setForeground(Color.RED);
		btnStop.setBackground(Color.WHITE);
		btnStop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblUserOnline.setText("0");
				try {
					server.stopserver();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				} catch (Exception e) {
					e.printStackTrace();
					ServerGui.updateMessage("STOP SERVER");
					lblStatus.setText("<html><font color='red'>OFF</font></html>");
				}
			}
		});
		btnStop.setBounds(539, 194, 143, 43);						//// Vi tri button Stop
		frmServerMangement.getContentPane().add(btnStop);
		
		JLabel lblnew111 = new JLabel("Tr\u1EA1ng Th\u00E1i:");
		lblnew111.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblnew111.setBounds(26, 168, 89, 16);
		frmServerMangement.getContentPane().add(lblnew111);
		
		lblStatus = new JLabel("OFF");
		lblStatus.setForeground(Color.RED);
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblStatus.setBounds(204, 168, 81, 16);


		frmServerMangement.getContentPane().add(lblStatus);
//		lblStatus.setText("<html><font color='red'>OFF</font></html>");
		
		JLabel lblRecord = new JLabel("Th\u00F4ng Tin:");
		lblRecord.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblRecord.setBounds(26, 240, 89, 28);
		frmServerMangement.getContentPane().add(lblRecord);
		
		JLabel lbllabelUserOnline = new JLabel("Ng\u01B0\u1EDDi D\u00F9ng Ho\u1EA1t \u0110\u1ED9ng:");
		lbllabelUserOnline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lbllabelUserOnline.setBounds(26, 207, 159, 30);
		frmServerMangement.getContentPane().add(lbllabelUserOnline);
		
		lblUserOnline = new JLabel("0");
		lblUserOnline.setForeground(Color.BLUE);
		lblUserOnline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblUserOnline.setBounds(215, 207, 33, 30);
		frmServerMangement.getContentPane().add(lblUserOnline);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new ServerCore(8080);
					ServerGui.updateMessage("START SERVER");
					lblStatus.setText("<html><font color='green'>RUNNING...</font></html>");
				} catch (Exception e) {
					ServerGui.updateMessage("START ERROR");
					e.printStackTrace();
				}
			}
		});
	}
}

