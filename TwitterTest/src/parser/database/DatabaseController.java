package parser.database;

import java.util.concurrent.SynchronousQueue;

public class DatabaseController implements DBCInterface{
	
	private final String PATH = "res/accounts.csv";
	
	private Database db;
	private PersonParser pp;

	public DatabaseController(){
		db = new Database();
		pp = new PersonParser(this);
	}
	
	public void insertPerson(String name, String[] keys){
		if(db.insertIntoPerson(name, keys) == false){
			System.err.println("NOT ABLE TO INSERT " + name);
		};
	}
	
	public void run(){
		System.out.println("Clearing database");
		db.clearDatabase();
		System.out.println("Inserting people");
		pp.parse(PATH);
		System.out.println("Done inserting");
	}
}
