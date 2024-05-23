package ChatClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Views.LoginChat;

import javax.swing.JLabel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import ChatTags.Tags;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class MainGui {

	private Client clientNode;
	private static String IPClient = "", nameUser = "", dataUser = "";
	private static int portClient = 0;
	private JFrame frameMainGui;
	private JTextField txtNameFriend;
	private JButton btnChat, btnExit;
	private JLabel lblLogo;
	private JLabel lblActiveNow;
	private static JList<String> listActive;

	static DefaultListModel<String> model = new DefaultListModel<>();
	private JLabel lblUsername;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGui(String arg, int arg1, String name, String msg) throws Exception {
		IPClient = arg;
		portClient = arg1;
		nameUser = name;
		dataUser = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frameMainGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGui() throws Exception {
		initialize();
		clientNode = new Client(IPClient, portClient, nameUser, dataUser);
	}

	public static void updateFriendMainGui(String msg) {
		model.addElement(msg);
	}

	public static void resetList() {
		model.clear();
	}

	private void initialize() {
		frameMainGui = new JFrame();
		frameMainGui.getContentPane().setBackground(new Color(255, 218, 185));
		frameMainGui.setTitle("Home Chat");
		frameMainGui.setResizable(false);
		frameMainGui.setBounds(580, 130, 500, 560);
		frameMainGui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameMainGui.getContentPane().setLayout(null);
		
		frameMainGui.setVisible(true);

		URL url = MainGui.class.getResource("/Image/hemo.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		frameMainGui.setIconImage((java.awt.Image) img);

		JLabel lblHello = new JLabel("Xin Chào");
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHello.setBounds(12, 82, 95, 27);
		frameMainGui.getContentPane().add(lblHello);

		JLabel lblFriendsName = new JLabel("Tên Người Dùng:");
		lblFriendsName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblFriendsName.setBounds(24, 425, 110, 16);
		frameMainGui.getContentPane().add(lblFriendsName);

		txtNameFriend = new JTextField("");
		txtNameFriend.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtNameFriend.setColumns(10);
		txtNameFriend.setBounds(144, 419, 332, 28);
		frameMainGui.getContentPane().add(txtNameFriend);

		btnChat = new JButton("Chat");
		btnChat.setBackground(Color.WHITE);
		btnChat.setFont(new Font("Segoe UI", Font.ITALIC, 13));

		btnChat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = txtNameFriend.getText();
				if (name.equals("") || Client.clientarray == null) {
					Tags.show(frameMainGui, "Tên Người Dùng Trống", false);
					return;
				}
				if (name.equals(nameUser)) {
					Tags.show(frameMainGui, "Phần mềm này không hỗ trợ chức năng tự trò chuyện", false);
					return;
				}
				int size = Client.clientarray.size();
				for (int i = 0; i < size; i++) {
					if (name.equals(Client.clientarray.get(i).getName())) {
						try {
							clientNode.intialNewChat(Client.clientarray.get(i).getHost(),
									Client.clientarray.get(i).getPort(), name);
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				Tags.show(frameMainGui, "Bạn không được tìm thấy. Vui lòng đợi để cập nhật danh sách bạn bè của bạn",
						false);
			}
		});
		btnChat.setBounds(22, 465, 129, 44);
		frameMainGui.getContentPane().add(btnChat);
		btnChat.setIcon(new javax.swing.ImageIcon(MainGui.class.getResource("/image/chat.png")));
		btnExit = new JButton("Thoát");
		btnExit.setBackground(Color.WHITE);
		btnExit.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frameMainGui, "Bạn có chắc không ?", true);
				if (result == 0) {
					try {
						clientNode.exit();
						frameMainGui.dispose();
					} catch (Exception e) {
						frameMainGui.dispose();
					}
				}
			}
		});
		btnExit.setBounds(345, 465, 129, 44);
		btnExit.setIcon(new javax.swing.ImageIcon(MainGui.class.getResource("/image/stop.png")));
		frameMainGui.getContentPane().add(btnExit);

		lblLogo = new JLabel("Messenger");
		lblLogo.setForeground(new Color(0, 0, 205));
		lblLogo.setIcon(new javax.swing.ImageIcon(MainGui.class.getResource("/image/mess2.png")));
		lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLogo.setBounds(178, 20, 172, 49);
		frameMainGui.getContentPane().add(lblLogo);

		lblActiveNow = new JLabel("Danh sách người dùng online");
		lblActiveNow.setForeground(new Color(100, 149, 237));
		lblActiveNow.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblActiveNow.setBounds(10, 111, 221, 28);
		frameMainGui.getContentPane().add(lblActiveNow);

		listActive = new JList<>(model);
		listActive.setBackground(new Color(255, 255, 255));
		listActive.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		listActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String value = (String) listActive.getModel().getElementAt(listActive.locationToIndex(arg0.getPoint()));
				txtNameFriend.setText(value);
			}
		});
		listActive.setBounds(12, 152, 464, 251);
		frameMainGui.getContentPane().add(listActive);

		lblUsername = new JLabel(nameUser);
		lblUsername.setForeground(Color.RED);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(100, 81, 156, 28);
		frameMainGui.getContentPane().add(lblUsername);

	}

	public static int request(String msg, boolean type) {
		JFrame frameMessage = new JFrame();
		return Tags.show(frameMessage, msg, type);
	}
}
