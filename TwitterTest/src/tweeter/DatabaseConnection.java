package tweeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

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
		private PreparedStatement insertTweet;
		private PreparedStatement getRefTweetId;
		private PreparedStatement insertLink;
		private PreparedStatement updateTweetID;
		private PreparedStatement updateReferenceTweetID;

		public DatabaseConnection(){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(URL+DATABASE_NAME, USER_NAME, PASSWORD);
				statement = connection.createStatement();
				prepareStatements();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void prepareStatements(){
			try {
				updateTweetID = connection.prepareStatement("UPDATE " + TABLE_TWEET 
						+ " SET " + TWEET_TWEETID + " = "
						+ "(?)"
						+ " WHERE "
						+ TWEET_ID
						+ " = "
						+ "(?);");
				
				updateReferenceTweetID = connection.prepareStatement("UPDATE " + TABLE_TWEET 
						+ " SET " + TWEET_REF_TWEET_ID + " = "
						+ "(?)"
						+ " WHERE "
						+ TWEET_ID
						+ " = "
						+ "(?);");
				
				getRefTweetId = connection.prepareStatement("SELECT " + TWEET_TWEETID
						+ " FROM " + TABLE_TWEET
						+ " WHERE " + TWEET_ID + " = "
						+ "(?);");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}	
		
		public Long getRefId(Integer row_id) {

			Long refID = null;	
			Statement stmt = null;
			String query =		"SELECT " + TWEET_TWEETID
					+ " FROM " + TABLE_TWEET
					+ " WHERE " + TWEET_ID + " = "
					+ row_id;
	    
		    try {
		        stmt = connection.createStatement();
		        
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	refID = rs.getLong(TWEET_TWEETID);
		        }
		        
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return refID;	
		}

			
		private void getKeys(String person_id, String text, String type, Integer row_id){
			System.out.println("_______________________");

			String key_1 = null, key_2 = null, key_3 = null, key_4 = null;
			Long ref_id = null;			
			
			String queryKeys = "SELECT * FROM "+ TABLE_PERSON + " WHERE " + PERSON_ID + " = " + person_id;
		    PreparedStatement statementKeys;
		        
				try {
					statementKeys = connection.prepareStatement(queryKeys);
					ResultSet resKeys = statementKeys.executeQuery();

					while(resKeys.next()) {
						
						 key_1 = resKeys.getString(PERSON_KEY1);
					     key_2 = resKeys.getString(PERSON_KEY2);
					     key_3 = resKeys.getString(PERSON_KEY3);
					     key_4 = resKeys.getString(PERSON_KEY4);
					     
					     if(type.equals("response")){
					      	ref_id = getRefId(row_id - 1); 	
					     }else if (type.equals("tweet")){
					    	 ref_id = null; 
					     }	     		     
					    
					    Tweeter tweeter = new Tweeter(key_1, key_2, key_3, key_4, text, type, row_id, ref_id);
					    Long tweet_id = tweeter.tweet(); //diese Methode gibt die TwitterID des Tweets als Long zurück
				    
					    saveIDofTweet(tweet_id, row_id);
					    
					    if(type.equals("response")){
					    		saveIDofRefId(ref_id, row_id);					    	 
					     }else if (type.equals("tweet")){
					     }	
					    
					}
					
					//System.out.println(resKeys.getInt(1));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					 
		}		
	
	

		public void getAllTweets(){
			
			String query = "SELECT " + TWEET_PID + ", "+ TWEET_ID + ", " + TWEET_TYPE + ", " + TWEET_TEXT + " FROM "+ TABLE_TWEET;
			try {
				PreparedStatement statementID = connection.prepareStatement(query);
				ResultSet res = statementID.executeQuery();
				
				 while (res.next())
			      {
			        String person_id = res.getString(TWEET_PID);
			        String text = res.getString(TWEET_TEXT);
			        String type = res.getString(TWEET_TYPE);
			        Integer row_id = res.getInt(TWEET_ID);

			        getKeys(person_id, text, type, row_id); 			               
			      }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void saveIDofTweet(Long twitterID, Integer row_id) {
			try {
				updateTweetID.setLong(1, twitterID);
				updateTweetID.setInt(2, row_id);
				updateTweetID.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void saveIDofRefId(Long refID, Integer row_id) {
			System.out.print("test");
			try {
				updateReferenceTweetID.setLong(1, refID);
				updateReferenceTweetID.setInt(2, row_id);				
				updateReferenceTweetID.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
}