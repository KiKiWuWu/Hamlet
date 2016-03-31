package parser.database;

import java.util.Map;

/**
 * This interface specifies the necessary actions, a storage of any kind needs the be able to handle to 
 * be used in the parser
 * @author Matt_2
 *
 */
public interface FolgersDatabase {

	/** Removes all entries from the whole databse */
	public void clearDatabase();
	
	/**
	 * @param person the {@link Person} to be inserted into the database
	 * @return true if insertion was successful <br>
	 * false if not
	 */
	public boolean insertIntoPerson(Person person);
	
	/**
	 * @param tweet the {@link Tweet} to be inserted into the database
	 * @return true if insertion was successful <br>
	 * false if not
	 */
	public boolean insertTweet(Tweet tweet);
	
	/**
	 * Gets all people from the database that are marked as replacable with their twitter-ids
	 * @return A map containg the original names as keys and the the twitter-ids as the corresponding values
	 */
	public Map<CharSequence, CharSequence> getReplacableNames();
}
