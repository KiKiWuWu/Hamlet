package parser.xmlParsing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is used for changing the text of the tweet, insert hashtags, mentions et ceterum censeo Carthaginem esse delendam
 * @author rom54494
 *
 */
public class StringConverter {
	
	/** Contains the characters to be searched as keys and their replacement as values */
	private Map<CharSequence, CharSequence> converter;
	/** Set containing all keys for conveniences sake */
	private Set<String> keys;
	
	/**
	 * 
	 * @param converter Map containing values which shall be replaced as keys and the replacement as value
	 */
	public StringConverter(Map<CharSequence, CharSequence> converter){
		this.converter = converter;
		keys = new HashSet<String>();
		getKeys();
	}
	
	/**
	 * Fills {@code keys} with Strings of all entries in the map {@code converter} for easier use later
	 */
	private void getKeys(){
		Map<CharSequence, CharSequence> newMap = new HashMap<>();//needed because of ConcurrentModificationException
		CharSequence key;
		CharSequence value;
		for(CharSequence c : converter.keySet()){
			key = c;
			value = converter.get(c);
			newMap.put(key, value);
			keys.add(key.toString());
		}
		converter = newMap;
	}

	/**
	 * Looks at a {@link CharSequence}, splits it by the {@code delimiter} and than replaces all tokens
	 * which are keys in {code converter}
	 * @param line The line to be changed
	 * @param delimiter The chars by which the line will be split
	 * @return The changed line
	 */
	public CharSequence convertString(CharSequence line, CharSequence delimiter){
		String[] lines = line.toString().split(delimiter.toString());
		String token, result = "";

		for(int i = 0; i < lines.length; i++){
			token = lines[i].replaceAll("[^a-zA-Z ]", "");;
			for(String key : keys){
				if(token.contains(key)){
					if(converter.get(key) == null){
						System.err.println("Matched but no result in map for: " + key);
					}
					else{
						lines[i] = lines[i].replace(key, converter.get(key));
					}
					break;
				}
			}
			result+= lines[i] + delimiter;
		}		
		return result.trim();
	}
}
