package Views;

import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import ChatClient.MainGui;
import ChatTags.Encode;
import ChatTags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.UIManager;


import java.awt.Color;

public class LoginChat {
 private static String NAME_FAILED = "Vui Lòng Nhập Tên Và Thử Lại";
 private static String NAME_EXSIST = "THIS NAME IS ALREADY USED. PLEASE TRY AGAIN";
 private static String SERVER_NOT_START = "Chọn IP Server Kết Nối";
 private static String abc = "Vui Lòng Điền Đầy Đủ Thông Tin";
 private Pattern checkName = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");

 private JFrame frameLoginForm;
 private JTextField txtPort;
 private JLabel lblError;
 private String name = "", IP = "";
 private JTextField txtIP;	
 private JTextField txtUsername;	
 private JButton btnLogin;
 static String username = "";

 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     LoginChat window = new LoginChat(username);
     window.frameLoginForm.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 public LoginChat(String username) throws UnknownHostException {
  this.username = username;
  initialize();
 }

 private void initialize() throws UnknownHostException {
  frameLoginForm = new JFrame();
  frameLoginForm.getContentPane().setBackground(Color.WHITE);
  frameLoginForm.setTitle("Đăng Nhập Và Kết Nôi");
  frameLoginForm.setResizable(false);
  frameLoginForm.setBounds(570, 150, 518, 472);
  frameLoginForm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  frameLoginForm.getContentPane().setLayout(null);
  frameLoginForm.setVisible(true);
  
   URL url = LoginChat.class.getResource("/Image/icondangnhap.png");
	Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
	frameLoginForm.setIconImage((java.awt.Image) img);

  JLabel lblWelcome = new JLabel("Kết Nối Tài Khoản");
  lblWelcome.setForeground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
  lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 25));
  lblWelcome.setBounds(161, 36, 225, 48);
  frameLoginForm.getContentPane().add(lblWelcome);

  JLabel lblHostServer = new JLabel("IP Server:");
  lblHostServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblHostServer.setBounds(30, 195, 86, 20);
  frameLoginForm.getContentPane().add(lblHostServer);

  JLabel lblPortServer = new JLabel("Port Server:");
  lblPortServer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblPortServer.setBounds(30, 250, 79, 14);
  frameLoginForm.getContentPane().add(lblPortServer);

  txtPort = new JTextField();
  txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtPort.setText("8080");
  txtPort.setEditable(false);
  txtPort.setColumns(10);
  txtPort.setBounds(199, 243, 187, 28);
  frameLoginForm.getContentPane().add(txtPort);

  JLabel lblUserName = new JLabel("Tên Tài Khoản:");
  lblUserName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  lblUserName.setBounds(27, 134, 100, 38);
  frameLoginForm.getContentPane().add(lblUserName);
//  lblUserName.setIcon(new javax.swing.ImageIcon(Login.class.getResource("/image/user.png")));

  lblError = new JLabel("");
  lblError.setForeground(Color.RED);
  lblError.setBounds(161, 281, 282, 36);
  frameLoginForm.getContentPane().add(lblError);

  txtIP = new JTextField();
  txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
  txtIP.setEditable(false);
  txtIP.setFont(new Font("Tahoma", Font.PLAIN, 13));
  txtIP.setBounds(144, 193, 282, 28);
  frameLoginForm.getContentPane().add(txtIP);
  txtIP.setColumns(10);

  txtUsername = new JTextField(username);
//  txtUsername.setEditable(false);
  txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
  txtUsername.setColumns(10);
  txtUsername.setBounds(144, 138, 282, 30);
  frameLoginForm.getContentPane().add(txtUsername);

  btnLogin = new JButton("K\u1EBFt N\u1ED1i");
  btnLogin.setBackground(Color.LIGHT_GRAY);
  btnLogin.setIcon(new ImageIcon(LoginChat.class.getResource("/image/cn2.png")));
  btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
  btnLogin.addActionListener(new ActionListener() {

   public void actionPerformed(ActionEvent arg0) {
    name = txtUsername.getText();
    lblError.setVisible(false);
    IP = txtIP.getText();
    if(name.equals("") == true && IP.equals("") == true) {
    	lblError.setText(abc);
    	lblError.setVisible(true);
    	
    }else if(IP.equals("")== true) {
    	lblError.setText(SERVER_NOT_START);
    	lblError.setVisible(true);
    }else if(name.equals("") == true ) {
    	lblError.setText(NAME_FAILED);
    	lblError.setVisible(true);
    }else {
    	//must edit here
        if (checkName.matcher(name).matches() && !IP.equals("")) {
         try {
          Random rd = new Random();
          int portPeer = 10000 + rd.nextInt() % 1000;
          InetAddress ipServer = InetAddress.getByName(IP);
          int portServer = Integer.parseInt("8080");
          Socket socketClient = new Socket(ipServer, portServer);

          String msg = Encode.getCreateAccount(name, Integer.toString(portPeer));
          ObjectOutputStream serverOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
          serverOutputStream.writeObject(msg);
          serverOutputStream.flush();
          ObjectInputStream serverInputStream = new ObjectInputStream(socketClient.getInputStream());
          msg = (String) serverInputStream.readObject();

          socketClient.close();
          if (msg.equals(Tags.SESSION_DENY_TAG)) {
           lblError.setText(NAME_EXSIST);
           lblError.setVisible(true);
           return;
          }
          new MainGui(IP, portPeer, name, msg);
          //						new menuGUI(IP, portPeer, "toan", msg);
          frameLoginForm.dispose();
         } catch (Exception e) {
          lblError.setText(SERVER_NOT_START);
          lblError.setVisible(true);
          e.printStackTrace();
         }
        }
        else {
         lblError.setText(NAME_FAILED);
         lblError.setVisible(true);
         lblError.setText(NAME_FAILED);
        }
    }
   }
  });
  
  btnLogin.setBounds(179, 327, 151, 48);
  frameLoginForm.getContentPane().add(btnLogin);
  lblError.setVisible(false);


 }
}