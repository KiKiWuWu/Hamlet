package parser.xmlParsing;

/**
 * Class containing all necessary informations for the tweet to be posted on Twitter
 * @author rom54494
 */
public class Tweet {
	
	/** Who said the line */
	private String speaker;
	/**	What did the person say */
	private String text;
	/** Will it be handled as a Tweet, Response or something else */
	private String type;
	/** Number of the line/character */
	private int lineNumber;
	
	/**
	 * 
	 * @param speaker The person who said the line
	 * @param text The line itself
	 * @param type Type how it will be handled for Twitter (Tweet, Response etc.)
	 */
	public Tweet(String speaker, String text, String type, int lineNumber){
		if(speaker == null){ 
			this.speaker = "Regie";
		}
		else{
			this.speaker = speaker;
		}
		
		if(type == null){
			this.type = "response";
		}
		else if(type.equals("tweet")){
			this.type = type;
		}
		else{
			this.type = "response";
		}
		
		this.text = text;
		
		this.lineNumber = lineNumber;
	}
	
	/**
	 * @return The speaker or NULL if no speaker has be specified yet
	 */
	public String getSpeaker(){
		return speaker;
	}
	
	/**
	 * @return The line spoken by the speaker
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * @return The type of the line previously specified or {@code null} when no type has been set yet. Calling this method
	 * will only back a String of the correct type (or null)
	 */
	public String getType(){
		return type;
	}
	
	public int getLineNumber(){
		return lineNumber;
	}
	
	/**
	 * @return A String consisting of "speaker : text"
	 */
	@Override
	public String toString(){
		//return speaker + ": " + text + " , " + lineNumber;
		return speaker + ": " + text;
	}
	/**
	 * Adds a string which determines if the line is to be handled as a response, a retweet or a normal tweet
	 * @param type String, may only be of ... 
	 * @return true if the type has been changed successfully <br> false if the type couldn't be changed	 * 
	 */
	/*public void setType(String type){
		if(type.equals("tweet") == false){
			this.type = "response";
		}
		else{
			this.type = type;
		}
	}*/
	
}
