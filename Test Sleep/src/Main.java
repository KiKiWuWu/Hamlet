import java.util.regex.Pattern;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		
		
		System.out.println("Start");
		Thread.sleep(1000*62); //62 seks
		
		String string = "Act 1 Scene 3";
		System.out.println(Pattern.matches("Act \\d Scene \\d", string));
		
		
		
		System.out.println("Stopp");
		// TODO Auto-generated method stub

	}

}
