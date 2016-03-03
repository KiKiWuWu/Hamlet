package parser.xmlParsing;

import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

/**
 * This class is designed to hold an {@link Element} representing the head of a tree and
 * extracts the data relevant for the database. 
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
	private final String DELIMITER = " ";

	private LinkedList<Tweet> currentTweets;
	private Element head;
	private StringConverter nameInserter;
	private StringConverter hashTagInserter;

	public ElementList(Element head) {
		this.head = head;
		this.nameInserter = new StringConverter(null, null);
		this.hashTagInserter = new StringConverter(null, null);
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
		else if(tag.equals(TAG_STAGE) ){
			return "Regie";
		}
		else{
			return "";
		}
	}

	/**
	 * Creates a {@link Tweet} and adds it to {@code currentTweets}
	 * @param speaker Who has said the line
	 * @param line What was said. If "", no tweet will be added
	 */
	private void addTweet(String speaker, String line) {
		if (line.equals("") == false) {
			if(speaker == null){
				speaker = "Regie";
			}
			String[] speakers = speaker.split(" ");
			for(String s : speakers){
				s = nameInserter.convertString(s, DELIMITER).toString();
				s = hashTagInserter.convertString(s, DELIMITER).toString();
				currentTweets.add(new Tweet(s, line, null));
			}

		}
	}

	/**
	 * Recursively goes through the XML and creates {@link Tweet}s when necessary
	 * @param head {@link Element} to start parsing from
	 * @param speaker the speaker in the parent Element, may be changed or kept for the Tweet
	 */
	private void parseTree(Element head, String speaker) {
		String line = "";
		//updates speaker only when needed
		String tmp = updateSpeaker(head);
		if(tmp != ""){
			speaker = tmp;
		}
		LinkedList<Element> children = new LinkedList<Element>(head.getChildren());
		if (children.size() == 0) {//no child-nodes
			return;
		}
		for (Element child : children) {
			//tag-name of current child
			String tag = child.getName();
			switch (tag) {
			case TAG_HEAD:
			case TAG_SPEAKER:
				break;//those tags needed to be excluded manually
			case TAG_CHAR:
			case TAG_WORD:
			case TAG_PUNC:
				line += child.getText();
				break;
			case TAG_LB://when a character speaks more than one line
				addTweet(speaker, line);
				line = "";
				break;
			default://adds Tweet with current line and performs recursive call with current child
				addTweet(speaker, line);
				line = "";
				parseTree(child, speaker);
				break;
			}
		}
		addTweet(speaker, line);//End of text, but no linebreak
	}

	/**
	 * Parses the tree starting with {@code head}
	 * @return All {@link Tweet}s that could be parsed
	 */
	public List<Tweet> getTweets() {
		parseTree(head, null);
		return currentTweets;
	}
}
