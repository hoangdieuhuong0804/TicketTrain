package ChatClient;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Label;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import ChatData.DataFile;
import Views.LoginChat;
import ChatTags.Decode;
import ChatTags.Encode;
import ChatTags.Tags;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;


import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;


public class ChatGui {

	private static String URL_DIR = System.getProperty("user.dir");
	private static String TEMP = "/temp/";
	private String str;
	private ChatRoom chat;
	private Socket socketChat;
	private String nameUser = "", nameGuest = "", nameFile = "";
	private JFrame frameChatGui;
	private JTextField textName;
	private JPanel panelMessage;
	private JTextPane txtDisplayChat;
	private Label textState, lblReceive;
	private JButton btnDisConnect, btnSend, btnChoose;
	public boolean isStop = false, isSendFile = false, isReceiveFile = false;
	private JProgressBar progressSendFile;
	private JTextField txtPath;
	private int portServer = 0;
	private JTextField txtMessage;
	private JScrollPane scrollPane;
	private JButton btnSmileBigIcon;
	private JButton btnCryingIcon;
	private JButton btnSmileCryingIcon;
	private JButton btnHeartEyeIcon;
	private JButton buttonScaredIcon;
	private JButton buttonSadIcon;
	private JLabel lblNewLabel;
	private JMenu mnNewMenu_2;
	private JMenuItem mntmNewMenuItem_5;
	private JMenuItem mntmNewMenuItem_6;
	private JMenu mnNewMenu_3;
	private JMenuItem mntmNewMenuItem_7;
	private JMenuItem mntmNewMenuItem_8;
	private JMenuItem mntmNewMenuItem_9;

