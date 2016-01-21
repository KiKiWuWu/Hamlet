package parser.xmlParsing;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

/**
 * This class is designed to hold a list of {@link Element}, iterate over it and extract the data relevant for the database.
 * For getting the correct sequence of lines across different instances of this class over the same data, 
 * the method {@code getCurrentLineNumber} should be used.
 * @author rom54494
 *
 */
public class ElementList {

	private List<Element> list;
	private Iterator<Element> iter;
	private Element currentElement;
	private int currentLine;
	private String currentSpeaker;
	
	public ElementList(List<Element> list){
		this.list = list;
		currentLine = Integer.MAX_VALUE;
		iter = this.list.iterator();
	}
	
	/**
	 * Takes a look at {@code currentElement} and reads the number of the current line from it
	 * @return the new line number
	 */
	private int updateLineNumber(){
		return 0;
	}
	
	private void updateSpeaker(){
		
	}
	
	/**
	 * Looks at {@code currentElement} and parses the drama-text from it
	 * @return A String with the text of the drama, corresponding to the current element
	 */
	public String getCurrentLineText(){
		return null;
	}
	
	/**
	 * @return the number of the line in the current element. Returns Integer.MAX_VALUE if no line is specified (head-tag!!)
	 */
	public int getCurrentLineNumber(){
		return currentLine;
	}
	
	public String getCurrentSpeaker(){
		return currentSpeaker;
	}
	
	/**
	 * Tells the class to update the current element
	 * @return true, if the element has been updated successfully
	 * <br> false, if there were no more elements in the list and no update was possible
	 */
	public boolean setNext(){
		if(iter.hasNext()){
			currentElement = iter.next();
			currentLine = updateLineNumber();
			updateSpeaker();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks the list (via the iterator) if any more {@link Elements} are left
	 * @return true if the are more Elements to work with
	 * <br> false if at the end of the list
	 */
	public boolean hasNext(){
		if(iter.hasNext()){
			return true;
		}
		else{
			return false;
		}
	}
}
