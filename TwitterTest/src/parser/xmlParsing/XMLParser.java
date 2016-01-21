package parser.xmlParsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import parser.database.DBCInterface;
import parser.xmlParsing.ElementList;

public class XMLParser {
	
	/** This array contains the tags of the XML which contain relevant text. */
	private final String[] TEXT_TAGS = {"stage", "sp"};
	
	private Namespace ns;
	private DBCInterface controller;
	private Document doc;
	private Element root;
	private Element body;
	private ElementList[] lists;
	private ArrayList<Element> acts, scenes;
	
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
	 * Creates the array {@code lists} with the same size as {@code TEXT_TAGS} and creates for each
	 * tag in this array an {@link ElementList} with the data in {@code body}.
	 */
	private void initLists(Element scene){
		lists = new ElementList[TEXT_TAGS.length];
		for(int i = 0; i < lists.length; i++){
			List el = scene.getChildren();
			lists[i] = new ElementList(scene.getChildren(TEXT_TAGS[i], ns), ns);
		}
	}
	
	private void initScenes(Element act){
		scenes = new ArrayList<>(act.getChildren("div2", ns));
	}
	
	/**
	 * Checks if at least one {@link ElementList} in {@code lists} has elements left.
	 * @return true if there are elements left to parse
	 * <br> false if all elements have been parsed and added to the database
	 */
	private boolean elementsLeft(){
		for(ElementList el : lists){
			if(el.hasNext()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Looks at all {@link ElementList}s of {@code list} and determines which has the next line
	 * to be processed.
	 * @return The index of the ElementList in {@code lists} which holds the next line to be processed.
	 */
	private int getNextLine(){
		int index = -1;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < lists.length; i++){
			int line = lists[i].getCurrentLineNumber();
			if(line < min){
				min = line;
				index = i;
			}
		}
		return index;
	}
	
	public void run(){
		for(int numAct = 0; numAct < 1; numAct++){
			initScenes(acts.get(numAct));
			for(int numScene = 0; numScene < 1; numScene++){
				initLists(scenes.get(numScene));
				while(elementsLeft()){
					int curr = getNextLine();
					Tweet t = new Tweet(lists[curr].getCurrentSpeaker(), lists[curr].getCurrentLineText(), null);
					controller.insertLine(t);
					lists[curr].setNext();
				}
			}				
		}
	}
}
