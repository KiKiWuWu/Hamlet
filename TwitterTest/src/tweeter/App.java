package tweeter;

/**
 * Only there for starting the program and initializing MainController
 * @author Ulrike
 *
 */

public class App {	
	public static void main(String[] args) {
		TwitterDatabaseController tdc = new TwitterDatabaseController();
		tdc.run();
	}
}
