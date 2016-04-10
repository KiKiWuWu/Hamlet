package tweeter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class TwitterDatabaseController {
	
	private final String PATH = "res/start.txt";
	private DatabaseConnection dc;
	private int last_tweet;
	
	private void initDatabaseConnectionControllers(){
		dc = new DatabaseConnection(this);	
	}
	
	
	/**
	 * returnes the last tweeted ID of the database
	 * the last tweet ID is saved in an external file named start.txt in the res folder
	 * @return 
	 */
	
	private int getLastTweet(){
			try {
				BufferedReader br = new BufferedReader(new FileReader(PATH));
				int result = Integer.parseInt(br.readLine());
				br.close();
				return result;
			} catch (NumberFormatException | IOException e ) {
				return 1;
			}		
	}
	
	public void saveLastTweet(int tid){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(PATH));
			bw.write(Integer.toString(last_tweet));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * checks the last tweeted ID
	 * and invokes the methode getAllTweets
	 */
	private void runHamletTweets(){ 
		
		/**the methode prepareStatements is not necessary */
		dc.prepareStatements();	
		
		last_tweet = getLastTweet();
		/** System.out.println("LAST TWEET id: "+last_tweet);*/
		
		last_tweet = dc.getAllTweets(last_tweet);
		System.out.println(last_tweet);
		saveLastTweet(last_tweet);
	
		System.out.println("end");	
		 
	}
	/**
	 * initialication of Database controllers
	 * starting runHamletTweets methode
	 */
	public void run(){
		initDatabaseConnectionControllers();
		runHamletTweets(); 
	}
}
