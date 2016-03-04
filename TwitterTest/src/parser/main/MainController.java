package parser.main;

import parser.database.DBCInterface;
import parser.database.DatabaseController;

public class MainController {
	
	private DBCInterface db;
	
	private void initControllers(){
		db = new DatabaseController();	
	}
		
	private void runControllers(){
		db.run();	
	}
		
	public void run(){
		initControllers();
		runControllers();
	}
}
