import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
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

	public static void main(String[] args) {
		SalesforceClient salesforceClient = new SalesforceClient(
				"vswamidass@salesforce.com",
				"MrandMrs#0");
		if (salesforceClient.login()) {
			List<SObject> userList = salesforceClient.getQueryResultRecords("" +
					"Select Id,SmallPhotoUrl,FullPhotoUrl from User " +
					"where FullPhotoUrl !=  '/profilephoto/005/F' " +
					"and IsActive = true LIMIT 5");
			
			Integer count = 0;
			
			SalesforceDownloader sd = new SalesforceDownloader(salesforceClient);
			
			for (SObject s : userList) {
				//download image
				ChatterProfileImage i = new ChatterProfileImage(
						s.getField("SmallPhotoUrl").toString(),
						s.getField("FullPhotoUrl").toString(),
						s.getField("Id").toString());
				System.out.println(i.getSmallPhotoUrl());
				sd.downloadImageToFile(i);
				count++;
			}
		}
		
		System.out.println(salesforceClient.getProtocolAndHost());
	}

}
