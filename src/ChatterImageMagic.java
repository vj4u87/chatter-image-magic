import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.sforce.soap.partner.sobject.*;

public class ChatterImageMagic {

	private static Properties configProperties = new Properties();
	
	private static void loadConfigProperties() {
		try {
			configProperties.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			System.out.println("WARNING: unable to read config.properties.");
		}
	}

	public static void main(String[] args) {

		ChatterImageMagic.loadConfigProperties();
		
		if (configProperties.getProperty("debug") != null) {
			Utils.setDebugEnabled(true);
		}
		
		String un;
		un = configProperties.getProperty("username");
		if (un == null) {
			un = Utils.getUserInput("username:");
		}
		
		String pw;
		pw = configProperties.getProperty("password");
		if (pw == null) {
			pw = Utils.getUserInput("password:");
		}
		
		SalesforceClient salesforceClient = new SalesforceClient(un,pw);

		String endPoint;
		endPoint = configProperties.getProperty("endpoint");
		if (endPoint != null && !endPoint.equals("")) {
			salesforceClient.setEndpoint(endPoint);
		}
		
		String dir = configProperties.getProperty("image_directory");
		if (dir == null || dir.equals("")) {
			dir = "images";	
		}
		
		if (salesforceClient.login()) {
			List<SObject> userList = salesforceClient.getQueryResultRecords("" +
					"SELECT Id,SmallPhotoUrl,FullPhotoUrl,Username from User " +
					"WHERE FullPhotoUrl !=  '/profilephoto/005/F' " +
					"AND IsActive = true LIMIT 100");
			
			SalesforceDownloader sd = new SalesforceDownloader(salesforceClient,dir);
			
			Integer count = 0;
			for (SObject s : userList) {
				System.out.print(count + " of " + userList.size() + "-" + s.getField("Username") + "...");
				ChatterProfileImage i = new ChatterProfileImage(
						s.getField("SmallPhotoUrl").toString(),
						s.getField("FullPhotoUrl").toString(),
						s.getField("Id").toString(),
						s.getField("Username").toString());
				sd.downloadImageToFile(i);
				System.out.println("done.");
				count++;
			}
			System.out.println("Downloaded " + count + " images to " + dir);
		} else {
			System.out.println("Error logging in.  Please check your username, password, and API key.");
		}
	}
}
