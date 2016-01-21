/* TwitterClient Application

 * Uses Twitter4j library (java)
 * Uses Twitter API 1.1
 * 
 * Can post, retweet, like a tweet and respond to a tweet when messageID is given
 * inspired from Elisha - Simple Developer
 */
package tweeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

import twitter4j.*;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

class TweetUsingTwitter4JExample {

	public static void main(String[] args) throws Exception, IOException, TwitterException {
		// createTable(); // initialize MySql Database DOESN'T work yet....

		// setAccounts(); /problem nicht public

		// ULRIKE RAAB //my account
		// Your Twitter App's Consumer Key
		String consumerKey = "v1qD37aTs6IM4TydfnRUmSqV4";
		// Your Twitter App's Consumer Secret
		String consumerSecret = "vU4KdFYBIZTcmJIsHfry940si7vqr8ZemW8ZhKHp4MkMrzE4re" + "";
		// Your Twitter Access Token
		String accessToken = "4284825923-5CUYEFWwEgbUSuACSQWa5MjekjgeSRq1GOcJ0wf";
		// Your Twitter Access Token Secret
		String accessTokenSecret = "6v4ST6Y7AHr74kD9u32WPJQMtSeJ6xWT6I3eyP5dwuHWc";

		posting(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		// replyingToTweet(677937491094171651L,consumerKey,
		// consumerSecret,accessToken, accessTokenSecret );
		// retweeting(677937491094171651L, consumerKey2, consumerSecret2,
		// accessToken2, accessTokenSecret2);
		// likingATweet(677937491094171651L, consumerKey, consumerSecret,
		// accessToken, accessTokenSecret);
	}

	public static void createTable() throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement create = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT,name varchar(50), text varrchar(140), messageid long, type varchar(20), PRIMARY KEY(id))");
			create.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("e2");
		}
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/databasename";// when
																	// using
																	// localhost
			// String url =
			// "jdbc:mysql://24.196.52.166:3306/databasename";//when using IP
			// adress
			String username = "root";
			String password = "Passwort9";

			Class.forName(driver);

			Connection connection = DriverManager.getConnection(url, username, password);

			System.out.println("Connection to Database made");
			return connection;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("e");
		}
		return null;
	}

	private static void retweeting(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) throws TwitterException {
		// you cannot retweet your own tweet!
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter4 = factory.getInstance();
		twitter4.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken accessToken4 = new AccessToken(accessToken, accessTokenSecret);
		twitter4.setOAuthAccessToken(accessToken4);
		twitter4.retweetStatus(messageId);

	}

	private static void likingATweet(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) throws TwitterException {
		// TODO Auto-generated method stub

		TwitterFactory factory3 = new TwitterFactory();
		Twitter twitter3 = factory3.getInstance();
		twitter3.setOAuthConsumer(consumerKey, consumerSecret);
		// Parameter von oben
		AccessToken accessToken3 = new AccessToken(accessToken, accessTokenSecret);

		twitter3.setOAuthAccessToken(accessToken3);
		Status status3 = twitter3.createFavorite(messageId);

		logTwitterServerRespond(status3);
	}

	public static void replyingToTweet(long messageId, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) throws TwitterException {
		// ist statisch gebunden an die TweetID
		System.out.println("replyingToTweet");

		// for replying to the snow tweet with id=677937491094171651
		String replyMessage = "Ach ist das schön wenn ein Programm läuft! :)";

		// long messageId =677937491094171651L; // messageId of the tweet to
		// which the user is replying
		TwitterFactory factory2 = new TwitterFactory();
		Twitter twitter2 = factory2.getInstance();
		twitter2.setOAuthConsumer(consumerKey, consumerSecret);
		AccessToken accessToken2 = new AccessToken(accessToken, accessTokenSecret);
		twitter2.setOAuthAccessToken(accessToken2);
		StatusUpdate statusUpdate2 = new StatusUpdate(replyMessage);
		statusUpdate2.setInReplyToStatusId(messageId);
		Status status2 = twitter2.updateStatus(statusUpdate2);

		logTwitterServerRespond(status2);

	};

	public static void posting(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret)
			throws TwitterException {
		System.out.println("posting a new tweet");
		// Instantiate a re-usable and thread-safe factory
		TwitterFactory twitterFactory = new TwitterFactory();
		// Instantiate a new Twitter instance
		Twitter twitter = twitterFactory.getInstance();
		// setup OAuth Consumer Credentials
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		// setup OAuth Access Token
		twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

		// Instantiate and initialize a new twitter status update
		StatusUpdate statusUpdate = new StatusUpdate("testtweet");

		// attach any media, if you want to
		// statusUpdate.setMedia(
		// //title of media
		// "http://simpledeveloper.com"
		// , new
		// URL("https://si0.twimg.com/profile_images/1733613899/Published_Copy_Book.jpg").openStream());

		Status status = twitter.updateStatus(statusUpdate);

		logTwitterServerRespond(status);
	};

	private static void setAccounts() {
		// TODO Auto-generated method stub
		// ULRIKE RAAB //my account
		// Your Twitter App's Consumer Key
		String consumerKey = "v1qD37aTs6IM4TydfnRUmSqV4";
		// Your Twitter App's Consumer Secret
		String consumerSecret = "vU4KdFYBIZTcmJIsHfry940si7vqr8ZemW8ZhKHp4MkMrzE4re" + "";
		// Your Twitter Access Token
		String accessToken = "4284825923-5CUYEFWwEgbUSuACSQWa5MjekjgeSRq1GOcJ0wf";
		// Your Twitter Access Token Secret
		String accessTokenSecret = "6v4ST6Y7AHr74kD9u32WPJQMtSeJ6xWT6I3eyP5dwuHWc";

		// TIM WUTZ //fake account
		// Your Twitter App's Consumer Key
		String consumerKey2 = "IysM6RsvxyqjWke6C4OSfmjfW";
		// Your Twitter App's Consumer Secret
		String consumerSecret2 = "gmqs2S5rIPQY6Qcd10rcsOUt4ytF2Ruj8kNrcd9KW311xVSDnQ" + "";
		// Your Twitter Access Token
		String accessToken2 = "4545348503-jsAtAxkpmqu9pEgQgdzc1wa6XsBNKfwkZhSzBuN";
		// Your Twitter Access Token Secret
		String accessTokenSecret2 = "se8B7zvGf0aWsJlOGEXTKfHRFl2jf1ULFdepgY2bfhsDk";

	}

	public static void logTwitterServerRespond(Status status) {
		// response from twitter server
		System.out.println("status.toString() = " + status.toString());
		System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
		System.out.println("status.getSource() = " + status.getSource());
		System.out.println("status.getText() = " + status.getText());

		System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
		System.out.println("status.getUserMentionEntities() = " + Arrays.toString(status.getUserMentionEntities()));
	};

}