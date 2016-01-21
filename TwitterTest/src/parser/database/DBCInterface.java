package parser.database;

import parser.xmlParsing.Tweet;

/**
 * This interface is responsible to write entries into the corresponding database
 * @author rom54494
 *
 */
public interface DBCInterface {
	/**
	 * Inserts a name in a database
	 * @param name Name of the person to be added
	 * @param keys Twitter-API-Keys of the corresponding account
	 */
	void insertPerson(String name, String[] keys);
	/**
	 * Inserts an instance of {@link parser.xmalParsing.Tweet} into the database
	 * @param tweet The tweet to be added
	 */
	void insertLine(Tweet tweet);
}