	public ChatGui(String user, String guest, Socket socket, int port) {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui window = new ChatGui(nameUser, nameGuest, socketChat, portServer, 0);
					window.frameChatGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGui window = new ChatGui();
					window.frameChatGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateChat_receive(String msg) {
		appendToPane(txtDisplayChat, "<div class='left' style='width: 40%; background-color: #f1f0f0;'>"+ msg +"</div>");
	}

	public void updateChat_send(String msg) {
		appendToPane(txtDisplayChat, "<table class='bang' style='color: white; clear:both; width: 100%;'>"
				+ "<tr align='left'>"
				+ "<td style='width: 59%; '></td>"
				+ "<td style='width: 40%; background-color: #0084ff;'>" + msg 
				+"</td> </tr>"
				+ "</table>");
	}
	
	public void updateChat_notify(String msg) {
		appendToPane(txtDisplayChat, "<table class='bang' style='color: white; clear:both; width: 100%;'>"
				+ "<tr align='right'>"
				+ "<td style='width: 59%; '></td>"
				+ "<td style='width: 40%; background-color: #f1c40f;'>" + msg 
				+"</td> </tr>"
				+ "</table>");
	}

	
	public void updateChat_send_Symbol(String msg) {
		appendToPane(txtDisplayChat, "<table style='width: 100%;'>"
				+ "<tr align='right'>"
				+ "<td style='width: 59%;'></td>"
				+ "<td style='width: 40%;'>" + msg 
				+"</td> </tr>"
				+ "</table>");
	}
	
	public ChatGui() {
		initialize();
	}

	public ChatGui(String user, String guest, Socket socket, int port, int a)
			throws Exception {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		initialize();
		chat = new ChatRoom(socketChat, nameUser, nameGuest);
		chat.start();
	}

	private void initialize() {
		File fileTemp = new File(URL_DIR + "/temp");
		if (!fileTemp.exists()) {
			fileTemp.mkdirs();
		}
		frameChatGui = new JFrame();
		frameChatGui.getContentPane().setBackground(new Color(192, 192, 192));
		frameChatGui.setTitle("Messenger");
		frameChatGui.setResizable(false);
		frameChatGui.setBounds(480, 80, 673, 718);
		frameChatGui.getContentPane().setLayout(null);
		frameChatGui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		frameChatGui.setVisible(true);
		

		URL url = ChatGui.class.getResource("/Image/mess3.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		frameChatGui.setIconImage((java.awt.Image) img);

		JLabel lblClientIP = new JLabel("");
		lblClientIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblClientIP.setBounds(10, 57, 41, 40);
		lblClientIP.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/user_chat.png")));
		frameChatGui.getContentPane().add(lblClientIP);

		textName = new JTextField(nameUser);
		textName.setBackground(new Color(192, 192, 192));
		textName.setForeground(Color.RED);
		textName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textName.setEditable(false);
		textName.setBounds(61, 57, 176, 40);
		frameChatGui.getContentPane().add(textName);
		textName.setText(nameGuest);
		textName.setColumns(10);

		panelMessage = new JPanel();
		panelMessage.setBackground(new Color(192, 192, 192));
		panelMessage.setBounds(10, 446, 639, 201);
		panelMessage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Message"));
		frameChatGui.getContentPane().add(panelMessage);
		panelMessage.setLayout(null);

		txtMessage = new JTextField("");
		txtMessage.setBounds(10, 21, 453, 62);
		panelMessage.add(txtMessage);
		txtMessage.setColumns(10);

		btnSend = new JButton("");
		btnSend.setBackground(new Color(192, 192, 192));
		btnSend.setIcon(new ImageIcon(ChatGui.class.getResource("/image/send.png")));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnSend.setBounds(562, 33, 39, 43);
		btnSend.setContentAreaFilled(false);
		panelMessage.add(btnSend);
		

		
		txtPath = new JTextField("");
		txtPath.setBounds(55, 163, 433, 25);
		panelMessage.add(txtPath);
		txtPath.setEditable(false);
		txtPath.setColumns(10);
				
				
		Label label = new Label("Path:");
		label.setBounds(10, 166, 39, 22);
		panelMessage.add(label);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JButton btnSendLike = new JButton("");
		btnSendLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/like.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnSendLike.setBackground(new Color(192, 192, 192));
		btnSendLike.setBounds(489, 33, 50, 43);
		btnSendLike.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/like.png")));
		//transparent button
		btnSendLike.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSendLike.setContentAreaFilled(false);


		panelMessage.add(btnSendLike);
		
		JButton btnSmileIcon = new JButton("");
		btnSmileIcon.setBackground(new Color(192, 192, 192));
		btnSmileIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/smile.png") +"'></img>";
				System.out.println("Tin nhan truoc khi bi encode: " +  msg);
				System.out.println("Tin nhan sau khi bi encode: " +  Encode.sendMessage(msg));
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnSmileIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSmileIcon.setContentAreaFilled(false);
		btnSmileIcon.setBounds(62, 96, 50, 36);
		btnSmileIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile.png")));
		panelMessage.add(btnSmileIcon);
		
		Label label_1 = new Label("Icon");
		label_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		label_1.setBounds(10, 107, 39, 22);
		panelMessage.add(label_1);
		
		btnSmileBigIcon = new JButton("");
		btnSmileBigIcon.setBackground(new Color(192, 192, 192));
		btnSmileBigIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSmileBigIcon.setContentAreaFilled(false);
		btnSmileBigIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/smile_big.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}		});
		btnSmileBigIcon.setBounds(124, 96, 50, 36);
		panelMessage.add(btnSmileBigIcon);
		btnSmileBigIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile_big.png")));
		
		btnCryingIcon = new JButton("");
		btnCryingIcon.setBackground(new Color(192, 192, 192));
		btnCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCryingIcon.setContentAreaFilled(false);
		btnCryingIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/crying.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnCryingIcon.setBounds(186, 96, 65, 36);
		panelMessage.add(btnCryingIcon);
		btnCryingIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/crying.png")));
		
		btnSmileCryingIcon = new JButton("");
		btnSmileCryingIcon.setBackground(new Color(192, 192, 192));
		btnSmileCryingIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSmileCryingIcon.setContentAreaFilled(false);
		btnSmileCryingIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/smile_cry.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnSmileCryingIcon.setBounds(255, 96, 56, 39);
		panelMessage.add(btnSmileCryingIcon);
		btnSmileCryingIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/smile_cry.png")));
		
