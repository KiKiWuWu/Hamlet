package parser.xmlParsing;

import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

/**
 * This class is designed to hold a list of {@link Element}, iterate over it and
 * extract the data relevant for the database. For getting the correct sequence
 * of lines across different instances of this class over the same data, the
 * method {@code getCurrentLineNumber} should be used.
 * 
 * @author rom54494
 *
 */
public class ElementList {

	private final String TAG_SPEECH = "sp";
	private final String TAG_STAGE = "stage";
	private final String TAG_HEAD = "head";
	private final String TAG_SPEAKER = "speaker";
	private final String TAG_WORD = "w";
	private final String TAG_CHAR = "c";
	private final String TAG_PUNC = "pc";
	private final String TAG_LB = "lb";
	private final String ATTR_WHO = "who";

	private LinkedList<Tweet> currentTweets;
	private Element head;
	private int ct = 0;

	public ElementList(Element head) {
		this.head = head;
		currentTweets = new LinkedList<Tweet>();
	}

	/**
	 * After going to the next line, the speaker has to be updated if possible
	 * (not for stage-directions etc.)<br>
	 * Will set {@code currentSpeaker} to {@code null} if no speaker can be
	 * found.
	 */
	private String updateSpeaker(Element currentElement) {
		String tag = currentElement.getName();
		if (tag.equals(TAG_SPEECH)) {
			return currentElement.getAttributeValue(ATTR_WHO);
		} 
		else if(tag.equals(TAG_STAGE)){
			return "Regie";
		}
		else{
			return "";
		}
	}

	private void addTweet(String speaker, String line) {
		if (line.equals("") == false) {
			currentTweets.add(new Tweet(speaker, line, null, ++ct));
		}
	}

	private void parseTree(Element head, String speaker) {
		String line = "";
		String tmp = updateSpeaker(head);
		if(tmp != ""){
			speaker = tmp;
		}
		LinkedList<Element> children = new LinkedList<Element>(head.getChildren());
		if (children.size() == 0) {
			return;
		}
		for (Element child : children) {
			String tag = child.getName();
			switch (tag) {
			case TAG_HEAD:
			case TAG_SPEAKER:
				break;
			case TAG_CHAR:
			case TAG_WORD:
			case TAG_PUNC:
				line += child.getText();
				break;
			case TAG_LB:
				addTweet(speaker, line);
				line = "";
				break;
			default:
				addTweet(speaker, line);
				line = "";
				parseTree(child, speaker);
				break;
			}
		}
		addTweet(speaker, line);
	}

	public List<Tweet> getTweets() {
		parseTree(head, null);
		return currentTweets;
	}
}
