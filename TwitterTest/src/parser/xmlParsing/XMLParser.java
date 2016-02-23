package parser.xmlParsing;

import java.io.IOException;
import java.util.ArrayList;

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
	
	/**
	 * Fills {@code scenes} for the current act
	 * @param act The current act from which the scenes shall be extraced
	 */
	private void initScenes(Element act){
		scenes = new ArrayList<>(act.getChildren("div2", ns));
	}
	
	/**
	 * Gives the controller a {@link Tweet} with the initial informations about the scene to follow
	 * @param act the current act
	 * @param scene the current scene
	 */
	private Tweet constructInitialSceneTweet(int act, int scene){
		return new Tweet(null, "Act " + act + " Scene " + scene, "tweet", -1);
	}
	
	/**
	 * Method responsible for parsing the XML. <br>
	 * The file will be parsed and the contents will be given back by calling
	 * {@code insertLine} of the {@link DBCInterface}.
	 */
	public void run(){
		ArrayList<Tweet> tl = new ArrayList<Tweet>();

		for(int numAct = 3; numAct < 4; numAct++){
			initScenes(acts.get(numAct));
			for(int numScene = 4; numScene < 5; numScene++){
				tl.add(constructInitialSceneTweet(numAct+1, numScene+1));;
				ElementList elist = new ElementList(scenes.get(numScene)); 
				tl.addAll(elist.getTweets());
				for(Tweet t : tl){
					controller.insertLine(t);
				}
			}				
		}
	}
}
