package com.chatterimagemagic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


import com.chatterimagemagic.common.Utils;
import com.chatterimagemagic.salesforce.SalesforceClient;
import com.chatterimagemagic.salesforce.SalesforceDownloader;
import com.chatterimagemagic.salesforce.chatter.ChatterProfileImage;
import com.chatterimagemagic.salesforce.chatter.ChatterUser;
import com.chatterimagemagic.salesforce.chatter.ChatterProfileImage.ChatterImageType;
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
					"SELECT Id,SmallPhotoUrl,FullPhotoUrl,Username,Name from User " +
					"WHERE FullPhotoUrl !=  '/profilephoto/005/F' " +
					"AND IsActive = true LIMIT 300");
			
			SalesforceDownloader sd = new SalesforceDownloader(salesforceClient,dir);
			
			Integer count = 0;
			for (SObject s : userList) {
				System.out.print(count + " of " + userList.size() + "-" + s.getField("Username") + "...");
				
				ChatterUser user = new ChatterUser(
						s.getField("SmallPhotoUrl").toString(),
						s.getField("FullPhotoUrl").toString(),
						s.getField("Id").toString(),
						s.getField("Username").toString(),
						s.getField("Name").toString());
				
				ChatterProfileImage smallImage = new ChatterProfileImage(user,ChatterProfileImage.ChatterImageType.SMALL);
				ChatterProfileImage fullImage = new ChatterProfileImage(user,ChatterProfileImage.ChatterImageType.FULL);
				
				sd.downloadImageToFile(smallImage);
				sd.downloadImageToFile(fullImage);
				
				System.out.println("done.");
				count++;
			}
			System.out.println("Downloaded " + count + " images to " + dir);
		} else {
			System.out.println("Error logging in.  Please check your username, password, and API key.");
		}
	}
}
