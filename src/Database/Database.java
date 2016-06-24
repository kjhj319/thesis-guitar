package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import parsing.AnalyzeTab;

public class Database {
	  private String user = "root";
	  private String pass = "root";
	  
	  String connection = "jdbc:mysql://localhost:3306/thesis";
	  Connection connect;
	  Statement stat;
	  
	  public void initialize() throws ClassNotFoundException{
			  Class.forName("com.mysql.jdbc.Driver");
	  }
	  
	  public ArrayList<String> getLogin(String username,String password) throws ClassNotFoundException
	  {
		  PreparedStatement stat = null;
		ArrayList<String> information = new ArrayList<String>();
			String query = "select * from user where (Username = ? or Email = ?) and Password = SHA( ? )";
			
			try
			{
			  
				connect = DriverManager.getConnection(connection, user, pass);
				stat = connect.prepareStatement(query);
				stat.setString(1, username);
				stat.setString(2, username);
				stat.setString(3, password);
				
				ResultSet resultSet = stat.executeQuery();
				
				if(resultSet.next()){
					information.add(resultSet.getString("Username"));
					information.add(password);
					information.add(resultSet.getString("Email"));
					information.add(resultSet.getString("Tabs_Uploaded"));
				}
				else{
				}
				
				connect.close();
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
			}
				
		return information;
	  }
	  
	  public int getRegistration(String username, String password, String email) throws ClassNotFoundException
	  {
		  
		String queryy = "insert into user (Username, Password, Email) values (?"
						+ ", SHA( ? ), ? );";
		//code for accessing db

		if(getLogin(username, password).size() > 1 ||
		   getLogin(email, password).size() > 1){
			return 2;
		}
		
		try
		{
			connect = DriverManager.getConnection(connection, user, pass);
			PreparedStatement stat = connect.prepareStatement(queryy);
			stat.setString(1, username);
			stat.setString(2, password);
			stat.setString(3, email);
		    stat.executeUpdate();
		  connect.close();
		}
		
		catch(SQLException e)
		{
			System.out.println(e);
		}
			
		return 1;
	  }
	  
	  public int insertGuitarTab(String title, String artist, String date, String username, int difficulty, String tab){
		  
		  String queryy = "insert into `guitar tablatures` (Title, Composer, Date_Uploaded, Username, `Analysis_Results`, Difficulty, Tab) values (?"
					+ ", ?, ?, ?, ?, ?, ?);";
	//code for accessing db

		try
		{
			connect = DriverManager.getConnection(connection, user, pass);
			PreparedStatement stat = connect.prepareStatement(queryy);
			stat.setString(1, title);
			stat.setString(2, artist);
			stat.setString(3, date);
			stat.setString(4, username);
			stat.setInt(5, 1);
			stat.setInt(6, difficulty);
			stat.setString(7, tab);
		    stat.executeUpdate();
		  connect.close();
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
			return 2;
		}
		  
		  return 1;
	  }
	  