		btnHeartEyeIcon = new JButton("");
		btnHeartEyeIcon.setBackground(new Color(192, 192, 192));
		btnHeartEyeIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHeartEyeIcon.setContentAreaFilled(false);
		btnHeartEyeIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/heart_eye.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		btnHeartEyeIcon.setBounds(323, 96, 75, 36);
		panelMessage.add(btnHeartEyeIcon);
		btnHeartEyeIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/heart_eye.png")));
		
		buttonScaredIcon = new JButton("");
		buttonScaredIcon.setBackground(new Color(192, 192, 192));
		buttonScaredIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/scared.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		buttonScaredIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/scared.png")));
		buttonScaredIcon.setContentAreaFilled(false);
		buttonScaredIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		buttonScaredIcon.setBounds(394, 96, 75, 36);
		panelMessage.add(buttonScaredIcon);
		
		buttonSadIcon = new JButton("");
		buttonSadIcon.setBackground(new Color(192, 192, 192));
		buttonSadIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "<img src='" + ChatGui.class.getResource("/image/sad.png") +"'></img>";
				try {
					chat.sendMessage(Encode.sendMessage(msg));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateChat_send_Symbol(msg);
			}
		});
		buttonSadIcon.setIcon(new javax.swing.ImageIcon(ChatGui.class.getResource("/image/sad.png")));
		buttonSadIcon.setContentAreaFilled(false);
		buttonSadIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
		buttonSadIcon.setBounds(476, 96, 75, 36);
		panelMessage.add(buttonSadIcon);
		
		btnChoose = new JButton("File");
		btnChoose.setBackground(Color.LIGHT_GRAY);
		btnChoose.setIcon(new ImageIcon(ChatGui.class.getResource("/image/flie.png")));
		btnChoose.setBounds(522, 156, 94, 36);
		panelMessage.add(btnChoose);
		btnChoose.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileChooser.showOpenDialog(frameChatGui);
				if (result == JFileChooser.APPROVE_OPTION) {
					isSendFile = true;
					String path_send = (fileChooser.getSelectedFile()
							.getAbsolutePath()) ;
					System.out.println(path_send);
					nameFile = fileChooser.getSelectedFile().getName();
					txtPath.setText(path_send);
				}
			}
		});
