package parser.main;

import parser.database.DBCInterface;
import parser.database.DatabaseController;

/**
 * Currently only used to initiate the DBCInterface, should technically be removed 
 * if nothing else is added to it; Tweeter could be started here after database was built
 * @author rom54494
 *
 */
public class MainController {
	/** The Controller for the database */
	private DBCInterface db;
	
	/** Instanciates and inits all controllers if necessary */
	private void initControllers(){
		db = new DatabaseController();	
	}
		
	/** Starts the execution of all controller */
	private void runControllers(){
		db.run();	
	}
		
	/** Starts initiation and execution of moduels */
	public void run(){
		initControllers();
		runControllers();
	}
}
