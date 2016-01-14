package parser.main;

import parser.database.DatabaseController;

public class MainController {
	
	private DatabaseController db;
	
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
