import java.io.FileNotFoundException;

import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;

import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;


public class SalesforceClient {
	private static String DEFAULT_ENDPOINT = "http://www.salesforce.com/services/Soap/u/21.0";
		
	private String authEndPoint = "";
	private ConnectorConfig config = new ConnectorConfig();
	
	private PartnerConnection connection;
	
	public SalesforceClient (String username, String password) {
		try {
			config.setUsername(username);
			config.setPassword(password);

			config.setAuthEndpoint(DEFAULT_ENDPOINT);
			config.setTraceFile("traceLogs.txt");
			config.setTraceMessage(true);
			config.setPrettyPrintXml(true);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}
	
	public boolean login() {
		boolean success = false;

		try {
			connection = new PartnerConnection(config);
			
			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} 
		return success;
	}
	
	public void logout() {
		try {
			connection.logout();
			System.out.println("Logged out");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
	
	public void getUserInfo() {
		try {
			GetUserInfoResult userInfo = connection.getUserInfo();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} 
		
		System.out.println("\nLogging in ...\n");
		/*System.out.println("UserID: " + userInfo.getUserId());
		System.out.println("User Full Name: " + userInfo.getUserFullName());
		System.out.println("User Email: " + userInfo.getUserEmail());
		System.out.println();
		*/
		System.out.println("SessionID: " + config.getSessionId());
		System.out.println("Auth End Point: " + config.getAuthEndpoint());
		authEndPoint = config.getAuthEndpoint();
		System.out.println("Service End Point: "
				+ config.getServiceEndpoint());
		System.out.println();
	}
	                           

	public void downloadImage(Downloadable d) {
		
	}
	
	public SObject[] runQuery(String soql) {
		try {
			QueryResult result = connection.query(soql);
			
			boolean done = false;
			
			if (result.getSize() > 0) {
				while (!done) {
					SObject[] records = result.getRecords();
					for (SObject s : records) {
						System.out.println(s.getId());
					}
					if (result.isDone()) {
						done = true;
					} else {
						result = connection.queryMore(result.getQueryLocator());
					}
				}
			} else {
				System.out.println("No records found.");
			}
			
			return null; 
		} catch (ConnectionException ce) {
			ce.printStackTrace();
			return null;
		}
	}
	
}
