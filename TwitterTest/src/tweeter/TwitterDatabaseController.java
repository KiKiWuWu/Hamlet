package tweeter;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class TwitterDatabaseController {
	
	private DatabaseConnection dc;
	
	private void initDatabaseConnectionControllers(){
		dc = new DatabaseConnection();	
	}
		
	private void runHamletTweets(){ 
		
		dc.prepareStatements();	//brauch ich anscheinend nicht....	
		dc.getAllTweets();
		
	
		System.out.println("end");	
		 
	}
	
	public void run(){
		initDatabaseConnectionControllers();
		runHamletTweets(); 
	}
	
	

}
