import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Utils {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static boolean isDebugEnabled = false;
	
	public static void setDebugEnabled(boolean e) {
		isDebugEnabled = e;
	}
	
	public static boolean isDebugEnabled() {
		return isDebugEnabled;
	}
	
	public static String getUserInput(String prompt) {
		String result = "";
		try {
			System.out.print(prompt);
			result = reader.readLine();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}
