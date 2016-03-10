package parser.database;

import parser.xmlParsing.XMLParser;

import java.util.Map;

public class DatabaseController implements DBCInterface{
	
	/** Path to the CSV-File containing the necessary information about the characters */
	private final String PATH_PEOPLE = "res/accounts.csv";
	/** Path to the XML containing the play itself */
	private final String PATH_HAMLET = "res/folger/Ham.xml";
	
	/** Database in which tweets and people are stored */
	private FolgersDatabase db;
	/** Module which parses the CSV containg the people of the play */
	private PersonParser pp;
	/** Module parsing the play itself */
	private XMLParser xmlParser;

	/**
	 * Initializes all modules or sub-controllers for the whole program here
	 */
	public DatabaseController(){
		db = new FolgersSQLDatabase();
		pp = new PersonParser(this);
		xmlParser = new XMLParser(this, PATH_HAMLET);
	}
	
	@Override
	public void insertPerson(Person person){
		if(db.insertIntoPerson(person) == false){
			System.err.println("NOT ABLE TO INSERT " + person.getName());
		}
	}
	
	@Override
	public void insertLine(Tweet tweet){
		if(db.insertTweet(tweet) == false){
			System.err.println("NOT ABLE TO INSERT LINE " + tweet);
		}
	}
	
	@Override
	public Map<CharSequence, CharSequence> getReplacableCharacters() {
		return db.getReplacableNames();
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
