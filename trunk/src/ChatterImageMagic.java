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
		//Read inputs from propery files.
		
		SalesforceClient salesforceClient = new SalesforceClient(
				"vswamidass@salesforce.com",
				"");
		
		if (salesforceClient.login()) {
			List<SObject> userList = salesforceClient.getQueryResultRecords("" +
					"Select Id,SmallPhotoUrl,FullPhotoUrl from User " +
					"where FullPhotoUrl !=  '/profilephoto/005/F' " +
					"and IsActive = true LIMIT 100");
			
			SalesforceDownloader sd = new SalesforceDownloader(salesforceClient,"images");
			
			Integer count = 0;
			for (SObject s : userList) {
				ChatterProfileImage i = new ChatterProfileImage(
						s.getField("SmallPhotoUrl").toString(),
						s.getField("FullPhotoUrl").toString(),
						s.getField("Id").toString());
				sd.downloadImageToFile(i);
				count++;
			}
			System.out.println("Downloaded " + count + " images");
		}
		

	}

}