//		btnChoose.setBorder(BorderFactory.createEmptyBorder());
//		btnChoose.setContentAreaFilled(true);
		
		//action when press button Send
		btnSend.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (isSendFile)
					try {
						chat.sendMessage(Encode.sendFile(nameFile));
					} catch (Exception e) {
						e.printStackTrace();
					}

				if (isStop) {
					updateChat_send(txtMessage.getText().toString());
					txtMessage.setText(""); //reset text Send
					return;
				}
				String msg = txtMessage.getText();
				if (msg.equals(""))
					return;
				try {
					chat.sendMessage(Encode.sendMessage(msg));
					updateChat_send(msg);
					txtMessage.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txtMessage.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = txtMessage.getText();
					if (isStop) {
						updateChat_send(txtMessage.getText().toString());
						txtMessage.setText("");
						return;
					}
					if (msg.equals("")) {
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
						return;
					}
					try {
						chat.sendMessage(Encode.sendMessage(msg));
						updateChat_send(msg);
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
					} catch (Exception e) {
						txtMessage.setText("");
						txtMessage.setCaretPosition(0);
					}
				}
			}
		});

		btnDisConnect = new JButton("");
		btnDisConnect.setIcon(new ImageIcon(ChatGui.class.getResource("/image/btn.png")));
		btnDisConnect.setBackground(new Color(192, 192, 192));
		btnDisConnect.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnDisConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frameChatGui, "Bạn có chắc chắn đóng trò chuyện với tài khoản: "
						+ nameGuest, true);
				if (result == 0) {
					try {
						isStop = true;
						frameChatGui.dispose();
						chat.sendMessage(Tags.CHAT_CLOSE_TAG);
						chat.stopChat();
						System.gc();
						new ClientServer(nameUser);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		btnDisConnect.setBounds(608, 57, 41, 40);
		frameChatGui.getContentPane().add(btnDisConnect);

		progressSendFile = new JProgressBar(0, 100);
		progressSendFile.setBounds(172, 657, 388, 14);
		progressSendFile.setStringPainted(true);
		frameChatGui.getContentPane().add(progressSendFile);
		progressSendFile.setVisible(false);

		textState = new Label("");
		textState.setBounds(6, 570, 158, 22);
		textState.setVisible(false);
		frameChatGui.getContentPane().add(textState);

		lblReceive = new Label("Receiving ...");
		lblReceive.setBounds(566, 657, 83, 14);
		lblReceive.setVisible(false);
		frameChatGui.getContentPane().add(lblReceive);
		
		txtDisplayChat = new JTextPane();
		txtDisplayChat.setBackground(Color.BLACK);
		txtDisplayChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtDisplayChat.setEditable(false);
		txtDisplayChat.setContentType( "text/html" );
		txtDisplayChat.setMargin(new Insets(6, 6, 6, 6));
		txtDisplayChat.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		txtDisplayChat.setBounds(1, 1, 647, 268);
		appendToPane(txtDisplayChat, "<div class='clear' style='background-color:white'></div>"); //set default background
			    
		frameChatGui.getContentPane().add(txtDisplayChat);
	
		scrollPane = new JScrollPane(txtDisplayChat);
		scrollPane.setBounds(6, 118, 649, 318);
		frameChatGui.getContentPane().add(scrollPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		menuBar.setBounds(0, 0, 655, 40);
		frameChatGui.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Cài Đặt");
		mnNewMenu.setIcon(new ImageIcon(ChatGui.class.getResource("/image/menu.png")));
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Màu Sắc Đoạn Chat");
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnNewMenu.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Red");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setBackground(Color.RED);
			}
			
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Black");
		mntmNewMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setBackground(Color.BLACK);
			}
			
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Yellow");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setBackground(Color.YELLOW);
			}
			
		});
		
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Green");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setBackground(Color.GREEN);
			}
			
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Màu Khác....");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		mnNewMenu_2 = new JMenu("Định Dạng");
		mnNewMenu_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnNewMenu.add(mnNewMenu_2);
		
		mntmNewMenuItem_5 = new JMenuItem("In Đậm");
		mntmNewMenuItem_5.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setFont(new Font("Time New Roman",Font.BOLD,16));
				txtMessage.setFont(new Font("Time New Roman",Font.BOLD,16));
			}
		      }
		      );
		mnNewMenu_2.add(mntmNewMenuItem_5);
		
		mntmNewMenuItem_6 = new JMenuItem("In Nghiêng");
		mntmNewMenuItem_6.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtDisplayChat.setFont(new Font("Time New Roman",Font.ITALIC,16));
				txtMessage.setFont(new Font("Time New Roman",Font.ITALIC,16));
			}
		      }
		      );
		mnNewMenu_2.add(mntmNewMenuItem_6);
		
		mnNewMenu_3 = new JMenu("Công Cụ");
		mnNewMenu_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnNewMenu.add(mnNewMenu_3);
		
		mntmNewMenuItem_7 = new JMenuItem("Copy");
		mntmNewMenuItem_7.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doCopy();
			}
		      }
		      );
		mnNewMenu_3.add(mntmNewMenuItem_7);
		
		mntmNewMenuItem_8 = new JMenuItem("Cut");
		mntmNewMenuItem_8.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doCut();
			}
		      }
		      );
		mnNewMenu_3.add(mntmNewMenuItem_8);
		
		mntmNewMenuItem_9 = new JMenuItem("Paste");
		mntmNewMenuItem_9.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doPaste();
			}
		      }
		      );
		mnNewMenu_3.add(mntmNewMenuItem_9);
		