	  public int insertAnalyzeResult(String title, String artist, HashMap<String, Double> resultList){
		  
		  String queryy = "insert into `analysis results` (Title, Composer, ratioOfChord, "
		  											   + "ratioOfFrets, freqOfSlur, freqOfTech, "
		  											   + "localDist, `chordDist-1`, `chordDist-2`, "
		  											   + "`chordDist-3`, `chordDist-4`, `chordDist-5`,"
		  											   + "`chordDist-6`, `fingerDist-0`, `fingerDist-1`,"
		  											   + "`fingerDist-2`, `fretDist-0`, `fretDist-1`,"
		  											   + "`fretDist-2`, `fretDist-3`) "
		  											   + "values (?, ?, ?, "
		  											   + "?, ?, ?,"
		  											   + "?, ?, ?,"
		  											   + "?, ?, ?,"
		  											   + "?, ?, ?,"
		  											   + "?, ?, ?,"
		  											   + "?, ?);";
	//code for accessing db

		try
		{
			connect = DriverManager.getConnection(connection, user, pass);
			PreparedStatement stat = connect.prepareStatement(queryy);
			stat.setString(1, title);
			stat.setString(2, artist);
			stat.setFloat(3, resultList.get("ratioOfChord").floatValue());
			stat.setFloat(4, 0);
			stat.setInt(5, resultList.get("freqOfSlur").intValue());
			stat.setInt(6, resultList.get("freqOfTech").intValue());
			stat.setDouble(7, resultList.get("localDist"));
			stat.setDouble(8, resultList.get("chordDist-1"));
			stat.setDouble(9, resultList.get("chordDist-2"));
			stat.setDouble(10, resultList.get("chordDist-3"));
			stat.setDouble(11, resultList.get("chordDist-4"));
			stat.setDouble(12, resultList.get("chordDist-5"));
			stat.setDouble(13, resultList.get("chordDist-6"));
			stat.setDouble(14, resultList.get("fingerDist-0"));
			stat.setDouble(15, resultList.get("fingerDist-1"));
			stat.setDouble(16, resultList.get("fingerDist-2"));
			stat.setDouble(17, resultList.get("fretDist-0"));
			stat.setDouble(18, resultList.get("fretDist-1"));
			stat.setDouble(19, resultList.get("fretDist-2"));
			stat.setDouble(20, resultList.get("fretDist-3"));
			stat.executeUpdate();
		  connect.close();
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
			return 2;
		}
		  
		  return 1;
	  }
	  
	  public ArrayList<HashMap<String, String>> getAllTabs(String order){
		  ArrayList<HashMap<String, String>> tabs = new ArrayList<HashMap<String, String>>();
		  
		  String query = "select Title, Composer, Date_Uploaded, Username, Difficulty from `guitar tablatures` order by Difficulty";
		  HashMap<String, String> analysis;
			try
			{
			  
				
				query = query + " " + order;
				
				connect = DriverManager.getConnection(connection, user, pass);
				stat = connect.prepareStatement(query);
				
				ResultSet resultSet = stat.executeQuery(query);
				
				while(resultSet.next()){
					analysis = new HashMap<String, String>();
					analysis.put("title", resultSet.getString("Title"));
					analysis.put("artist", resultSet.getString("Composer"));
					analysis.put("date", resultSet.getString("Date_Uploaded"));
					analysis.put("username", resultSet.getString("Username"));
					analysis.put("difficulty", String.valueOf(resultSet.getInt("Difficulty")));
					tabs.add(analysis);
				}
				
				System.out.println(tabs.size());
				connect.close();
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
			}
		  
		  return tabs;
	  }
	  
