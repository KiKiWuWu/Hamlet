package parser.xmlParsing;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * This class is designed to hold a list of {@link Element}, iterate over it and extract the data relevant for the database.
 * For getting the correct sequence of lines across different instances of this class over the same data, 
 * the method {@code getCurrentLineNumber} should be used.
 * @author rom54494
 *
 */
public class ElementList {
	
	private final String TAG_SPEECH = "ab";
	private final String TAG_SPEAKER = "speaker";
	private final String ATTR_ID = "id";

	private Namespace ns;
	private List<Element> list;
	private Iterator<Element> iter;
	private Element currentElement;
	private int currentLine;
	private String currentText;
	private String currentSpeaker;
	
	public ElementList(List<Element> list, Namespace ns){
		this.ns = ns;
		this.list = list;
		currentLine = Integer.MAX_VALUE;
		iter = this.list.iterator();
		setNext();
	}
	
	private void updateText(){
		List<Element> textList;
		String line = "";
		if(currentElement.getChild(TAG_SPEECH, ns) != null){//speaker-text
			textList = currentElement.getChild(TAG_SPEECH, ns).getChildren();
		}
		else{//stage-direction
			textList = currentElement.getChildren();
		}
		for(Element e : textList){
			line += e.getText();
		}
		currentText = line;
	}
	
	/**
	 * Takes a look at {@code currentElement} and reads the number of the current line from it
	 * @return the new line number
	 */
	private void updateLineNumber(){
		String line;
		if(currentElement.getChild(TAG_SPEECH, ns) != null){//speaker-text
			line = currentElement.getChild(TAG_SPEECH, ns).getChild("w", ns).getAttribute(ATTR_ID, Namespace.XML_NAMESPACE).getValue();
		}
		else{//stage-direction
			line = currentElement.getChild("w", ns).getAttribute(ATTR_ID, Namespace.XML_NAMESPACE).getValue();
		}
		line = line.replace("w", "");
		currentLine = Integer.parseInt(line);
	}
	
	/**
	 * After going to the next line, the speaker has to be updated if possible (not for stage-directions etc.)
	 */
	private void updateSpeaker(){
		if(currentElement.getChild("speaker", ns) != null){
			currentSpeaker = currentElement.getChild("speaker", ns).getChild("w", ns).getTextTrim();
		}
		else{
			currentSpeaker = null;
		}
	}
	
	/**
	 * Looks at {@code currentElement} and parses the drama-text from it
	 * @return A String with the text of the drama, corresponding to the current element
	 */
	public String getCurrentLineText(){
		return currentText;
	}
	
	/**
	 * @return the number of the line in the current element. Returns Integer.MAX_VALUE if no line is specified (head-tag!!)
	 */
	public int getCurrentLineNumber(){
		return currentLine;
	}
	
	/**
	 * @return The person who is saying the current line, {@code null} if no speaker
	 */
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
			updateLineNumber();
			updateText();			
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
