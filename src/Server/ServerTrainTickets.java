package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import DB_Connection.StatementMySQL;
import Model.InfoTrain;
import Model.StatusTickets;
import Model.TypeTickets;
import Model.User;

public class ServerTrainTickets {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8002);
			System.out.println("=>>> TURN ON SERVER_TRAIN_TICKETS");
			while (true) {
				Socket socket = server.accept();
				System.out.println("==== New connection from " + socket.getInetAddress().getHostAddress() + " =====");
				System.out.println("SERVER LISTENING...");
				ClientHandlerTrainTickets clientHandler = new ClientHandlerTrainTickets(socket);
				clientHandler.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

class ClientHandlerTrainTickets extends Thread {
	private Socket clientSocket;

	public ClientHandlerTrainTickets(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				String[] data = inputLine.split(",");
				String type = data[0];
				
				if(type.equals("checkSeats")) {
					int indexSeats =0;
					int total = 0;
					String priceType = "";
					String mashClient =  data[1];
					String idtype = data[2];
					String dayClient = data[3];
					String sql = "SELECT * FROM `statustickest` WHERE `mash` = '"+mashClient+"' AND `idtype` = '"+idtype+"'";
					StatementMySQL statementMySQ2 = new StatementMySQL();
					List<StatusTickets> statusTickets = new ArrayList<StatusTickets>();
					statusTickets = statementMySQ2.LoadDataStatusTickets(sql);
					for(int i=0;i<statusTickets.size();i++) {
						indexSeats =statusTickets.get(i).getBooked();
						total = statusTickets.get(i).getTotaltickets();
					}
					int nextIndex = indexSeats +1;
					if(nextIndex < total) {
						out.println(Integer.toString(nextIndex));
					}else {
						out.println(Integer.toString(0));
					}
				}else if(type.equals("getPrice")) {
					int price =0;
					String idtype = data[1];
					String sql = "SELECT * FROM `typetickets` WHERE `idtype` = '"+idtype+"'";
					StatementMySQL statementMySQ = new StatementMySQL();
					List<TypeTickets> typeTickets = new ArrayList<TypeTickets>();
					typeTickets = statementMySQ.LoadDataTypeTickets(sql);
					for(int i=0;i<typeTickets.size();i++) {
						price = typeTickets.get(i).getPrice();
						out.println(Integer.toString(price));
					}
				}else if(type.equals("typeTickets")) {
					String idtype = data[1];
					String type1 = "";
					String sql = "SELECT * FROM `typetickets` WHERE `idtype` = '"+idtype+"'";
					StatementMySQL statementMySQ = new StatementMySQL();
					List<TypeTickets> typeTickets = new ArrayList<TypeTickets>();
					typeTickets = statementMySQ.LoadDataTypeTickets(sql);
					for(int i=0;i<typeTickets.size();i++) {
						type1 = typeTickets.get(i).getType();
					}
					out.println(type1);
				}else if(type.equals("nameTrain")) {
					String mash = data[1];
					String nameTrain = "";
					String sql = "SELECT * FROM `infotrain` WHERE `mash` = '"+mash+"'";
					StatementMySQL statementMySQ = new StatementMySQL();
					List<InfoTrain> infoTrain = new ArrayList<InfoTrain>();
					infoTrain = statementMySQ.LoadDataInfoTrain(sql);
					for(int i =0; i< infoTrain.size(); i++) {
						nameTrain = infoTrain.get(i).getNameTrain();
					}
					out.println(nameTrain);
					
				}else if(type.equals("login")) {
					String username = data[1];
					String password = data[2];
					System.out.println("Info Login of Client: " + username + " - " + password);
					String sql = "SELECT * FROM `account` WHERE `username`='" + username + "' AND `password`='"+ password +"'";
//					System.out.println(sql);
					StatementMySQL statementMySQL = new StatementMySQL();
					List<User> user = new ArrayList<User>();
					user = statementMySQL.LoadDataUser(sql);
					if (user.size() > 0) {
						out.println("Login Success");
					} else {
						out.println("Error");
					}
				}else if (type.equals("signUpTickets")) {
					String username = data[1];
					String idTickets = data[2];
					String mash = data[3];
					String idtypes = data[4];
					String locationStart = data[5];
					String locationEnd = data[6];
					String seats = data[7];
					String timeStart = data[8];
					String expectedendTime = data[9];
					String dayTrain = data[10];
					String priceTickets = data[11];
					String status = data[12];
					
					String sql = "INSERT INTO `clientbooktickets` (`idtickets`,`username`,`mash`,`idtype`,`locationstartclient`,`locationendclient`,`seats`,`timestart`,`expectedendtime`,`day`,`pricetickets`,`status`)"
							+ " VALUES('"+idTickets+"','"+username+"','"+mash+"','"+idtypes+"','"+locationStart+"','"+locationEnd+"','"+seats+"','"+timeStart+"','"+expectedendTime+"','"+dayTrain+"',"+priceTickets+",'"+status+"')";
					System.out.println(sql);
					StatementMySQL statementMySQ = new StatementMySQL();
					int result = statementMySQ.signUpTickets(sql);
					if(result == 1) {
						out.println("Success");
					}else {
						out.println("Error");
					}
					
				}
				
				
			}
			clientSocket.close();
		} catch (

		IOException e) {
//			e.printStackTrace();
		}
	}

}

