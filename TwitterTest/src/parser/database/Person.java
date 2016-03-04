package parser.database;

/**
 * A class containing the necessary information to insert a person into the database. <br>
 * Data should be read from res/accounts.csv
 * @author rom54494
 *
 */
public class Person {

	/** Name of the person */
	private String name;
	/** ID of the person in the folger-xml */
	private String folger_id;
	/** Twitterkeys for tweeting automatically */
	private String[] keys;
	/** If the name of the person is unique enough to be replaced by the twitter-id in tweets */
	private int replacable;
	/** Twitter-id of the account of this person */
	private String twitter_id;
	/** potential inforamtion at the end of CSV, will probably get removed */
	private String sonstiges;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the folger_id
	 */
	public String getFolger_id() {
		return folger_id;
	}
	/**
	 * @param folger_id the folger_id to set
	 */
	public void setFolger_id(String folger_id) {
		this.folger_id = folger_id;
	}
	/**
	 * @return the keys
	 */
	public String[] getKeys() {
		return keys;
	}
	/**
	 * @param keys the keys to set
	 */
	public void setKeys(String[] keys) {
		if(keys.length == 4){
			this.keys = keys;
		}
	}
	/**
	 * @return the replacable
	 */
	public int getReplacable() {
		return replacable;
	}
	/**
	 * @param replacable the replacable to set
	 */
	public void setReplacable(int replacable) {
		this.replacable = replacable;
	}
	/**
	 * @return the twitter_id
	 */
	public String getTwitter_id() {
		return twitter_id;
	}
	/**
	 * @param twitter_id the twitter_id to set
	 */
	public void setTwitter_id(String twitter_id) {
		this.twitter_id = twitter_id;
	}
	/**
	 * @return the sonstiges
	 */
	public String getSonstiges() {
		return sonstiges;
	}
	/**
	 * @param sonstiges the sonstiges to set
	 */
	public void setSonstiges(String sonstiges) {
		this.sonstiges = sonstiges;
	}
}
