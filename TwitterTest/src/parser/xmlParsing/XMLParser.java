package parser.xmlParsing;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import parser.database.DBCInterface;
import parser.xmlParsing.ElementList;

public class XMLParser {
	
	/** This array contains the tags of the XML which contain relevant text. */
	private final String[] TEXT_TAGS = {"stage", "sp"};
	
	private DBCInterface controller;
	private Document doc;
	private Element body;
	private ElementList[] lists;
	private ArrayList<Element> acts, scenes;
	
	public XMLParser(DBCInterface controller, final String PATH){
		this.controller = controller;
		try {
			doc = new SAXBuilder().build(PATH);
			body = doc.getRootElement().getChild("body");
		} catch (IOException | JDOMException e) {
			e.printStackTrace();
		}
		acts = new ArrayList<>(body.getChildren("div1"));
	}
	
	/**
	 * Creates the array {@code lists} with the same size as {@code TEXT_TAGS} and creates for each
	 * tag in this array an {@link ElementList} with the data in {@code body}.
	 */
	private void initLists(Element scene){
		lists = new ElementList[TEXT_TAGS.length];
		for(int i = 0; i < lists.length; i++){
			lists[i] = new ElementList(scene.getChildren(TEXT_TAGS[i]));
		}
	}
	
	private void initScenes(Element act){
		scenes = new ArrayList<>(act.getChildren("div2"));
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
	 */
	private int getNextLine(){
		int min = Integer.MAX_VALUE;
		for(ElementList el : lists){
			if(el.getCurrentLineNumber() < min){
				min = el.getCurrentLineNumber();
			}
		}
		return min;
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