	  public HashMap<String, String> getTab(String title, String artist){
		  HashMap<String, String> tab = new HashMap<String, String>();
		  
		  String query = "select * from `guitar tablatures` g , `analysis results` a where (g.Title = a.Title and g.Composer = a.Composer)"
		  																			+ "and (g.Title = ? and g.Composer = ?)";
		  HashMap<String, String> analysis = new HashMap<String, String>();
			try
			{
				connect = DriverManager.getConnection(connection, user, pass);
				PreparedStatement stat = connect.prepareStatement(query);
				stat.setString(1, title);
				stat.setString(2, artist);
				
				ResultSet resultSet = stat.executeQuery();
				
				if(resultSet.next()){
					Double totalTab = resultSet.getDouble("freqOfSlur") + resultSet.getDouble("freqOfTech");
					
					analysis = new HashMap<String, String>();
					analysis.put("title", resultSet.getString("Title"));
					analysis.put("artist", resultSet.getString("Composer"));
					analysis.put("date", resultSet.getString("Date_Uploaded"));
					analysis.put("username", resultSet.getString("Username"));
					analysis.put("difficulty", String.valueOf(resultSet.getInt("Difficulty")));
					analysis.put("ratioOfChord", String.valueOf(resultSet.getFloat("ratioOfChord")));
					analysis.put("ratioOfFrets", String.valueOf(resultSet.getFloat("ratioOfFrets")));
					analysis.put("freqOfSlur", String.valueOf((double)resultSet.getInt("freqOfSlur") / totalTab));
					analysis.put("freqOfTech", String.valueOf((double)resultSet.getInt("freqOfTech") / totalTab));
					analysis.put("localDist", String.valueOf(resultSet.getDouble("localDist")));
					analysis.put("chordDist_1", String.valueOf(resultSet.getDouble("chordDist-1")));
					analysis.put("chordDist_2", String.valueOf(resultSet.getDouble("chordDist-2")));
					analysis.put("chordDist_3", String.valueOf(resultSet.getDouble("chordDist-3")));
					analysis.put("chordDist_4", String.valueOf(resultSet.getDouble("chordDist-4")));
					analysis.put("chordDist_5", String.valueOf(resultSet.getDouble("chordDist-5")));
					analysis.put("chordDist_6", String.valueOf(resultSet.getDouble("chordDist-6")));
					analysis.put("fingerDist_0", String.valueOf(resultSet.getDouble("fingerDist-0")));
					analysis.put("fingerDist_1", String.valueOf(resultSet.getDouble("fingerDist-1")));
					analysis.put("fingerDist_2", String.valueOf(resultSet.getDouble("fingerDist-2")));
					analysis.put("fretDist_0", String.valueOf(resultSet.getDouble("fretDist-0")));
					analysis.put("fretDist_1", String.valueOf(resultSet.getDouble("fretDist-1")));
					analysis.put("fretDist_2", String.valueOf(resultSet.getDouble("fretDist-2")));
					analysis.put("fretDist_3", String.valueOf(resultSet.getDouble("fretDist-3")));
					analysis.put("tab", resultSet.getString("Tab"));
				}
				
				System.out.println(String.valueOf(resultSet.getDouble("chordDist-1")));
				connect.close();
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
			}
		  System.out.println(analysis.get("title") + analysis.get("artist"));
		  return analysis;
	  }
	  
	  public ArrayList<HashMap<String, String>> searchAllTabs(String search, String order, String type){
		  ArrayList<HashMap<String, String>> tabs = new ArrayList<HashMap<String, String>>();
		  
		  String addQuery = "";
		  
		  if(type.equals("None")){
			  addQuery = "Title LIKE ? or Composer LIKE ? or Username LIKE ? or Difficulty LIKE ?";
		  }
		  else if(type.equals("Artist")){
			  addQuery = "Composer LIKE ?";
		  }
		  else if(type.equals("Title")){
			  addQuery = "Title LIKE ?";
		  }
		  else if(type.equals("Difficulty")){
			  addQuery = "Difficulty LIKE ?";
		  }
		  else if(type.equals("Uploader")){
			  addQuery = "Username LIKE ?";
		  }
		  
		  String query = "select Title, Composer, Date_Uploaded, Username, Difficulty from `guitar tablatures`"
	  			   + "where " + addQuery + " order by Difficulty";
		  
		  HashMap<String, String> analysis;
			try
			{
				query = query + " " + order;
				
				connect = DriverManager.getConnection(connection, user, pass);
				PreparedStatement stat = connect.prepareStatement(query);
				stat.setString(1, "%" + search + "%");
				
				if(type.equals("None")){
					stat.setString(2, "%" + search + "%");
					stat.setString(3, "%" + search + "%");
					stat.setString(4, "%" + search + "%");
				}
				
				ResultSet resultSet = stat.executeQuery();
				
				while(resultSet.next()){
					analysis = new HashMap<String, String>();
					analysis.put("title", resultSet.getString("Title"));
					analysis.put("artist", resultSet.getString("Composer"));
					analysis.put("date", resultSet.getString("Date_Uploaded"));
					analysis.put("username", resultSet.getString("Username"));
					analysis.put("difficulty", String.valueOf(resultSet.getInt("Difficulty")));
					tabs.add(analysis);
				}
				
				System.out.println(tabs.size());
				connect.close();
			}
			
			catch(SQLException e)
			{
				System.out.println(e);
			}
		  
		  return tabs;
	  }
}
