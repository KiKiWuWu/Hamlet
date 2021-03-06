package tweeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import twitter4j.TwitterException;

public class DatabaseConnection {

	/**
	 * GENERAL STUFF password and user name according to your xampp account
	 * settings
	 */
	private final String DATABASE_NAME = "hamlet";
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USER_NAME = "root";
	private final String PASSWORD = "root";

	/** TABLES */
	private final String TABLE_TWEET = "tweets";
	private final String TABLE_PERSON = "person";

	/** COLUMNS */
	private final String TWEET_ID = "id";
	private final String TWEET_PID = "person_id";
	private final String TWEET_TEXT = "text";
	private final String TWEET_TYPE = "type";
	private final String TWEET_REF_TWEET = "reference_tweet";
	private final String TWEET_TWEETID = "tweet_id";
	private final String TWEET_REF_TWEET_ID = "ref_tweet_id";

	private final String PERSON_ID = "folger_id";

	private final String PERSON_NAME = "name";
	private final String PERSON_KEY1 = "consumer_key";
	private final String PERSON_KEY2 = "consumer_secret";
	private final String PERSON_KEY3 = "access_token";
	private final String PERSON_KEY4 = "access_token_secret";

	private final String PERSON_TWITTER_ID = "twitter_id";

	private TwitterDatabaseController controller;
	private Connection connection;
	private Statement statement;
	private ResultSet results;
	private PreparedStatement insertPerson;
	private PreparedStatement insertTweet;
	private PreparedStatement getRefTweetId;
	private PreparedStatement insertLink;
	private PreparedStatement updateTweetID;
	private PreparedStatement updateReferenceTweetID;

	String speaker = "";

	/**
	 * setting up the server connection of mysql database
	 */
	public DatabaseConnection(TwitterDatabaseController controller) {
		this.controller = controller;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL + DATABASE_NAME, USER_NAME, PASSWORD);
			statement = connection.createStatement();
			prepareStatements();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * some mysql select and update requests
	 */
	public void prepareStatements() {
		try {
			updateTweetID = connection.prepareStatement("UPDATE " + TABLE_TWEET + " SET " + TWEET_TWEETID + " = "
					+ "(?)" + " WHERE " + TWEET_ID + " = " + "(?);");

			updateReferenceTweetID = connection.prepareStatement("UPDATE " + TABLE_TWEET + " SET " + TWEET_REF_TWEET_ID
					+ " = " + "(?)" + " WHERE " + TWEET_ID + " = " + "(?);");

			getRefTweetId = connection.prepareStatement(
					"SELECT " + TWEET_TWEETID + " FROM " + TABLE_TWEET + " WHERE " + TWEET_ID + " = " + "(?);");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * returnes the TweetID of an already tweeted tweet of a specific row in the
	 * database (row_id) this is needed to get the reference tweetID for an
	 * response-tweet
	 * 
	 * @param row_id
	 * @return
	 */
	public Long getRefId(Integer row_id) {

		Long refID = null;
		Statement stmt = null;
		String query = "SELECT " + TWEET_TWEETID + " FROM " + TABLE_TWEET + " WHERE " + TWEET_ID + " = " + row_id;

		try {
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				refID = rs.getLong(TWEET_TWEETID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return refID;
	}

	/**
		 * first gets all necessary authentification keys for one account
		 * then initiates a new Tweet in TweeterClass with commited params
		 * @param person_id
		 * @param text
		 * @param type
		 * @param row_id
		 */
		private void getKeys(String person_id, String text, String type, Integer row_id){

			String key_1 = null, key_2 = null, key_3 = null, key_4 = null;
			Long ref_id = null;			
			

			String queryKeys = "SELECT * FROM "+ TABLE_PERSON + " WHERE " + PERSON_ID + " = '" + person_id + "'";
		    PreparedStatement statementKeys;
		    
		        
				try {
					statementKeys = connection.prepareStatement(queryKeys);
					ResultSet resKeys = statementKeys.executeQuery();

					while(resKeys.next()) {
						
						 key_1 = resKeys.getString(PERSON_KEY1);
					     key_2 = resKeys.getString(PERSON_KEY2);
					     key_3 = resKeys.getString(PERSON_KEY3);
					     key_4 = resKeys.getString(PERSON_KEY4);
					    // speaker = resKeys.getString(PERSON_TWITTER_ID);
					     
					     if(type.equals("response")){
					      	ref_id = getRefId(row_id - 1); 	
					     }else if (type.equals("tweet")){
					    	 ref_id = null; 
					    	 speaker = "";
					     }	     		     
					    
					    try {

					    	/** a 62 second time frame to circumvent the twitter POST request limits*/
							Thread.sleep(1000*62); 
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					    
					    TweeterClass tweeter = new TweeterClass(key_1, key_2, key_3, key_4, speaker + " " +text, type, row_id, ref_id);
					    speaker = resKeys.getString(PERSON_TWITTER_ID);

					    long tweet_id = -1;
						try { //diese Methode gibt die TwitterID des Tweets als Long zurueck
							tweet_id = tweeter.tweet();
							  saveIDofTweet(tweet_id, row_id);							    
							  if(type.equals("response")){
								  saveIDofRefId(ref_id, row_id);					    	 
							  }else if (type.equals("tweet")){
							    	 //nothing?
							  }
						} catch (TwitterException e) {//in case something went wrong tweeting, last id is saved and program exits
							controller.saveLastTweet(row_id);
							System.exit(0);
						} 					  
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}					 
		}

	/**
	 * first gets all information from every row in the database (speaker_id,
	 * text
	 * 
	 * @param last_tweet
	 * @return
	 */
	public int getAllTweets(int last_tweet) {

		String query = "SELECT " + TWEET_PID + ", " + TWEET_ID + ", " + TWEET_TYPE + ", " + TWEET_TEXT + " FROM "
				+ TABLE_TWEET;
		try {
			PreparedStatement statementID = connection.prepareStatement(query);
			ResultSet res = statementID.executeQuery();

			Integer row_id;

			res.next();

			int current = res.getInt(TWEET_ID);

			while (current < last_tweet) {
				res.next();
				current = res.getInt(TWEET_ID);
			}

			do {

				String person_id = res.getString(TWEET_PID);
				String text = res.getString(TWEET_TEXT); // @last_speaker
				String type = res.getString(TWEET_TYPE);
				row_id = res.getInt(TWEET_ID);

				getKeys(person_id, text, type, row_id);
			} while (res.next() && !notSzeneTweet(res.getString(TWEET_TEXT)));

			return row_id + 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * checks if a new scene/ act starts
	 * 
	 * @param text
	 * @return
	 */
	private boolean notSzeneTweet(String text) {

		return Pattern.matches("Act \\d Scene 1", text);
	}

	/**
	 * saves the new tweetIDs into the databse
	 * 
	 * @param twitterID
	 * @param row_id
	 */

	public void saveIDofTweet(Long twitterID, Integer row_id) {
		try {
			updateTweetID.setLong(1, twitterID);
			updateTweetID.setInt(2, row_id);
			updateTweetID.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * saves the referenceID for response-tweets in the database
	 * 
	 * @param refID
	 * @param row_id
	 */
	public void saveIDofRefId(Long refID, Integer row_id) {
		try {
			updateReferenceTweetID.setLong(1, refID);
			updateReferenceTweetID.setInt(2, row_id);
			updateReferenceTweetID.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
