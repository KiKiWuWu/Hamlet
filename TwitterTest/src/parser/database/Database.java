package parser.database;

import parser.xmlParsing.Tweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles all low-level-activities on the database
 * @author rom54494
 *
 */
public class Database {
	
	//general stuff
	private final String DATABASE_NAME = "hamlet";
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USER_NAME = "root";
	private final String PASSWORD = "root";
	private final String DEFAULT = "DEFAULT";
	
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
	private PreparedStatement insertTweet;
	private PreparedStatement getPerson;

	/**
	 * Tries to establish a connection to the database, program exits if this fails <br>
	 * because all other parts of the program rely on the database working
	 */
	public Database(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL+DATABASE_NAME, USER_NAME, PASSWORD);
			statement = connection.createStatement();
			prepareStatements();
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1 );
		}
	}
	
	/**
	 * Declaration of all PreparedStatements needed for working on the database
	 */
	private void prepareStatements(){
		try {
			insertPerson = connection.prepareStatement("INSERT INTO " + TABLE_PERSON 
					+ " (" + PERSON_NAME + ", " + PERSON_KEY1 + ", " + PERSON_KEY2+ ", " + PERSON_KEY3+ ", " + PERSON_KEY4+ ") "
					+ "VALUES "
					+ "( "
					+ "? , ? , ? , ?, ?);");
			
			insertTweet = connection.prepareStatement("INSERT INTO " + TABLE_TWEET 
					+ " (" + TWEET_PID + ", " + TWEET_TEXT + ", " + TWEET_TYPE +") "
					+ "VALUES "
					+ "( "
					+ "? , ? , ?);");
			
			getPerson = connection.prepareStatement("SELECT " + PERSON_NAME
					+ " FROM " + TABLE_PERSON
					+ " WHERE" + PERSON_ID + " = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns the ID corresponding to the name of an entry in the table TABLE_PERSON
	 * @param name The name of the person you want to search for in the database
	 * @return The ID of the person corresponding to the name, {@code -1} if there were no matches of an error occurred
	 */
	private int getIDfromName(String name){
		try {
			getPerson.setString(1, name);
			return getPerson.executeQuery().getInt(1);
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * Removes all values from the database for rebuilding
	 */
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
		
	/**
	 * Writes an entry into the table TABLE_PERSON
	 * @param name Name of the person to be added
	 * @param keys Twitter-API-Keys, must be 4
	 * @return true if entry is inserted successfully <br> 
	 * 		   false if wrong number of keys or SQL-Exception occurs
	 */
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
	
	/**
	 * Inserts a {@link parser.xmlParsing.Tweet} into the table TABLE_TWEET
	 * @param tweet - Tweet to be added to the table
	 * @return true if entry is inserted successvully <br>
	 * 		   false if an error has occurred
	 */
	public boolean insertTweet(Tweet tweet){
		//int pid = getIDfromName(tweet.getSpeaker());
		int pid = 1;
		if(pid != -1 && tweet.getText() != null){
			try {
				insertTweet.setInt(1, pid);
				insertTweet.setString(2, tweet.getText());
				insertTweet.setString(3, tweet.getType());
				insertTweet.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
