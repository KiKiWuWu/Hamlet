package parser.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	//general stuff
	private final String DATABASE_NAME = "hamlet";
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USER_NAME = "root";
	private final String PASSWORD = "root";
	
	//TABLES
	private final String TABLE_TWEET = "tweets";
	private final String TABLE_PERSON = "person";
	
	//COLUMNS
	private final String TWEET_ID = "id";
	private final String TWEET_PID = "person_id";
	private final String TWEET_TEXT = "text";
	private final String TWEET_TYPE = "type";
	private final String TWEET_REF_TWEET = "reference_tweet";
	private final String TWEET_TWEETID = "tweet_id";
	private final String TWEET_REF_TWEET_ID = "ref_tweet_id";
	
	private final String PERSON_ID = "id";
	private final String PERSON_NAME = "name";
	private final String PERSON_KEY1 = "key_1";
	private final String PERSON_KEY2 = "key_2";
	private final String PERSON_KEY3 = "key_3";
	private final String PERSON_KEY4 = "key_4";
	
	private Connection connection;
	private Statement statement;
	private ResultSet results;
	private PreparedStatement insertPerson;
	private PreparedStatement insertLink;

	public Database(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL+DATABASE_NAME, USER_NAME, PASSWORD);
			statement = connection.createStatement();
			prepareStatements();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void prepareStatements(){
		try {
			insertPerson = connection.prepareStatement("INSERT INTO " + TABLE_PERSON 
					+ " (" + PERSON_NAME + ", " + PERSON_KEY1 + ", " + PERSON_KEY2+ ", " + PERSON_KEY3+ ", " + PERSON_KEY4+ ") "
					+ "VALUES "
					+ "( "
					+ "? , ? , ? , ?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearDatabase(){
		try {
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");
			statement.execute("TRUNCATE TABLE " + TABLE_TWEET);
			statement.execute("TRUNCATE TABLE " + TABLE_PERSON);
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean insertIntoPerson(String name, String[] keys){
		if(keys.length != 4){ return false;}
		try {
			insertPerson.setString(1, name);
			for(int i = 0; i < keys.length; i++){
				insertPerson.setString(i + 2, keys[i]);
			}
			insertPerson.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
