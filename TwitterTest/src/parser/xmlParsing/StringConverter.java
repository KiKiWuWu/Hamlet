package parser.xmlParsing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class StringConverter {
	
	/** Contains the characters to be searched as keys and their replacement as values */
	private Map<CharSequence, CharSequence> converter;
	/** Set containing all keys for conveniences sake */
	private HashSet<String> keys;
	
	/**
	 * Creates an instance, mapping the entries in {@code keys} to the entries in {@code values}.</br>
	 * The corresponding values have to be in the same order, otherwise the mapping will be wrong. </br>
	 * Will throw an exception if keys and values don't contain the exact same number of elements.
	 * @param keys The chars which shall be converted
	 * @param values The replacement-chars
	 */
	public StringConverter(Iterable<CharSequence> keys, Iterable<CharSequence> values){
		converter = new HashMap<CharSequence, CharSequence>();
		Iterator<CharSequence> kIt = keys.iterator();
		Iterator<CharSequence> vIt = values.iterator();
		CharSequence key, value;

		while(kIt.hasNext()){
			key = kIt.next();
			value = vIt.next();
			converter.put(key, value);
		}
		keys = converter.keySet();
	}
	
	/**
	 * Creates an instance, mapping the keys to a variation of the corresponding key.</br>
	 * Example: (example, X, 0) -> Xexample
	 * @param keys The chars which shall be converted
	 * @param toAdd The character which will be added to every key
	 * @param position At which position the char will be added, an exception will be thrown if position is bigger than
	 * length of shortest string in keys
	 */
	public StringConverter(Iterable<CharSequence> keys, char toAdd, int position){
		converter = new HashMap<CharSequence, CharSequence>();
		Iterator<CharSequence> kIt = keys.iterator();
		CharSequence key, pre, after;
		String value;
		
		while(kIt.hasNext()){
			key = kIt.next();
			pre = key.subSequence(0, position);
			after = key.subSequence(position, key.length());
			value = "" + pre + toAdd + after;
			converter.put(key, value);
			
		}
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
		String token;
		for(int i = 0; i < lines.length; i++){
			token = lines[i].trim().toLowerCase();
			for(String key : keys){
				if(token.equals(key.trim().toLowerCase())){
					lines[i] = converter.get(key).toString();
				}
			}
		}
		
		return Arrays.toString(lines);
	}
}
