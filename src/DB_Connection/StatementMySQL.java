package DB_Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.ClientBookTickets;
import Model.InfoTrain;
import Model.StatusTickets;
import Model.TypeTickets;
import Model.User;


public class StatementMySQL {

	public static int addData(String statement) {
		Connection connection = ConnectMySQL.getConnection();
		int result = 0;
		Statement st = null;
		if(connection!= null) {
			try {
				st = connection.createStatement();
				result = st.executeUpdate(statement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return result;
	}
	
	public static List<User> LoadDataUser(String statement) {
		Connection connection = ConnectMySQL.getConnection();
		
		List<User> resultAccounts = new ArrayList<User>();
		ResultSet resultSet = null;
		Statement st = null;
		
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(statement);
				if(resultSet != null) {
					while(resultSet.next()) {  // Ktra xe trong list có dữ liệu hay ko
						User account = new User();
						account.setUsername(resultSet.getString("username"));
						account.setPassword(resultSet.getString("password"));
						account.setFullname(resultSet.getString("fullname"));
						account.setCccd(resultSet.getString("cccd"));
						account.setPhonenumber(resultSet.getString("phonenumber"));
						account.setEmail(resultSet.getString("email"));
						resultAccounts.add(account);
					}
					return resultAccounts;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return null;
	}
	
	
	public static List<InfoTrain> LoadDataInfoTrain(String statement) {
		Connection connection = ConnectMySQL.getConnection();
		
		List<InfoTrain> resultInfoTrain = new ArrayList<InfoTrain>();
		ResultSet resultSet = null;
		Statement st = null;
		
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(statement);
				if(resultSet != null) {
					while(resultSet.next()) {  // Ktra xe trong list có dữ liệu hay ko
						InfoTrain infoTrain = new InfoTrain();
						infoTrain.setMash(resultSet.getString("mash"));
						infoTrain.setNameTrain(resultSet.getString("nameTrain"));
						infoTrain.setLocationStartTrain(resultSet.getString("locationStartTrain"));
						infoTrain.setLocationEndTrain(resultSet.getString("locationEndTrain"));
						infoTrain.setTimeStartTrain(resultSet.getString("timeStartTrain"));
						infoTrain.setExpectedendTime(resultSet.getString("expectedendTime"));
						infoTrain.setDirection(resultSet.getString("direction"));
						resultInfoTrain.add(infoTrain);
					}
					return resultInfoTrain;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return null;
	}
	
	
	public static List<StatusTickets> LoadDataStatusTickets(String statement) {
		Connection connection = ConnectMySQL.getConnection();
		
		List<StatusTickets> resultStatusTickets = new ArrayList<StatusTickets>();
		ResultSet resultSet = null;
		Statement st = null;
		
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(statement);
				if(resultSet != null) {
					while(resultSet.next()) {  // Ktra xe trong list có dữ liệu hay ko
						StatusTickets statusTickets = new  StatusTickets();
						statusTickets.setIdstatus(resultSet.getString("idstatus"));
						statusTickets.setMash(resultSet.getString("mash"));
						statusTickets.setIdtype(resultSet.getString("idtype"));
						int total = (int) resultSet.getInt("totaltickets");
						statusTickets.setTotaltickets(total);
						int booked = (int) resultSet.getInt("booked");
						statusTickets.setBooked(booked);
						resultStatusTickets.add(statusTickets);
					}
					return resultStatusTickets;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return null;
	}
	
	public static List<TypeTickets> LoadDataTypeTickets(String statement){
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
		List<TypeTickets> resultTypeTickets = new ArrayList<TypeTickets>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(statement);
				if(resultSet != null) {
					while(resultSet.next()) {
						TypeTickets typeTickets = new TypeTickets();
						typeTickets.setIdtypes(resultSet.getString("idtype"));
						typeTickets.setType(resultSet.getString("type"));
						typeTickets.setPrice(resultSet.getInt("price"));
						resultTypeTickets.add(typeTickets);
					}
					return resultTypeTickets;
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return null;
	}
	
	public static int signUpTickets(String statement) {
		Connection connection = ConnectMySQL.getConnection();
		int result = 0;
		Statement st = null;
		if(connection!= null) {
			try {
				st = connection.createStatement();
				result = st.executeUpdate(statement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return result;
	}
	
	public static void checkStatusTickets() {
		Connection connection = ConnectMySQL.getConnection();
		Statement st = null;
		String sql = "UPDATE  `clientbooktickets` SET `status` = '"+true+"' WHERE `day` <= CURDATE()";
//		UPDATE  `clientbooktickets` SET `status` = '"+true+"' WHERE `day` <= CURDATE() AND `timestart` <= CURTIME() AND MINUTE(`timestart`) <= MINUTE(CURTIME())
		if(connection!= null) {
			try {
				st = connection.createStatement();
				st.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
	}
	
	public static List<ClientBookTickets> dataUser(String user, String status){
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
		String sql = "SELECT * FROM `clientbooktickets` WHERE `username` = '"+user+"' AND `status` = '"+status+"'";
		List<ClientBookTickets> resultUser = new ArrayList<ClientBookTickets>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						ClientBookTickets clientBookTickets = new ClientBookTickets();
						clientBookTickets.setUsername(resultSet.getString("username"));
						clientBookTickets.setIdtickets(resultSet.getString("idtickets"));
						clientBookTickets.setMash(resultSet.getString("mash"));
						clientBookTickets.setIdtype(resultSet.getString("idtype"));
						clientBookTickets.setLocationStartClient(resultSet.getString("locationstartclient"));
						clientBookTickets.setLocationEndClient(resultSet.getString("locationendclient"));
						clientBookTickets.setSeats(resultSet.getString("seats"));
						clientBookTickets.setTimeStart(resultSet.getString("timestart"));
						clientBookTickets.setExpectedendTime(resultSet.getString("expectedendTime"));
						clientBookTickets.setDay(resultSet.getString("day"));
						clientBookTickets.setPriceTickets(resultSet.getInt("pricetickets"));
						clientBookTickets.setStatus(resultSet.getString("status"));
						resultUser.add(clientBookTickets);
					}
					return resultUser;
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return null;
	}
	
	public static String getNameTrain(String mash) {
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
		String sql = "SELECT * FROM `infotrain` WHERE `mash` = '"+mash+"'";
		List<InfoTrain> result = new ArrayList<InfoTrain>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						return resultSet.getString("nameTrain");
					}
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return "";
	}
	
	public static String getTypeTrain(String type) {
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
		String sql = "SELECT * FROM `typetickets` WHERE `idtype` = '"+type+"'";
		List<TypeTickets> result = new ArrayList<TypeTickets>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						return resultSet.getString("type");
					}
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return "";
	}
	
	
	public static List<ClientBookTickets> dataUserByID(String idtickets, String username, String status){
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
		String sql = "SELECT * FROM `clientbooktickets` WHERE `idtickets` = '"+idtickets+"' AND `status` = '"+status+"' AND `username` = '"+username+"'";
		List<ClientBookTickets> resultUser = new ArrayList<ClientBookTickets>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						ClientBookTickets clientBookTickets = new ClientBookTickets();
						clientBookTickets.setUsername(resultSet.getString("username"));
						clientBookTickets.setIdtickets(resultSet.getString("idtickets"));
						clientBookTickets.setMash(resultSet.getString("mash"));
						clientBookTickets.setIdtype(resultSet.getString("idtype"));
						clientBookTickets.setLocationStartClient(resultSet.getString("locationstartclient"));
						clientBookTickets.setLocationEndClient(resultSet.getString("locationendclient"));
						clientBookTickets.setSeats(resultSet.getString("seats"));
						clientBookTickets.setTimeStart(resultSet.getString("timestart"));
						clientBookTickets.setExpectedendTime(resultSet.getString("expectedendTime"));
						clientBookTickets.setDay(resultSet.getString("day"));
						clientBookTickets.setPriceTickets(resultSet.getInt("pricetickets"));
						clientBookTickets.setStatus(resultSet.getString("status"));
						resultUser.add(clientBookTickets);
					}
					return resultUser;
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return null;
	}
	
	public static List<ClientBookTickets> dataUserByTime(String year, String month, String day, String username, String stauts){
		Connection connection = ConnectMySQL.getConnection();
		ResultSet resultSet = null;
		Statement st = null;
//		String sql = "SELECT * FROM `clientbooktickets` WHERE `idtickets` = '"+idtickets+"' AND `status` = 'true'";
		String sql = null;
		if(day.length()==0 && month.length()==0) {
			 sql="SELECT * FROM `clientbooktickets` WHERE Year(day)= '"+year+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(day.length()==0 && year.length()==0) {
			sql="SELECT * FROM `clientbooktickets` WHERE MONTH(day)= '"+month+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(month.length()==0 && year.length()==0) {
			sql="SELECT * FROM `clientbooktickets` WHERE DAY(day)= '"+day+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(day.length()==0) {
			sql="SELECT * FROM `clientbooktickets` WHERE YEAR(day)= '"+year+"' AND MONTH(day)= '"+month+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(month.length()==0) {
			sql="SELECT * FROM `clientbooktickets` WHERE DAY(day)= '"+day+"' AND YEAR(day)= '"+year+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(year.length()==0) {
			sql= "SELECT * FROM `clientbooktickets` WHERE DAY(day)= '"+day+"' AND MONTH(day)= '"+month+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		else if(day.length()!=0 && month.length()!=0 && year.length()!=0) {
			sql="SELECT * FROM `clientbooktickets` WHERE DAY(day)= '"+day+"' AND MONTH(day)= '"+month+" AND YEAR(day)= '"+year+"' AND `status` = '"+stauts+"' AND `username` = '"+username+"'";
		}
		List<ClientBookTickets> resultUser = new ArrayList<ClientBookTickets>();
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet  = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						ClientBookTickets clientBookTickets = new ClientBookTickets();
						clientBookTickets.setUsername(resultSet.getString("username"));
						clientBookTickets.setIdtickets(resultSet.getString("idtickets"));
						clientBookTickets.setMash(resultSet.getString("mash"));
						clientBookTickets.setIdtype(resultSet.getString("idtype"));
						clientBookTickets.setLocationStartClient(resultSet.getString("locationstartclient"));
						clientBookTickets.setLocationEndClient(resultSet.getString("locationendclient"));
						clientBookTickets.setSeats(resultSet.getString("seats"));
						clientBookTickets.setTimeStart(resultSet.getString("timestart"));
						clientBookTickets.setExpectedendTime(resultSet.getString("expectedendTime"));
						clientBookTickets.setDay(resultSet.getString("day"));
						clientBookTickets.setPriceTickets(resultSet.getInt("pricetickets"));
						clientBookTickets.setStatus(resultSet.getString("status"));
						resultUser.add(clientBookTickets);
					}
					return resultUser;
				}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					st.close();
					resultSet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return null;
	}
	
	public static int deleteByID(String idtickets) {
		Connection connection = ConnectMySQL.getConnection();
		int result = 0;
		Statement st = null;
		String sql = "DELETE FROM `clientbooktickets` WHERE `idtickets` = '"+idtickets+"'";
		if(connection!= null) {
			try {
				st = connection.createStatement();
				result = st.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return result;
	}
	
	public static int getDataMonth(int month) {
		Connection connection = ConnectMySQL.getConnection();
		Statement st = null;
		ResultSet resultSet = null;
		int num = 0;
		Random rd = new Random();
		int number = rd.nextInt(901) + 100;
		String sql = "SELECT COUNT(`day`) as 'month' FROM `clientbooktickets` WHERE MONTH(`day`) = "+month+";";
//		UPDATE  `clientbooktickets` SET `status` = '"+true+"' WHERE `day` <= CURDATE() AND `timestart` <= CURTIME() AND MINUTE(`timestart`) <= MINUTE(CURTIME())
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						num = resultSet.getInt("month");
//						System.out.println(num);
					}
				}
				num = number;
				return num;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return 0;
	}
	
	public static int checkDataSeats(String mash, String seats, String day) {
		Connection connection = ConnectMySQL.getConnection();
		Statement st = null;
		ResultSet resultSet = null;
		int num = 0;
		String sql="SELECT * FROM `clientbooktickets` WHERE `mash` = '"+mash+"' AND `seats` = '"+seats+"' AND `day` = '"+day+"'";
		System.out.println(sql);
		if(connection!= null) {
			try {
				st = connection.createStatement();
				resultSet = st.executeQuery(sql);
				if(resultSet != null) {
					while(resultSet.next()) {
						System.out.println("Chạy");
					    num+=1;
//						System.out.println(num);
					}
				}
				System.out.println(num);
				return num;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					st.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return num;
	}
}
