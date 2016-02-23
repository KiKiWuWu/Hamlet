package parser.xmlParsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import parser.database.DBCInterface;
import parser.xmlParsing.ElementList;

/**
 * This class is resonsible for parsing the XML-file of the play.
 * @author rom54494
 *
 */
public class XMLParser {
	
	/** This array contains the tags of the XML which contain relevant text. */
	private final String[] TEXT_TAGS = {"stage", "sp"};
	/** Namespace for the xml, is read from it directly */
	private Namespace ns;
	/** Responsible for handling the insertion of tweets into the database */
	private DBCInterface controller;
	/** The XML itself */
	private Document doc;
	/** Root-element of the XML, only used to get {@code body} but kept for possible future use */
	private Element root;
	/** Main contents of the XML the whole play is stored within */
	private Element body;
	/** These hold the XML for the acts or scenes respectively */
	private ArrayList<Element> acts, scenes;
	/** These hold the specific elements to be processed for each scene */
	private ArrayList<ElementList> lists;
	
	/**
	 * @param controller Receives the lines when the are parsed
	 * @param PATH Path to the XML-file of the play
	 */
	public XMLParser(DBCInterface controller, final String PATH){
		this.controller = controller;
		try {
			doc = new SAXBuilder().build(PATH);
			root = doc.getRootElement();
			ns = root.getNamespace();
			body = root.getChild("text", ns).getChild("body", ns);
		} catch (IOException | JDOMException e) {
			e.printStackTrace();
		}
		acts = new ArrayList<Element>(body.getChildren("div1", ns));
	}
	
	private LinkedList<Element> buildResultList(Element head, String tag){
		LinkedList<Element> result = new LinkedList<Element>();
		if(head.getName().equals(tag)){
			result.add(head);
		}
		else{
			for(Element e : head.getChildren()){
				if(e.getName().equals(tag)){
					result.add(e);
				}
				else{
					result.addAll(buildResultList(e, tag));
				}
			}
		}
		return result;
	}
	
	/**
	 * Creates the array {@code lists} with the same size as {@code TEXT_TAGS} and creates for each
	 * tag in this array an {@link ElementList} with the data in {@code body}.
	 */
	private void initLists(Element scene){
		lists = new ArrayList<ElementList>();
		for(int i = 0; i < TEXT_TAGS.length; i++){
			LinkedList<Element> resultList = new LinkedList<Element>();
			for(Element e : scene.getChildren()){
				resultList.addAll(buildResultList(e, TEXT_TAGS[i]));
			}
			lists.add( new ElementList(resultList, ns) );
		}
	}
	
	/**
	 * Fills {@code scenes} for the current act
	 * @param act The current act from which the scenes shall be extraced
	 */
	private void initScenes(Element act){
		scenes = new ArrayList<>(act.getChildren("div2", ns));
	}
	
	/**
	 * Checks if at least one {@link ElementList} in {@code lists} has elements left.
	 * @return true if there are elements left to parse
	 * <br> false if all elements have been parsed and added to the database
	 */
	private boolean elementsLeft(){
		if(lists.size() == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Looks at all {@link ElementList}s of {@code list} and determines which has the next line
	 * to be processed.
	 * @return The index of the ElementList in {@code lists} which holds the next line to be processed.
	 */
	private int getNextLine(){
		int index = -1;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < lists.size(); i++){
			int line = lists.get(i).getCurrentLineNumber();
			if(line < min){
				min = line;
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Gives the controller a {@link Tweet} with the initial informations about the scene to follow
	 * @param act the current act
	 * @param scene the current scene
	 */
	private void constructInitialSceneTweet(int act, int scene){
		Tweet t = new Tweet(null, "Act " + act + " Scene " + scene, "tweet");
		controller.insertLine(t);
	}
	
	/**
	 * Method responsible for parsing the XML. <br>
	 * The file will be parsed and the contents will be given back by calling
	 * {@code insertLine} of the {@link DBCInterface}.
	 */
	public void run(){
		Tweet t;
		int curr;
		String speaker;
		ArrayList<String> textList;
		/*ArrayList<Tweet> tl = new ArrayList<Tweet>();
		tl.sort(new TweetComperator());*/
		for(int numAct = 3; numAct < 4; numAct++){
			initScenes(acts.get(numAct));
			for(int numScene = 4; numScene < 5; numScene++){
				constructInitialSceneTweet(numAct+1, numScene+1);
				initLists(scenes.get(numScene));
				while(elementsLeft()){
					curr = getNextLine();
					speaker = lists.get(curr).getCurrentSpeaker();
					textList = new ArrayList<String>(lists.get(curr).getCurrentLineText());
					for(String s : textList){
						t = new Tweet(speaker, s, null);
						controller.insertLine(t);
					}
					//removes list so it doesn't have to be checked every time if elements are left
					if(lists.get(curr).setNext() == false){
						lists.remove(curr);
					}
				}
			}				
		}
	}
}
