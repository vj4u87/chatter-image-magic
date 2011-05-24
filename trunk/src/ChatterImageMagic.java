import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;

import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;

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
		
		if (salesforceClient.login()) {
			List<SObject> userList = salesforceClient.getQueryResultRecords("" +
					"Select Id,SmallPhotoUrl,FullPhotoUrl,Username from User " +
					"where FullPhotoUrl !=  '/profilephoto/005/F' " +
					"and IsActive = true LIMIT 10");
			
			String dir = configProperties.getProperty("image_directory");
			SalesforceDownloader sd = new SalesforceDownloader(salesforceClient,dir);
			
			Integer count = 0;
			for (SObject s : userList) {
				ChatterProfileImage i = new ChatterProfileImage(
						s.getField("SmallPhotoUrl").toString(),
						s.getField("FullPhotoUrl").toString(),
						s.getField("Id").toString(),
						s.getField("Username").toString());
				sd.downloadImageToFile(i);
				count++;
			}
			System.out.println("Downloaded " + count + " images");
		}
		

	}

}
