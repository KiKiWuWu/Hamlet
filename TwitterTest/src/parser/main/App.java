package parser.main;

/**
 * Only there for starting the program and intializing MainController
 * @author rom54494
 *
 */
public class App {	
	public static void main(String[] args) {
		System.out.println("Starting parsing-program, init MainController");
		MainController mc = new MainController();
		mc.run();
		System.out.println("Shutting down parsing-programm");
	}
}
