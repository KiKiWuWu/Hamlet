package parser.xmlParsing;

/**
 * Class containing all necessary informations for the tweet to be posted on Twitter
 * @author rom54494
 */
public class Tweet {
	
	/** Who said the line */
	private String speaker = null;
	/**	What did the person say */
	private String text = null;
	/** Will it be handled as a Tweet, Response or something else */
	private String type = null;
	
	/**
	 * 
	 * @param speaker The person who said the line
	 * @param text The line itself
	 * @param type Type how it will be handled for Twitter (Tweet, Response etc.)
	 */
	public Tweet(String speaker, String text, String type){
		if(speaker == null){ 
			this.speaker = "Regie";
		}
		else{
			this.speaker = speaker;
		}
		this.text = text;
		this.type = type;
	}
	/**
	 * Adds the speaker of the current line of text
	 * @param speaker Name of the speaker
	 */
	public void setSpeaker(String speaker){
		this.speaker = speaker;
	}
	
	/**
	 * @return The speaker or NULL if no speaker has be specified yet
	 */
	public String getSpeaker(){
		return speaker;
	}
	
	/**
	 * Adds a String which represents the current line spoken
	 * @param text Current spoken line or NULL if no line has been added previously
	 */
	public void setText(String text){
		this.text = text;
	}
	
	/**
	 * @return The line spoken by the speaker
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * Adds a string which determines if the line is to be handled as a response, a retweet or a normal tweet
	 * @param type String, may only be of ... 
	 * @return true if the type has been changed successfully <br> false if the type couldn't be changed	 * 
	 */
	public boolean setType(String type){
		//TODO blub
		if(type.equals(type)){
			this.type = "response";
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @return The type of the line previously specified or {@code null} when no type has been set yet. Calling this method
	 * will only back a String of the correct type (or null)
	 */
	public String getType(){
		//return type;
		return "response";
	}
	
	/**
	 * @return A String consisting of "speaker : text"
	 */
	@Override
	public String toString(){
		return speaker + ": " + text;
	}
}
