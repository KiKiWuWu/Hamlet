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
	
	/**
	 * 
	 * @param speaker The person who said the line
	 * @param text The line itself
	 * @param type Type how it will be handled for Twitter (Tweet, Response etc.), may be null for default
	 */
	public Tweet(String speaker, String text, String type){
		this.speaker = speaker;
		
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

	/**
	 * @return A String consisting of "speaker : text"
	 */
	@Override
	public String toString(){
		return speaker + ": " + text;
		/*if(speaker != null){
			return speaker.trim();
		}
		else{return text;}*/
	}
}
