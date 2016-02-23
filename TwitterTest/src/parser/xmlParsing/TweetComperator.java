package parser.xmlParsing;

import java.util.Comparator;

public class TweetComperator implements Comparator<Tweet>{

	@Override
	public int compare(Tweet o1, Tweet o2) {
		return Integer.compare(o1.getLineNumber(), o2.getLineNumber());
	}

}
