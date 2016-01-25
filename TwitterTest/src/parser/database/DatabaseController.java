package parser.database;

import parser.xmlParsing.XMLParser;
import parser.xmlParsing.Tweet;

public class DatabaseController implements DBCInterface{
	
	private final String PATH_PEOPLE = "res/accounts.csv";
	private final String PATH_HAMLET = "res/folger/Ham.xml";
	
	private Database db;
	private PersonParser pp;
	private XMLParser xmlParser;

	/**
	 * Initializes all modules or sub-controllers for the whole program here
	 */
	public DatabaseController(){
		db = new Database();
		pp = new PersonParser(this);
		xmlParser = new XMLParser(this, PATH_HAMLET);
	}
	
	@Override
	public void insertPerson(String name, String[] keys){
		if(db.insertIntoPerson(name, keys) == false){
			System.err.println("NOT ABLE TO INSERT " + name);
		}
	}
	
	@Override
	public void insertLine(Tweet tweet){
		if(db.insertTweet(tweet) == false){
			System.err.println("NOT ABLE TO INSERT LINE " + tweet);
		}
	}
	
	/**
	 * Starts the program proper. These steps will be taken in this exact order <br>
	 * <ul>
	 * <li> clearing the database </li>
	 * <li> parsing the CSV containing the data of all people and writing them into the databse</li>
	 * <li> parsing the XML containing the text of the drama and adding it into the database with all necessary informations </li>
	 * </ul>
	 * After this process, the database should be ready to be used directly for posting the contents on Twitter
	 */
	public void run(){
		System.out.println("Clearing database");
		db.clearDatabase();
		System.out.println("Inserting people");
		pp.parse(PATH_PEOPLE);
		System.out.println("Done inserting");
		System.out.println("Starting to parse XML");
		xmlParser.run();
		System.out.println("Done parsing XML into database");
	}
}