//		lblNewLabel = new JLabel("Đang hoạt động");
//		lblNewLabel.setBounds(252, 25, 90, 14);
//		frameChatGui.getContentPane().add(lblNewLabel);
//		

		
	
		
	}

	public class ChatRoom extends Thread {

		private Socket connect;
		private ObjectOutputStream outPeer;
		private ObjectInputStream inPeer;
		private boolean continueSendFile = true, finishReceive = false;
		private int sizeOfSend = 0, sizeOfData = 0, sizeFile = 0,
				sizeReceive = 0;
		private String nameFileReceive = "";
		private InputStream inFileSend;
		private DataFile dataFile;

		public ChatRoom(Socket connection, String name, String guest)
				throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
		}

		@Override
		public void run() {
			super.run();
			OutputStream out = null;
			while (!isStop) {
				try {
					inPeer = new ObjectInputStream(connect.getInputStream());
					Object obj = inPeer.readObject();
					if (obj instanceof String) {
						String msgObj = obj.toString();
						if (msgObj.equals(Tags.CHAT_CLOSE_TAG)) {
							isStop = true;
							Tags.show(frameChatGui, nameGuest 
									+ " đã đóng cửa trò chuyện với bạn! Cửa sổ này cũng sẽ bị đóng.", false);
							try {	
								isStop = true;
								frameChatGui.dispose();
								chat.sendMessage(Tags.CHAT_CLOSE_TAG);
								chat.stopChat();
								System.gc();
							} catch (Exception e) {
								e.printStackTrace();
							}
							connect.close();
							break;
						}
						if (Decode.checkFile(msgObj)) {
							isReceiveFile = true;
							nameFileReceive = msgObj.substring(10,
									msgObj.length() - 11);
							int result = Tags.show(frameChatGui, nameGuest
									+ " send file " + nameFileReceive
									+ " for you", true);
							if (result == 0) {
								File fileReceive = new File(URL_DIR + TEMP
										+ "/" + nameFileReceive);
								if (!fileReceive.exists()) {
									fileReceive.createNewFile();
								}
								String msg = Tags.FILE_REQ_ACK_OPEN_TAG
										+ Integer.toBinaryString(portServer)
										+ Tags.FILE_REQ_ACK_CLOSE_TAG;
								sendMessage(msg);
							} else {
								sendMessage(Tags.FILE_REQ_NOACK_TAG);
							}
						} else if (Decode.checkFeedBack(msgObj)) {
							btnChoose.setEnabled(false);

							new Thread(new Runnable() {
								public void run() {
									try {
										sendMessage(Tags.FILE_DATA_BEGIN_TAG);
										updateChat_notify("Bạn đang gửi tệp: " + nameFile);
										isSendFile = false;
										sendFile(txtPath.getText());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
						} else if (msgObj.equals(Tags.FILE_REQ_NOACK_TAG)) {
							Tags.show(frameChatGui, nameGuest
									+ " don't want receive file", false);
						} else if (msgObj.equals(Tags.FILE_DATA_BEGIN_TAG)) {
							finishReceive = false;
							lblReceive.setVisible(true);
							out = new FileOutputStream(URL_DIR + TEMP
									+ nameFileReceive);
						} else if (msgObj.equals(Tags.FILE_DATA_CLOSE_TAG)) {
							updateChat_receive("Bạn nhận được tệp: " + nameFileReceive + " with size " + sizeReceive + " KB");
							sizeReceive = 0;
							out.flush();
							out.close();
							lblReceive.setVisible(false);
							new Thread(new Runnable() {

								@Override
								public void run() {
									showSaveFile();
								}
							}).start();
							finishReceive = true;
//						} else if (msgObj.equals(Tags.FILE_DATA_CLOSE_TAG) && isFileLarge == true) {
//							updateChat_receive("File " + nameFileReceive + " too large to receive");
//							sizeReceive = 0;
//							out.flush();
//							out.close();
//							lblReceive.setVisible(false);
//							finishReceive = true;
						} else {
							String message = Decode.getMessage(msgObj);
							updateChat_receive(message);
						}
					} else if (obj instanceof DataFile) {
						DataFile data = (DataFile) obj;
						++sizeReceive;
						out.write(data.data);
					}
				} catch (Exception e) {
					File fileTemp = new File(URL_DIR + TEMP + nameFileReceive);
					if (fileTemp.exists() && !finishReceive) {
						fileTemp.delete();
					}
				}
			}
		}

		
		private void getData(String path) throws Exception {
			File fileData = new File(path);
			if (fileData.exists()) {
				sizeOfSend = 0;
				dataFile = new DataFile();
				sizeFile = (int) fileData.length();
				sizeOfData = sizeFile % 1024 == 0 ? (int) (fileData.length() / 1024)
						: (int) (fileData.length() / 1024) + 1;
				inFileSend = new FileInputStream(fileData);
			}
		}

		public void sendFile(String path) throws Exception {
			getData(path);
			textState.setVisible(true);
			if (sizeOfData > Tags.MAX_MSG_SIZE/1024) {
				textState.setText("File is too large...");
				inFileSend.close();
//				isFileLarge = true;
//				sendMessage(Tags.FILE_DATA_CLOSE_TAG);
				txtPath.setText("");
				btnChoose.setEnabled(true);
				isSendFile = false;
				inFileSend.close();
				return;
			}
			
			progressSendFile.setVisible(true);
			progressSendFile.setValue(0);
			
			textState.setText("Sending ...");
			do {
				System.out.println("sizeOfSend : " + sizeOfSend);
				if (continueSendFile) {
					continueSendFile = false;
//					updateChat_notify("If duoc thuc thi: " + String.valueOf(continueSendFile));
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								inFileSend.read(dataFile.data);
								sendMessage(dataFile);
								sizeOfSend++;
								if (sizeOfSend == sizeOfData - 1) {
									int size = sizeFile - sizeOfSend * 1024;
									dataFile = new DataFile(size);
								}
								progressSendFile
										.setValue((int) (sizeOfSend * 100 / sizeOfData));
								if (sizeOfSend >= sizeOfData) {
									inFileSend.close();
									isSendFile = true;
									sendMessage(Tags.FILE_DATA_CLOSE_TAG);
									progressSendFile.setVisible(false);
									textState.setVisible(false);
									isSendFile = false;
									txtPath.setText("");
									btnChoose.setEnabled(true);
									updateChat_notify("File sent complete");
									inFileSend.close();
								}
								continueSendFile = true;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			} while (sizeOfSend < sizeOfData);
		}

		private void showSaveFile() {
			while (true) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(frameChatGui);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile()
							.getAbsolutePath() + "/" + nameFileReceive );
					if (!file.exists()) {
						try {
							file.createNewFile();
							Thread.sleep(1000);
							InputStream input = new FileInputStream(URL_DIR
									+ TEMP + nameFileReceive);
							OutputStream output = new FileOutputStream(
									file.getAbsolutePath());
							copyFileReceive(input, output, URL_DIR + TEMP
									+ nameFileReceive);
						} catch (Exception e) {
							Tags.show(frameChatGui, "Tập tin nhận được của bạn bị l ",
									false);
						}
						break;
					} else {
						int resultContinue = Tags.show(frameChatGui,
								"File is exists. You want save file?", true);
						if (resultContinue == 0)
							continue;
						else
							break;
					}
				}
			}
		}
		
		
		//void send Message
		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			// only send text
			if (obj instanceof String) {
				String message = obj.toString();
				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile)
					isReceiveFile = false;
			} 
			// send attach file
			else if (obj instanceof DataFile) {
				outPeer.writeObject(obj);
				outPeer.flush();
			}
		}

		public void stopChat() {
			try {
				connect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copyFileReceive(InputStream inputStr, OutputStream outputStr,
			String path) throws IOException {
		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = inputStr.read(buffer)) > 0) {
			outputStr.write(buffer, 0, lenght);
		}
		inputStr.close();
		outputStr.close();
		File fileTemp = new File(path);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}
	}
	
	// send html to pane
	  private void appendToPane(JTextPane tp, String msg){
	    HTMLDocument doc = (HTMLDocument)tp.getDocument();
	    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
	    try {
	    	
	      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
	      tp.setCaretPosition(doc.getLength());
	      
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	  }
	  private void doCopy() {
		   str=txtMessage.getText();
		  
	   }
	  
	   private void doCut() {
		   str= txtMessage.getText();
		   txtMessage.setText(null);
		   
	   }
	   private void doPaste() {
		   try {
			((Appendable) txtMessage).append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
