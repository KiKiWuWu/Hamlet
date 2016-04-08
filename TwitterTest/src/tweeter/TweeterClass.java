/** TwitterClient Application

 * Uses Twitter4j library (java)
 * Uses Twitter API 1.1
 * 
 * Can post, retweet, like a tweet and respond to a tweet when messageID is given
 * inspired from Elisha - Simple Developer
 */

package tweeter;

import java.util.Arrays;

import twitter4j.*;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

class TweeterClass {	
	
	private String key_1 = null;
	private String key_2 = null;
	private String key_3 = null;
	private String key_4 = null;
	private String text = null;
	private String type = null;	
	private Integer row_id = null;
	private Long ref_tweet_id = null;

	
	public TweeterClass(String key_1, String key_2, String key_3, String key_4, String text, String type, Integer row_id, Long ref_tweet_id) {
		this.key_1 = key_1;
		this.key_2 = key_2;
		this.key_3 = key_3;
		this.key_4 = key_4;
		this.text = text;
		this.type = type;
		this.row_id = row_id;
		this.ref_tweet_id = ref_tweet_id;
			
	}
	
	/**
	 * checks if we want to tweet or make a response-tweet
	 * @return
	 */
	public Long tweet() {
		Long id = null;

		if(type.equals("tweet")){

			try {

				 id = posting(key_1, key_2, key_3, key_4, text);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
		}else if(type.equals("response")){

			try {
				 id = replyingToTweet(ref_tweet_id, key_1, key_2, key_3, key_4, text);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * this methode is retweeting a tweet (with a specific messageID)
	 * Please note that a twitter user can't retweet his own tweet!
	 * @param messageId
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @throws TwitterException
	 */
	private static void retweeting(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) throws TwitterException {
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter4 = factory.getInstance();
		twitter4.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken accessToken4 = new AccessToken(accessToken, accessTokenSecret);
		twitter4.setOAuthAccessToken(accessToken4);
		twitter4.retweetStatus(messageId);

	}

	/**
	 * given a messageID this methode likes a tweet
	 * @param messageId
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @throws TwitterException
	 */
	private static void likingATweet(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) throws TwitterException {

		TwitterFactory factory3 = new TwitterFactory();
		Twitter twitter3 = factory3.getInstance();
		twitter3.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken accessToken3 = new AccessToken(accessToken, accessTokenSecret);

		twitter3.setOAuthAccessToken(accessToken3);
		Status status3 = twitter3.createFavorite(messageId);

		logTwitterServerRespond(status3);
	}

	/**
	 * this methode replies to a tweet (messgeID) 
	 * @param messageId
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param text
	 * @return
	 * @throws TwitterException
	 */
	private static long replyingToTweet(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret, String text) throws TwitterException {
		String replyMessage = text;
		
		TwitterFactory factory2 = new TwitterFactory();
		Twitter twitter2 = factory2.getInstance();
		twitter2.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken accessToken2 = new AccessToken(accessToken, accessTokenSecret);
		twitter2.setOAuthAccessToken(accessToken2);
		StatusUpdate statusUpdate2 = new StatusUpdate(replyMessage);
		
		statusUpdate2.setInReplyToStatusId(messageId);
		
		Status status2 = twitter2.updateStatus(statusUpdate2);
		
		logTwitterServerRespond(status2);
		
		Long twitterID = status2.getId();
		return twitterID;

	};

	/**
	 * this mehtode is responsible for tweeting 
	 * it returnes the given messageID
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param text
	 * @return
	 * @throws TwitterException
	 */
	private static long posting(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, String text)
			throws TwitterException {
		
		/** Instantiate a re-usable and thread-safe factory */
		TwitterFactory twitterFactory = new TwitterFactory();
		/** Instantiate a new Twitter instance */
		Twitter twitter = twitterFactory.getInstance();
		/** setup OAuth Consumer Credentials */
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		/** setup OAuth Access Token */
		twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

		/** Instantiate and initialize a new twitter status update */
		StatusUpdate statusUpdate = new StatusUpdate(text);

		Status status = twitter.updateStatus(statusUpdate);

		logTwitterServerRespond(status);
		
		Long twitterID = status.getId();
		return twitterID;
		
		
		
	};
	
	/**
	 * Log output response from Twitter Server
	 * @param status
	 */

	public static void logTwitterServerRespond(Status status) {
	
		System.out.println("status.toString() = " + status.toString());

		System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
		System.out.println("status.getSource() = " + status.getSource());
		System.out.println("status.getText() = " + status.getText());

		System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
		System.out.println("status.getUserMentionEntities() = " + Arrays.toString(status.getUserMentionEntities()));
		
		
	};

}