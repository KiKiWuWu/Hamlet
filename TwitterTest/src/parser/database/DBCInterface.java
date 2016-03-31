package parser.database;

import java.util.Map;

/**
 * This interface is responsible to write entries, person-names and {@link Tweet}s into the corresponding database
 * @author rom54494
 *
 */
public interface DBCInterface {
	/**
	 * Inserts a name in a database
	 * @param person The {@link Person} to be added into the database
	 */
	public void insertPerson(Person person);
	
	/**
	 * Inserts an instance of {@link Tweet} into the database
	 * @param tweet The tweet to be added
	 */
	public void insertLine(Tweet tweet);
	
	/**
	 * @return all characters of the play whose names can be substituted by their Twitter-ids
	 */
	public Map<CharSequence, CharSequence> getReplacableCharacters();
	
	/**
	 * Starts the execution of the program. Building database, parsing play etc.
	 */
	public void run();
	
	
}
