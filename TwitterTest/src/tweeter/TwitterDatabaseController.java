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
		
	private void runHamletTweets(){ 
		
		dc.prepareStatements();	//brauch ich anscheinend nicht....	
		last_tweet = getLastTweet();
		System.out.println("LAST TWEET id: "+last_tweet);
		
		last_tweet = dc.getAllTweets(last_tweet);
		System.out.println(last_tweet);
		saveLastTweet(last_tweet);
	
		System.out.println("end");	
		 
	}
	
	public void run(){
		initDatabaseConnectionControllers();
		runHamletTweets(); 
	}
}
